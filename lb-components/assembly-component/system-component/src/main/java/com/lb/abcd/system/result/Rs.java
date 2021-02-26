package com.lb.abcd.system.result;

import lombok.Data;

/**
 * @ClassName Rs
 * @Description 自定义统一响应体
 * @Author Terran
 * @Date 2021/2/2 15:42
 * @Version 1.0
 */
@Data
public class Rs<R> {

    /**
     * 状态码，比如1000代表响应成功
     */
    private Integer code;

    /**
     * 响应信息，用来说明响应情况
     */
    private String msg;

    /**
     * 响应的具体数据
     */
    private R data;

    public Rs(R data) {
       this(RsCode.SUCCESS, data);
    }

    public Rs(RsCode rsCode, R data) {
        this.code = rsCode.getCode();
        this.msg = rsCode.getMsg();
        this.data = data;
    }

}