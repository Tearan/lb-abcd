package com.lb.abcd.shiro.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName UserUpdateReqVO
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/2 11:35
 * @Version 1.0
 */

@Data
public class UserUpdateReqVO {

    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "用户id不能为空")
    private String id;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "账户状态(0.正常 1.锁定 )")
    private Integer status;

    @ApiModelProperty(value = "所属部门")
    private String deptId;
}