package com.lb.abcd.service;

import com.lb.abcd.entity.User;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author Terran
 * @Date 2021/1/31 14:16
 * @Version 1.0
 */
public interface UserService {

    User findUserByUserName(String username);
}
