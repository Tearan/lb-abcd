package com.lb.abcd.shiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lb.abcd.shiro.entity.RolePermission;

import java.util.List;

/**
 * @ClassName RolePermissionService
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/3 13:19
 * @Version 1.0
 */
public interface RolePermissionService extends IService<RolePermission> {

    /*
    根据roleId获取permissionId
     */
    List<String> getPermissionIds(String roleId);
}
