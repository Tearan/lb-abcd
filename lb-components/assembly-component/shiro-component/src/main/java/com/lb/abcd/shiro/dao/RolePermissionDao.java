package com.lb.abcd.shiro.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lb.abcd.shiro.entity.RolePermission;
import com.lb.abcd.system.mapper.IMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName RolePermissionDao
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/2 14:00
 * @Version 1.0
 */
@Mapper
public interface RolePermissionDao extends BaseMapper<RolePermission> {

    /**
     * 根据roleId获取permissionId
     * @param roleId
     * @return
     */
    public List<String> getPermissionIds(String roleId);
}
