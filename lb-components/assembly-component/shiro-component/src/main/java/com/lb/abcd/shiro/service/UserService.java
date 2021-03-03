package com.lb.abcd.shiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lb.abcd.shiro.entity.User;
import com.lb.abcd.shiro.vo.request.*;
import com.lb.abcd.shiro.vo.response.LoginRespVO;
import com.lb.abcd.system.vo.response.PageVO;
import com.lb.abcd.shiro.vo.response.UserOwnRoleRespVO;

import java.util.List;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author Terran
 * @Date 2021/1/31 14:16
 * @Version 1.0
 */
public interface UserService extends IService<User> {

    /**
     * 获取所有用户信息
     */
    List<User> getUser();

    /**
     * 获取所有用户信息(分页)
     * @param vo
     * @return
     */
    PageVO<User> pageInfo(UserPageReqVO vo);

    /***
     * 登录
     * @param vo
     * @return
     */
    LoginRespVO login(LoginReqVO vo);

    /**
     * 注册
     * @param vo
     */
    void register(RegisterReqVO vo);

    /**
     * 获取当前用户信息
     * @param userId
     * @return
     */
    User getUser(String userId);

    /**
     * 修改当前用户密码
     * @param vo
     * @param accessToken
     * @param refreshToken
     */
    void updatePwd(UserUpdatePwdReqVO vo, String accessToken, String refreshToken);

    /**
     *  修改当前用户信息
     * @param user
     * @param userId
     */
    void updateUser(User user,String userId);

    /**
     * 退出系统
     * @param accessToken
     * @param refreshToken
     */
    void logout(String accessToken,String refreshToken);

    /**
     * 根据刷新token获取新的业务token
     * @param refreshToken
     * @return
     */
    String token(String refreshToken);

    /**
     * 新增用户
     * @param vo
     * @param userId
     */
    void add(UserAddReqVO vo, String userId);

    /**
     * 更新用户
     * @param vo
     * @param userId
     */
    void update(UserUpdateReqVO vo, String userId);

    /**
     * 删除用户
     * @param userIds
     * @param userId
     */
    void delete(List<String> userIds,String userId);

    /**
     * 赋予用户角色
     * @param vo
     */
    void updateUserRole(UserOwnRoleReqVO vo);

    /**
     * 查询用户拥有的角色
     * @param userId
     * @return
     */
    UserOwnRoleRespVO getUserOwnRole(String userId);
}
