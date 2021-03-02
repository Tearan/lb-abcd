package com.lb.abcd.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lb.abcd.shiro.dao.UserDao;
import com.lb.abcd.shiro.entity.User;
import com.lb.abcd.shiro.service.UserService;
import com.lb.abcd.shiro.vo.request.*;
import com.lb.abcd.shiro.vo.response.LoginRespVO;
import com.lb.abcd.shiro.vo.response.PageVO;
import com.lb.abcd.shiro.vo.response.UserOwnRoleRespVO;
import com.lb.abcd.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author Terran
 * @Date 2021/1/31 15:04
 * @Version 1.0
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao, User> implements UserService {

    @Override
    public List<User> getUser() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("id");
        return this.baseMapper.selectList(wrapper);
    }

    @Override
    public PageVO<User> pageInfo(UserPageReqVO vo) {
        return null;
    }

    @Override
    public LoginRespVO login(LoginReqVO vo) {
        return null;
    }

    @Override
    public void register(RegisterReqVO vo) {

    }

    @Override
    public User getUser(String userId) {
        return null;
    }

    @Override
    public void updatePwd(UserUpdatePwdReqVO vo, String accessToken, String refreshToken) {

    }

    @Override
    public void updateUser(User user, String userId) {

    }

    @Override
    public void logout(String accessToken, String refreshToken) {

    }

    @Override
    public String token(String refreshToken) {
        return null;
    }

    @Override
    public void add(UserAddReqVO vo, String userId) {

    }

    @Override
    public void update(UserUpdateReqVO vo, String userId) {

    }

    @Override
    public void delete(List<String> userIds, String userId) {

    }

    @Override
    public void updateUserRole(UserOwnRoleReqVO vo) {

    }

    @Override
    public UserOwnRoleRespVO getUserOwnRole(String userId) {
        return null;
    }
}
