package com.lb.abcd.shiro.service.impl;

import com.lb.abcd.shiro.dao.UserDao;
import com.lb.abcd.shiro.entity.User;
import com.lb.abcd.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author Terran
 * @Date 2021/1/31 15:04
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findUserByUserName(String username) {
        User user = new User();
        user.setUsername(username);
        Example<User> example = Example.of(user);
        Optional<User> optional = userDao.findOne(example);
        User user1 = optional.orElse(null);
        return user1;
    }

    @Override
    public User save(User user) {
       return userDao.save(user);
    }
}
