package com.lb.abcd.shiro.service.impl;

import com.lb.abcd.jwt.config.JwtConfig;
import com.lb.abcd.redis.util.RedisUtil;
import com.lb.abcd.shiro.dao.RoleDao;
import com.lb.abcd.shiro.dao.UserDao;
import com.lb.abcd.shiro.entity.Role;
import com.lb.abcd.shiro.entity.User;
import com.lb.abcd.shiro.service.RoleService;
import com.lb.abcd.shiro.service.UserService;
import com.lb.abcd.shiro.vo.request.AddRoleReqVO;
import com.lb.abcd.shiro.vo.request.RolePageReqVO;
import com.lb.abcd.shiro.vo.request.RoleUpdateReqVO;
import com.lb.abcd.shiro.vo.response.PageVO;
import com.lb.abcd.system.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RoleServiceImpl
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/1 14:42
 * @Version 1.0
 */

@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleDao, Role> implements RoleService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public List<Role> getRoleByUserId(String userId) {
        return null;
    }

    @Override
    public List<String> getRoleNames(String userId) {
        return null;
    }

    @Override
    public PageVO<Role> getRoles(RolePageReqVO vo) {
        return null;
    }

    @Override
    public void add(AddRoleReqVO vo) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Role getRole(String roleId) {
        return null;
    }

    @Override
    public void update(RoleUpdateReqVO vo) {

    }
}
