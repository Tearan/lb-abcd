package com.lb.abcd.system.result;

import lombok.Getter;

/**
 * @ClassName ReCode
 * @Description 响应码枚举
 * @Author Terran
 * @Date 2021/2/2 15:46
 * @Version 1.0
 */

@Getter
public enum RsCode {

    /** 成功状态码*/
    SUCCESS(1, "操作成功"),

    /** 参数错误:1000-1999 */
    FAILED(1000, "接口错误"),
    VALIDATE_FAILED(1001, "参数校验失败"),
    ERROR(1002, "未知错误"),

    /** 用户错误: 2000-2999 */
    USER_NOT_EXIST(2000,"用户不存在"),
    USER_LOGIN_FAIL(2001,"用户名或密码错误"),
    USER_NOT_LOGIN(2002,"用户还未登录,请先登录"),
    NO_PERMISSION(2003,"权限不足,请联系管理员");

    private Integer code;

    private String msg;

    RsCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
