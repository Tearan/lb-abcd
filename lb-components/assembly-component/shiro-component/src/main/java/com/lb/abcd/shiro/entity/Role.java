package com.lb.abcd.shiro.entity;

import lombok.Data;

import java.util.List;

/**
 * @ClassName Role
 * @Description TODO
 * @Author Terran
 * @Date 2021/1/31 15:07
 * @Version 1.0
 */
@Data
public class Role {

    private String rname;

    private List<Module> modules;
}
