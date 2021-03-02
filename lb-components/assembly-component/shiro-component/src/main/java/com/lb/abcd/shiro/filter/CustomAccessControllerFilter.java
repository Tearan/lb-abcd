package com.lb.abcd.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.lb.abcd.jwt.JwtToken;
import com.lb.abcd.system.constants.Constant;
import com.lb.abcd.system.exception.APIException;
import com.lb.abcd.system.result.Rs;
import com.lb.abcd.system.result.RsCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @ClassName CustomAccessControllerFilter
 * @Description 拦截器
 * @Author Terran
 * @Date 2021/3/1 13:35
 * @Version 1.0
 */
@Slf4j
public class CustomAccessControllerFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        log.info(request.getMethod());
        log.info(request.getRequestURL().toString());
        /** 判断客户端是否携带accessToken*/
        try {
            String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
            if(StringUtils.isEmpty(accessToken)){
                throw new APIException(RsCode.TOKEN_NOT_NULL);
            }

            /** 认证*/
            JwtToken customUsernamePasswordToken = new JwtToken(accessToken);
            getSubject(servletRequest,servletResponse).login(customUsernamePasswordToken);
        } catch (APIException e) {
            customResponse(e.getCode(),e.getMsg(),servletResponse);
            return false;

        } catch (AuthenticationException e) {
            if(e.getCause() instanceof APIException){
                APIException exception= (APIException) e.getCause();
                customResponse(exception.getCode(),exception.getMsg(),servletResponse);
            }else {
                customResponse(RsCode.SHIRO_AUTHENTICATION_ERROR.getCode(),RsCode.SHIRO_AUTHENTICATION_ERROR.getMsg(),servletResponse);
            }
            return false;
        }
        return true;
    }

    /**
     * 自定义错误响应
     */
    private void customResponse(int code, String msg, ServletResponse response){
        /** 自定义异常的类，用户返回给客户端相应的JSON格式的信息*/
        try {
            Rs result = Rs.getResult(code,msg);
            response.setContentType("application/json; charset=utf-8");
            response.setCharacterEncoding("UTF-8");

            String userJson = JSON.toJSONString(result);
            OutputStream out = response.getOutputStream();
            out.write(userJson.getBytes("UTF-8"));
            out.flush();
        } catch (IOException e) {
            log.error("error={}",e);
        }
    }
}
