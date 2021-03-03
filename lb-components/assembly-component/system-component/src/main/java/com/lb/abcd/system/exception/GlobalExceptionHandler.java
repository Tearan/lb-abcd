package com.lb.abcd.system.exception;

import com.lb.abcd.system.result.Rs;
import com.lb.abcd.system.result.RsCode;
import lombok.extern.slf4j.Slf4j;
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

    @ExceptionHandler(value = Exception.class)
    public Rs exception(Exception e){
        log.error("Exception,{},{}",e.getLocalizedMessage(),e);
        return Rs.getResult(RsCode.SYSTEM_ERROR);
    }

    /**
     * 自定义异常APIException
     */
    @ExceptionHandler(APIException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Rs<Object> APIExceptionHandler(APIException e) {
        log.error("api异常");
        return new Rs<>(e.getCode(), e.getMsg());
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
        return new Rs<>(RsCode.DATA_ERROR, list);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public Rs unauthorizedException(UnauthorizedException e){
        log.error("UnauthorizedException,{},{}",e.getLocalizedMessage(),e);
        return Rs.getResult(RsCode.NOT_PERMISSION);
    }
}
