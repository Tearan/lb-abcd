package com.lb.abcd.shiro.config;

import com.lb.abcd.jwt.JwtToken;
import com.lb.abcd.jwt.util.JWTUtil;
import com.lb.abcd.shiro.entity.User;
import com.lb.abcd.shiro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义权限控制类
 * @ClassName MyRealm
 * @Description 通过redis的缓存时间来判断token是否失效,
 *              如果存在则刷新缓存时间，延长token有效期
 * @Author Terran
 * @Date 2021/1/31 14:05
 * @Version 1.0
 */
@Slf4j
@Service
public class MyRealm extends AuthorizingRealm {

    @Resource
    UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 用户授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("用户授权");
        String userId = JWTUtil.getUserId(principalCollection.toString());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User user = userService.findUserByUserName(userId);
        Integer authority = user.getAuthority();
        Set<String> role = new HashSet<>();

        /**
         * 权限说明：
         *        2.查看权限;
         *        3.查看和编辑权限;
         *        6.查看和授权权限;
         *        7.查看、编辑、授权权限
         */
        switch (authority) {
            case 2:
                role.add("view");
                break;
            case 3:
                role.add("view");
                role.add("edit");
                break;
            case 6:
                role.add("view");
                role.add("authorization");
                break;
            case 7:
                role.add("view");
                role.add("edit");
                role.add("authorization");
                break;
            default:
                role.add("tourist");
        }
        /**
         * 设置角色集合
         * 设置权限方式与设置角色类似使用info.setStringPermissions();
         * */
        info.setRoles(role);
        return info;
    }

    /** 用户身份认证 */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("身份认证");
        String token = (String) authenticationToken.getCredentials();
        String userId = JWTUtil.getUserId(token);
        log.info( userId + "");
        User user = userService.findUserByUserName(userId);
        if (user == null) {
            throw new AuthenticationException("认证失败！");
        }
        return new SimpleAuthenticationInfo(token, token, "MyRealm");
    }


   /* private UserService userService;

    private RedisUtil redisUtils;

    @Autowired
    public MyRealm(UserService userService, RedisUtil redisUtils) {
        super();
        this.userService = userService;
        this.redisUtils = redisUtils;
    }

    *//**
     * 必须重写此方法，不然Shiro会报错
     * @param token
     * @return
     *//*
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    *//**
     * 认证.登录
     * @param auth
     * @return
     * @throws AuthenticationException
     *//*
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        *//** 解密获得username *//*
        String username = JWTUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("令牌无效");
        }
        User userBean = (User) redisUtils.get(token);
        if (userBean == null) {
            throw new AuthenticationException("令牌已过期");
        } else {
            redisUtils.expire(token, 60);
            return new SimpleAuthenticationInfo(token, token, "MyRealm");
        }
    }

    *//**
     * 授权
     * @param principal
     * @return
     *//*
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        String token = (String)principal.getPrimaryPrincipal();
        String username = JWTUtil.getUsername(token);
        User user = userService.findUserByUserName(username);
        List<String> permissions = new ArrayList<>();
        List<String> rolesName = new ArrayList<>();
        List<Role> roles = user.getRoles();
        if(roles.size() > 0) {
            for(Role role : roles) {
                rolesName.add(role.getRname());
                List<Module> modules = role.getModules();
                if(modules.size()>0) {
                    for(Module module : modules) {
                        permissions.add(module.getMname());
                    }
                }
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        *//** 将角色放入shiro中*//*
        info.addRoles(rolesName);
        *//** 将权限放入shiro中*//*
        info.addStringPermissions(permissions);
        return info;
    }
*/
}
