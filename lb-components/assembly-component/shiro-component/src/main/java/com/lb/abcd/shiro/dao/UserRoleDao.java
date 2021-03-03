package com.lb.abcd.shiro.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lb.abcd.shiro.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName UserRoleDao
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/2 14:02
 * @Version 1.0
 */

@Mapper
public interface UserRoleDao extends BaseMapper<UserRole> {

    /**
     * 根据roleId获取userId
     * @param roleId
     * @return
     */
    public List<String> getUserIds(String roleId);
}
