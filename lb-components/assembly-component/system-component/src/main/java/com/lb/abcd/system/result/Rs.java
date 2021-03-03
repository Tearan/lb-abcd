package com.lb.abcd.system.result;

import com.lb.abcd.system.exception.APIException;
import io.swagger.annotations.ApiModelProperty;
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
     * 状态码，比如1代表响应成功
     */
    @ApiModelProperty(value = "响应码")
    private Integer code;

    /**
     * 响应信息，用来说明响应情况
     */
    @ApiModelProperty(value = "响应信息")
    private String msg;

    /**
     * 响应的具体数据
     */
    @ApiModelProperty(value = "响应的数据")
    private R data;

    public Rs(RsCode responseCode, R data){
        this.code = responseCode.getCode();
        this.msg = responseCode.getMsg();
        this.data = data;
    }

    public Rs(RsCode responseCode){
        this.code = responseCode.getCode();
        this.msg = responseCode.getMsg();
        this.data = null;
    }

    public Rs(Integer code, R data) {
        this.code = code;
        this.data = data;
        this.msg = null;
    }
    public Rs(Integer code, String msg, R data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public Rs(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data=null;
    }

    public Rs() {
        this.code = RsCode.SUCCESS.getCode();
        this.msg = RsCode.SUCCESS.getMsg();
        this.data=null;
    }

    public Rs(R data) {
        this.code = RsCode.SUCCESS.getCode();
        this.msg = RsCode.SUCCESS.getMsg();
        this.data=data;
    }

    public static Rs success(){
        return new Rs();
    }

    public static <R>Rs success(R data){
        return new Rs(data);
    }

    public static <R>Rs getResult(RsCode responseCode,R data){
        return new Rs(responseCode,data);
    }

    public static Rs getResult(RsCode responseCode){
        return new Rs(responseCode);
    }

    public static <R>Rs getResult(Integer code,String msg,R data){
        return new Rs(code,msg,data);
    }

    public static Rs getResult(Integer code,String msg){
        return new Rs(code,msg);
    }

}