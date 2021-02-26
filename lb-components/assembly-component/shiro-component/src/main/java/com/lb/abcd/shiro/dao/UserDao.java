package com.lb.abcd.shiro.dao;

import com.lb.abcd.shiro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserDao
 * @Description TODO
 * @Author Terran
 * @Date 2021/2/3 13:54
 * @Version 1.0
 */
@Repository
public interface UserDao extends JpaRepository<User,String> {
}
