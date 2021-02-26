package com.lb.abcd.system.exception;

import com.lb.abcd.system.result.RsCode;
import lombok.Data;

/**
 * @ClassName APIException
 * @Description 自定义异常
 * @Author Terran
 * @Date 2021/2/2 15:52
 * @Version 1.0
 */
@Data
public class APIException extends RuntimeException{

    private Integer code;

    private String msg;

    public APIException() {
        this(RsCode.FAILED);
    }

    public APIException(RsCode failed) {
        this.code = failed.getCode();
        this.msg = failed.getMsg();
    }
}
