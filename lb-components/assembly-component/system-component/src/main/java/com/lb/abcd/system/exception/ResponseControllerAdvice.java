package com.lb.abcd.system.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lb.abcd.system.result.Rs;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @ClassName ResponseControllerAdvice
 * @Description 全局处理增强版Controller，避免Controller里返回数据每次都要用响应体来包装
 * @Author Terran
 * @Date 2021/2/2 15:57
 * @Version 1.0
 */

/** 注意哦，这里要加上需要扫描的包*/
@RestControllerAdvice(basePackages = {"com.lb.abcd.rest"})
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
        /** 如果接口返回的类型本身就是Rs那就没有必要进行额外的操作，返回false*/
        return !returnType.getGenericParameterType().equals(Rs.class);
    }
    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        /** String类型不能直接包装，所以要进行些特别的处理*/
        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                /** 将数据包装在Rs里后，再转换为json字符串响应给前端*/
                return objectMapper.writeValueAsString(new Rs<>(data));
            } catch (JsonProcessingException e) {
                throw new APIException();
            }
        }

        /** 将原本的数据包装在Rs里*/
        return new Rs<>(data);
    }
}
