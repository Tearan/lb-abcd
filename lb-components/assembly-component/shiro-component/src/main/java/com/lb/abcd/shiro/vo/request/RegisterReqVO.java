package com.lb.abcd.shiro.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName RegisterReqVO
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/2 11:33
 * @Version 1.0
 */

@Data
public class RegisterReqVO {

    @ApiModelProperty(value = "账号")
    @NotBlank(message = "账号不能为空")
    private String registerUsername;

    @ApiModelProperty(value ="密码")
    @NotBlank(message = "密码不能为空")
    private String registerPassword;

    @ApiModelProperty(value ="确认密码")
    @NotBlank(message = "确认密码不能为空")
    private String registerWellPassword;
}
