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

    /** 异常 code */
    private Integer code;

    /** 异常提示 */
    private String msg;

    public APIException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public APIException() {
        this(RsCode.SYSTEM_ERROR);
    }

    public APIException(RsCode failed) {
        this.code = failed.getCode();
        this.msg = failed.getMsg();
    }
}
