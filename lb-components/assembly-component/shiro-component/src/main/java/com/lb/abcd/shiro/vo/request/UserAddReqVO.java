package com.lb.abcd.shiro.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName UserAddReqVO
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/2 11:34
 * @Version 1.0
 */

@Data
public class UserAddReqVO {


    @ApiModelProperty(value = "账号")
    @NotBlank(message = "账号不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "所属部门")
    @NotBlank(message = "所属部门不能为空")
    private String deptId;

    @ApiModelProperty(value = "创建来源(0.web 1.android 2.ios )")
    private String createWhere;

    @ApiModelProperty(value = "账户状态(0.正常 1.锁定 )")
    private Integer status;
}
