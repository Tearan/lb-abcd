package com.lb.abcd.shiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lb.abcd.shiro.dao.UserRoleDao;
import com.lb.abcd.shiro.entity.UserRole;
import com.lb.abcd.shiro.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserRoleServiceImpl
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/3 9:55
 * @Version 1.0
 */

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {
}
