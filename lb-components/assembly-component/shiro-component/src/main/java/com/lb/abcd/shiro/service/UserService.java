package com.lb.abcd.shiro.service;

import com.lb.abcd.shiro.entity.User;
import com.lb.abcd.shiro.vo.request.*;
import com.lb.abcd.shiro.vo.response.LoginRespVO;
import com.lb.abcd.shiro.vo.response.PageVO;
import com.lb.abcd.shiro.vo.response.UserOwnRoleRespVO;
import com.lb.abcd.system.service.BaseService;

import java.util.List;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author Terran
 * @Date 2021/1/31 14:16
 * @Version 1.0
 */
public interface UserService extends BaseService<User> {

    /**
     * 获取所有用户信息
     */
    public List<User> getUser();

    /*
    获取所有用户信息(分页)
     */
    public PageVO<User> pageInfo(UserPageReqVO vo);

    /*
    登录
     */
    public LoginRespVO login(LoginReqVO vo);

    /*
    注册
     */
    public void register(RegisterReqVO vo);

    /*
    获取当前用户信息
     */
    public User getUser(String userId);

    /*
    修改当前用户密码
     */
    public void updatePwd(UserUpdatePwdReqVO vo, String accessToken, String refreshToken);

    /*
    修改当前用户信息
     */
    public void updateUser(User user,String userId);

    /*
    退出系统
     */
    public void logout(String accessToken,String refreshToken);

    /*
    根据刷新token获取新的业务token
     */
    public String token(String refreshToken);

    /*
    新增用户
     */
    public void add(UserAddReqVO vo, String userId);

    /*
    更新用户
     */
    public void update(UserUpdateReqVO vo, String userId);

    /*
    删除用户
     */
    public void delete(List<String> userIds,String userId);

    /*
    赋予用户角色
     */
    public void updateUserRole(UserOwnRoleReqVO vo);

    /*
    查询用户拥有的角色
     */
    public UserOwnRoleRespVO getUserOwnRole(String userId);
}
