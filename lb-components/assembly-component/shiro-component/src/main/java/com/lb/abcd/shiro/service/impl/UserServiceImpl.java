package com.lb.abcd.shiro.service.impl;

import com.lb.abcd.shiro.entity.User;
import com.lb.abcd.shiro.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author Terran
 * @Date 2021/1/31 15:04
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User findUserByUserName(String username) {
        return null;
    }
}
