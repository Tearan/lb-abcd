package com.lb.abcd.system.exception;

import com.lb.abcd.system.result.Rs;
import com.lb.abcd.system.result.RsCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName GlobalExceptionHandler
 * @Description 全局异常处理类
 * @Author Terran
 * @Date 2021/2/2 15:54
 * @Version 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 自定义异常APIException
     */
    @ExceptionHandler(APIException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Rs<Object> APIExceptionHandler(APIException e) {
        log.error("api异常");
        return new Rs<>(RsCode.FAILED, e.getMsg());
    }

    /**
     * 捕捉所有Shiro异常
     */
    @ExceptionHandler(ShiroException.class)
    public Rs<String> handle401(ShiroException e) {
        return new Rs<String>(RsCode.NO_PERMISSION, "无权访问(Unauthorized):" + e.getMessage());
    }

    /**
     * 单独捕捉Shiro(UnauthorizedException)异常 该异常为访问有权限管控的请求而该用户没有所需权限所抛出的异常
     */
    @ExceptionHandler(UnauthorizedException.class)
    public Rs<String> handle401(UnauthorizedException e) {
        return new Rs<String>(RsCode.NO_PERMISSION, "无权访问(Unauthorized):当前Subject没有此请求所需权限(" + e.getMessage() + ")");
    }

    /**
     * 单独捕捉Shiro(UnauthenticatedException)异常
     * 该异常为以游客身份访问有权限管控的请求无法对匿名主体进行授权，而授权失败所抛出的异常
     */
    @ExceptionHandler(UnauthenticatedException.class)
    public Rs<String> handle401(UnauthenticatedException e) {
        e.printStackTrace();
        return new Rs<String>(RsCode.NO_PERMISSION, "无权访问(Unauthorized):当前Subject是匿名Subject，请先登录(This subject is anonymous.)");
    }

    /**
     * 方法参数错误异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Rs<Object> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("方法参数错误异常");

        /** 从异常对象中拿到ObjectError对象*/
        List<String> list=new ArrayList<>();
        if (!e.getBindingResult().getAllErrors().isEmpty()){
            for(ObjectError error:e.getBindingResult().getAllErrors()){
                list.add(error.getDefaultMessage().toString());
            }
        }
        /** 然后提取错误提示信息进行返回*/
        return new Rs<>(RsCode.VALIDATE_FAILED, list);
    }
}
