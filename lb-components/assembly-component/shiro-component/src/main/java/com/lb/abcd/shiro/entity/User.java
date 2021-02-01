package com.lb.abcd.shiro.entity;

import lombok.Data;

import java.util.List;

/**
 * @ClassName User
 * @Description TODO
 * @Author Terran
 * @Date 2021/1/31 14:19
 * @Version 1.0
 */
@Data
public class User {

    private List<Role> roles;
}
