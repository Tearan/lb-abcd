package com.lb.abcd.aspect;

import com.alibaba.fastjson.JSON;
import com.lb.abcd.dao.SysLogDao;
import com.lb.abcd.entity.SysLog;
import com.lb.abcd.jwt.util.JWTUtil;
import com.lb.abcd.system.annotation.MyLog;
import com.lb.abcd.system.util.HttpContextUtils;
import com.lb.abcd.system.util.IDUtils;
import com.lb.abcd.system.util.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @ClassName MyLogAspect
 * @Description TODO
 * @Author Terran
 * @Date 2021/2/26 17:10
 * @Version 1.0
 */

@Aspect
@Component
@Slf4j
public class MyLogAspect {

    @Autowired
    private SysLogDao sysLogDao;

    @Pointcut("@annotation(com.lb.abcd.system.annotation.MyLog)")
    public void logPointCut(){

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        /** 执行方法*/
        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;
        try {
            /** 保存日志*/
            saveSysLog(point, time);
        } catch (Exception e) {
            log.error("e={}",e);
        }
        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = new SysLog();
        MyLog myLog = method.getAnnotation(MyLog.class);
        if(myLog != null){

            /** 注解上的描述*/
            sysLog.setOperation(myLog.title() + "-" + myLog.action());
        }

        /** 请求的方法名*/
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        /** 打印该方法耗时时间*/
        log.info("请求{}.{}耗时{}毫秒",className,methodName,time);
        try {
            /** 请求的参数*/
            Object[] args = joinPoint.getArgs();
            String params=null;
            if(args.length != 0){
                params= JSON.toJSONString(args);
            }

            sysLog.setParams(params);
        } catch (Exception e) {

        }
        /** 获取request*/
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();

        /** 设置IP地址*/
        sysLog.setIp(IPUtils.getIpAddr(request));
        log.info("Ip{}，接口地址{}，请求方式{}，入参：{}",sysLog.getIp(),request.getRequestURL(),request.getMethod(),sysLog.getParams());

        /** 用户名 正常token*/
        String token = request.getHeader("authorization");
        String userId= JWTUtil.getUserId(token);
        String username = JWTUtil.getUserName(token);
        sysLog.setUsername(username);
        sysLog.setUserId(userId);
        sysLog.setTime((int) time);
        sysLog.setId(IDUtils.generateId());
        sysLog.setCreateTime(new Date());
        log.info(sysLog.toString());
        sysLogDao.insert(sysLog);
    }
}
