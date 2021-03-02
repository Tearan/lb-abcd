package com.lb.abcd.shiro.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @ClassName RoleUpdateReqVO
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/1 14:45
 * @Version 1.0
 */

@Data
public class RoleUpdateReqVO {

    @ApiModelProperty(value = "角色id")
    @NotBlank(message = "角色 id 不能为空")
    private String id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String description;

    @ApiModelProperty(value = "状态(0:正常;1:弃用)")
    private Integer status;

    @ApiModelProperty(value = "所拥有的菜单权限")
    private List<String> permissions;
}