package com.lb.abcd.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lb.abcd.jwt.JwtToken;
import com.lb.abcd.jwt.config.JwtConfig;
import com.lb.abcd.jwt.util.JWTUtil;
import com.lb.abcd.redis.util.RedisUtil;
import com.lb.abcd.shiro.dao.UserDao;
import com.lb.abcd.shiro.entity.Role;
import com.lb.abcd.shiro.entity.User;
import com.lb.abcd.shiro.entity.UserRole;
import com.lb.abcd.shiro.service.*;
import com.lb.abcd.shiro.vo.request.*;
import com.lb.abcd.shiro.vo.response.LoginRespVO;
import com.lb.abcd.system.constants.Constant;
import com.lb.abcd.system.exception.APIException;
import com.lb.abcd.system.result.RsCode;
import com.lb.abcd.system.util.IDUtils;
import com.lb.abcd.system.util.PageUtil;
import com.lb.abcd.system.util.PasswordUtils;
import com.lb.abcd.system.vo.response.PageVO;
import com.lb.abcd.shiro.vo.response.UserOwnRoleRespVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author Terran
 * @Date 2021/1/31 15:04
 * @Version 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public List<User> getUser() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("id");
        return this.baseMapper.selectList(wrapper);
    }

    @Override
    public PageVO<User> pageInfo(UserPageReqVO vo) {
        Page<User> page = new Page<>(vo.getPageNum(),vo.getPageSize());
        IPage<User> users = this.baseMapper.selectAll(page,vo);
        return PageUtil.getPage(users);
    }

    @Override
    public LoginRespVO login(LoginReqVO vo) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",vo.getUsername());
        wrapper.eq("deleted",1);
        User userInfoByName = this.baseMapper.selectOne(wrapper);

        if(userInfoByName == null){
            throw new APIException(RsCode.ACCOUNT_ERROR);
        }

        /** 判断账号是否锁定*/
        if(userInfoByName.getStatus() == 2){
            throw new APIException(RsCode.ACCOUNT_LOCK_TIP);
        }

        if(!PasswordUtils.matches(userInfoByName.getSalt(),vo.getPassword(),userInfoByName.getPassword())){
            throw new APIException(RsCode.ACCOUNT_PASSWORD_ERROR);
        }
        LoginRespVO loginRespVO = new LoginRespVO();
        Map<String, Object> claims = new HashMap<>();

        /** 角色key*/
        claims.put(Constant.JWT_ROLES_KEY,roleService.getRoleNames(userInfoByName.getId()));

        /** 权限key*/
        claims.put(Constant.JWT_PERMISSIONS_KEY,permissionService.getPerms(userInfoByName.getId()).stream().collect(Collectors.toSet()));

        /** 用户名称 key*/
        claims.put(Constant.JWT_USER_NAME,userInfoByName.getUsername());

        String accessToken = JWTUtil.getAccessToken(userInfoByName.getId(),claims);
        String refreshToken;
        /** 判断：PC,还是APP用户;*/
        if(vo.getType().equals("1")){
            refreshToken = JWTUtil.getRefreshToken(userInfoByName.getId(),claims);
        }else {
            refreshToken= JWTUtil.getRefreshAppToken(userInfoByName.getId(),claims);
        }
        loginRespVO.setAccessToken(accessToken);
        loginRespVO.setRefreshToken(refreshToken);
        return loginRespVO;
    }

    @Override
    @Transactional
    public void register(RegisterReqVO vo) {
        if(!vo.getRegisterPassword().equals(vo.getRegisterWellPassword())){
            throw new APIException(RsCode.PASSWORD_ERROR);
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",vo.getRegisterUsername());
        List<User> users = this.baseMapper.selectList(wrapper);
        if(!users.isEmpty()){
            throw new APIException(RsCode.ACCOUNT_HAS_ERROR);
        }
        User user = new User();
        user.setUsername(vo.getRegisterUsername());
        user.setId(IDUtils.generateId());
        user.setCreateTime(new Date());
        String salt = PasswordUtils.getSalt();
        String ecdPwd = PasswordUtils.encode(vo.getRegisterPassword(),salt);
        user.setSalt(salt);
        user.setPassword(ecdPwd);
        user.setDeleted(1);
        user.setStatus(1);
        this.baseMapper.insert(user);
        UserRole userRole = new UserRole();
        userRole.setId(IDUtils.generateId());
        userRole.setCreateTime(new Date());
        userRole.setRoleId(User.roleId);
        userRole.setUserId(user.getId());
        userRoleService.save(userRole);
    }

    @Override
    public User getUser(String userId) {
        User user = this.baseMapper.getUser(userId);
        user.setSalt("it's null");
        user.setPassword("it's null");
        return user;
    }

    @Override
    @Transactional
    public void updatePwd(UserUpdatePwdReqVO vo, String accessToken, String refreshToken) {
        String userId = JWTUtil.getUserId(accessToken);
        User user = this.baseMapper.selectById(userId);
        if(user == null){
            throw new APIException(RsCode.TOKEN_ERROR);
        }
        /** 对旧密码进行匹配*/
        boolean matches = PasswordUtils.matches(user.getSalt(), vo.getOldPwd(), user.getPassword());
        if(!matches){
            throw new APIException(RsCode.OLD_PASSWORD_ERROR);
        }
        String password = PasswordUtils.encode(vo.getNewPwd(), user.getSalt());
        user.setUpdateId(userId);
        user.setUpdateTime(new Date());
        user.setPassword(password);
        this.baseMapper.updateById(user);

        /** 把当前业务toekn加入黑名单禁止再访问我们的系统资源*/
        redisUtil.set(Constant.JWT_ACCESS_TOKEN_BLACKLIST + accessToken,userId,JWTUtil.getRemainingTime(accessToken), TimeUnit.MILLISECONDS);

        /** 把当前刷新toekn加入黑名单 禁止再拿来刷新token*/
        redisUtil.set(Constant.JWT_REFRESH_TOKEN_BLACKLIST + refreshToken,userId,JWTUtil.getRemainingTime(refreshToken),TimeUnit.MILLISECONDS);

        /** 清楚用户授权数据缓存*/
        redisUtil.delete(Constant.IDENTIFY_CACHE_KEY + userId);
    }

    @Override
    @Transactional
    public void updateUser(User user, String userId) {
        this.baseMapper.updateById(user);
    }

    @Override
    public void logout(String accessToken, String refreshToken) {
        if(StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(refreshToken)){
            throw new APIException(RsCode.DATA_ERROR);
        }
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }

        String userId = JWTUtil.getUserId(accessToken);

        /** 把当前业务toekn加入黑名单禁止再访问我们的系统资源*/
        redisUtil.set(Constant.JWT_ACCESS_TOKEN_BLACKLIST + accessToken,userId,JWTUtil.getRemainingTime(accessToken), TimeUnit.MILLISECONDS);

        /** 把当前刷新toekn加入黑名单 禁止再拿来刷新token*/
        redisUtil.set(Constant.JWT_REFRESH_TOKEN_BLACKLIST + refreshToken,userId,JWTUtil.getRemainingTime(refreshToken),TimeUnit.MILLISECONDS);

        /** 清楚用户授权数据缓存*/
        redisUtil.delete(Constant.IDENTIFY_CACHE_KEY + userId);
    }

    @Override
    public String token(String refreshToken) {
        /** 判断刷新token是否过期，是否被加入黑名单*/
        if(!JWTUtil.validateToken(refreshToken) || redisUtil.hasKey(Constant.JWT_REFRESH_TOKEN_BLACKLIST+refreshToken)){
            throw new APIException(RsCode.TOKEN_ERROR);
        }
        String userId = JWTUtil.getUserId(refreshToken);
        String username = JWTUtil.getUserName(refreshToken);
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constant.JWT_ROLES_KEY,roleService.getRoleNames(userId));
        claims.put(Constant.JWT_PERMISSIONS_KEY,permissionService.getPerms(userId).stream().collect(Collectors.toSet()));
        claims.put(Constant.JWT_USER_NAME,username);
        String accessToken = JWTUtil.getAccessToken(userId,claims);
        return accessToken;
    }

    @Override
    @Transactional
    public void add(UserAddReqVO vo, String userId) {
        User user = new User();
        BeanUtils.copyProperties(vo,user);
        user.setId(IDUtils.generateId());
        String salt = PasswordUtils.getSalt();
        String password=PasswordUtils.encode(vo.getPassword(),salt);
        user.setPassword(password);
        user.setSalt(salt);
        user.setCreateTime(new Date());
        user.setCreateId(userId);
        this.save(user);
    }

    @Override
    @Transactional
    public void update(UserUpdateReqVO vo, String userId) {
        User user = new User();
        BeanUtils.copyProperties(vo,user);
        user.setUpdateTime(new Date());
        user.setUpdateId(userId);
        if(StringUtils.isEmpty(vo.getPassword())){
            user.setPassword(null);
        }else {
            String salt=PasswordUtils.getSalt();
            String password=PasswordUtils.encode(vo.getPassword(),salt);
            user.setSalt(salt);
            user.setPassword(password);
        }
        this.baseMapper.updateById(user);

        /** 用户是否被锁定*/
        if(vo.getStatus() == 2){
            redisUtil.set(Constant.ACCOUNT_LOCK_KEY + vo.getId(),vo.getId());
        }else{
            redisUtil.delete(Constant.ACCOUNT_LOCK_KEY + vo.getId());
        }
    }

    @Override
    public void delete(List<String> userIds, String userId) {
        List<User> users = new ArrayList<>();
        for(String id : userIds){
            User user = new User();
            user.setId(id);
            user.setUpdateId(userId);
            user.setUpdateTime(new Date());
            user.setDeleted(0);
            users.add(user);
        }
        this.updateBatchById(users);
        for(String id:userIds){

            /** 标记用户已经删除*/
            redisUtil.set(Constant.DELETED_USER_KEY + id,id,jwtConfig.getRefreshTokenExpireAppTime().toMillis(), TimeUnit.MILLISECONDS);

            /** 清楚用户授权数据缓存*/
            redisUtil.delete(Constant.IDENTIFY_CACHE_KEY + id);
        }
    }

    @Override
    public void updateUserRole(UserOwnRoleReqVO vo) {

        /** 删除用户角色关联的数据*/
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",vo.getUserId());
        this.userRoleService.remove(wrapper);

        /** 构建用户角色关联的数据*/
        List<UserRole> userRoles = new ArrayList<>();
        for(String roleId:vo.getRoleIds()){
            UserRole userRole = new UserRole();
            userRole.setId(IDUtils.generateId());
            userRole.setCreateTime(new Date());
            userRole.setUserId(vo.getUserId());
            userRole.setRoleId(roleId);
            userRoles.add(userRole);
        }
        this.userRoleService.saveBatch(userRoles);

        /** 通知要更新的用户刷新自己的业务的token*/
        redisUtil.set(Constant.JWT_REFRESH_KEY + vo.getUserId(),vo.getUserId(),jwtConfig.getAccessTokenExpireTime().toMillis(),TimeUnit.MILLISECONDS);

        /** 清除用户授权数据缓存*/
        redisUtil.delete(Constant.IDENTIFY_CACHE_KEY + vo.getUserId());
    }

    @Override
    public UserOwnRoleRespVO getUserOwnRole(String userId) {
        UserOwnRoleRespVO vo = new UserOwnRoleRespVO();
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId).eq("deleted",1);
        List<Role> roles = this.roleService.getRoleByUserId(userId);
        List<String> ownRoles = roles.stream().map(Role::getId).collect(Collectors.toList());
        QueryWrapper<Role> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("deleted",1);
        List<Role> allRole = this.roleService.getBaseMapper().selectList(wrapper1);
        vo.setOwnRoles(ownRoles);
        vo.setAllRole(allRole);
        return vo;
    }
}
