package com.lb.abcd.shiro.dao;

import com.lb.abcd.shiro.entity.Role;
import com.lb.abcd.shiro.entity.User;
import com.lb.abcd.system.mapper.IMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName RoleDao
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/1 14:43
 * @Version 1.0
 */

public interface RoleDao extends IMapper<Role> {
}
