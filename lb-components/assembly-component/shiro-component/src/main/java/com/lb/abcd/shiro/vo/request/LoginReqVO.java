package com.lb.abcd.shiro.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName LoginReqVO
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/2 11:31
 * @Version 1.0
 */

@Data
public class LoginReqVO {

    @ApiModelProperty(value = "账号")
    @NotBlank(message = "账号不能为空")
    private String username;

    @ApiModelProperty(value ="密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value ="验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;

    @ApiModelProperty(value = "登录类型 0：pc；1：App")
    @NotBlank(message = "用户登录类型不能为空")
    private String type;
}
