package com.lb.abcd.shiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lb.abcd.shiro.dao.RolePermissionDao;
import com.lb.abcd.shiro.entity.RolePermission;
import com.lb.abcd.shiro.service.RolePermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RolePermissionService
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/3 13:19
 * @Version 1.0
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionDao, RolePermission> implements RolePermissionService {

    @Override
    public List<String> getPermissionIds(String roleId) {
        return this.baseMapper.getPermissionIds(roleId);
    }
}
