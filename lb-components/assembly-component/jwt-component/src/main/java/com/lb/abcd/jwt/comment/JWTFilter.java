package com.lb.abcd.jwt.comment;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lb.abcd.jwt.JwtToken;

import com.lb.abcd.jwt.util.JWTUtil;
import com.lb.abcd.redis.util.RedisUtil;
import com.lb.abcd.system.result.Rs;
import com.lb.abcd.system.result.RsCode;
import com.lb.abcd.system.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName JWTFilter
 * @Description TODO
 * @Author Terran
 * @Date 2021/1/31 14:42
 * @Version 1.0
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {

    /**
     * 判断是否允许通过
     *
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        log.info("isAccessAllowed方法,判断是否允许通过");
        try {
            return executeLogin(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
            response401(request,response);
            return false;
        }
    }

    /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含token字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        log.info("isLoginAttempt方法,判断用户是否想要登入");
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("authorization");
        return authorization != null;
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        log.info("createToken方法,创建shiro token");
        String jwtToken = ((HttpServletRequest)request).getHeader("authorization");
        if(jwtToken != null) {
            return new JwtToken(jwtToken);
        }
        return null;
    }

    /**
     * isAccessAllowed为false时调用，验证失败
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        log.info("onAccessDenied方法");
        this.sendChallenge(request,response);
        responseError(response,"token verify fail");
        response401(request,response);
        return false;
    }


    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) {
        log.info("onLoginSuccess方法,shiro验证成功调用");
        String jwtToken = (String) token.getPrincipal();
        if (jwtToken != null){
            try{
                if(JWTUtil.verify(jwtToken)){

                    /** 判断Redis是否存在所对应的 RefreshToken*/
                    String userId = JWTUtil.getUserId(jwtToken);

                    Long currentTime = JWTUtil.getCurrentTime(jwtToken);

                    if (getRedisUtil().hasKey(String.valueOf(userId))) {
                        Long currentTimeMillisRedis = (Long) getRedisUtil().get(userId);
                        return currentTimeMillisRedis.equals(currentTime);
                    }
                }
                return false;
            }catch (Exception e){
                log.info("token验证:[{}]",e.getClass());
                if (e instanceof TokenExpiredException){
                    log.info("TokenExpiredException");
                    return refreshToken(request, response);
                }
            }
        }
        return true;
    }

    /**
     * 对跨域提供支持
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));

        /** 如果不带token，不去验证shiro */
        if (!isLoginAttempt(request,response)){
            responseError(response,"no token");
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);

    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {

        AuthenticationToken token = this.createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        } else {
            try {
                Subject subject = this.getSubject(request, response);
                subject.login(token);
                return this.onLoginSuccess(token, subject, request, response);
            } catch (AuthenticationException var5) {
                return this.onLoginFailure(token, var5, request, response);
            }
        }
    }

    /**
     * 刷新AccessToken，进行判断RefreshToken是否过期，未过期就返回新的AccessToken且继续正常访问
     */
    private boolean refreshToken(ServletRequest request, ServletResponse response) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String token = ((HttpServletRequest)request).getHeader("authorization");
        String userId = JWTUtil.getUserId(token);
        Long currentTime= JWTUtil.getCurrentTime(token);

        /** 判断Redis中RefreshToken是否存在*/
        if (getRedisUtil().hasKey(userId)) {

            /** Redis中RefreshToken还存在，获取RefreshToken的时间戳*/
            Long currentTimeMillisRedis = (Long) getRedisUtil().get(userId);

            /** 获取当前AccessToken中的时间戳，与RefreshToken的时间戳对比，如果当前时间戳一致，进行AccessToken刷新*/
            if (currentTimeMillisRedis.equals(currentTime)) {

                /** 获取当前最新时间戳*/
                Long currentTimeMillis =System.currentTimeMillis();
                getRedisUtil().set(userId, currentTimeMillis,JWTUtil.REFRESH_EXPIRE_TIME);

                /** 刷新AccessToken，设置时间戳为当前最新时间戳*/
                token = JWTUtil.sign(userId, currentTimeMillis);
                httpServletResponse.setHeader("Authorization", token);
                httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
                return true;
            }
        }
        return false;
    }

    /**
     * 将非法请求跳转到 /2003
     * @param req
     * @param resp
     */
    private void response401(ServletRequest req, ServletResponse resp) {
        try {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) resp;
            try {
                request.getRequestDispatcher("/2003").forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseError(ServletResponse response, String msg){
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(2003);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=UTF-8");
        try {
            String rj = new ObjectMapper().writeValueAsString(new Rs(RsCode.NO_PERMISSION,msg));
            httpResponse.getWriter().append(rj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private RedisUtil getRedisUtil(){
        return SpringContextUtils.getBean("redisUtil",RedisUtil.class);
    }
}