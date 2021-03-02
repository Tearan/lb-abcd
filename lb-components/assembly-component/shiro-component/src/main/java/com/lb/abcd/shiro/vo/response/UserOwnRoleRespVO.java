package com.lb.abcd.shiro.vo.response;

import com.lb.abcd.shiro.entity.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName UserOwnRoleRespVO
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/2 11:36
 * @Version 1.0
 */

@Data
public class UserOwnRoleRespVO {

    @ApiModelProperty(value = "拥有角色集合")
    private List<String> ownRoles;

    @ApiModelProperty(value = "所有角色列表")
    private List<Role> allRole;
}
