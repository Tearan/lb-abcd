package com.lb.abcd.shiro.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lb.abcd.shiro.entity.Permission;
import com.lb.abcd.system.mapper.IMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName PermissionDao
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/1 14:55
 * @Version 1.0
 */

@Mapper
public interface PermissionDao extends BaseMapper<Permission> {

    /**
     * 根据userId获取权限信息
     * @param userId
     * @return
     */
    public List<Permission> getPermissionByUserId(String userId);

    /**
     * 获取左侧菜单栏
     * @return
     */
    public List<Permission> getMenu();

    /**
     * 根据权限id获取用户Id
     * @param id
     * @return
     */
    public List<String> getUserIds(String id);
}
