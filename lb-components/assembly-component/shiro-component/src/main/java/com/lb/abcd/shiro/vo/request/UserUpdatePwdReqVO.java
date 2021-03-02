package com.lb.abcd.shiro.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName UserUpdatePwdReqVO
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/2 11:33
 * @Version 1.0
 */

@Data
public class UserUpdatePwdReqVO {

    @ApiModelProperty(value = "旧密码")
    @NotBlank(message = "旧密码不能为空")
    private String oldPwd;

    @ApiModelProperty(value = "新密码")
    @NotBlank(message = "新密码不能为空")
    private String newPwd;
}
