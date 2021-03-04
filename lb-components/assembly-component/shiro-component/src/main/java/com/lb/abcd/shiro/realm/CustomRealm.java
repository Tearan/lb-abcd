package com.lb.abcd.shiro.realm;

import com.lb.abcd.jwt.JwtToken;
import com.lb.abcd.jwt.util.JWTUtil;
import com.lb.abcd.redis.util.RedisUtil;
import com.lb.abcd.shiro.service.PermissionService;
import com.lb.abcd.shiro.service.RoleService;
import com.lb.abcd.system.constants.Constant;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName CustomRealm
 * @Description 安全认证
 * @Author Terran
 * @Date 2021/3/1 13:55
 * @Version 1.0
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String accessToken = (String) principalCollection.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        String userId = JWTUtil.getUserId(accessToken);

        /** 刷新token存在或者业务token过期，则重新从数据库中获取权限信息*/
        if(redisUtil.hasKey(Constant.JWT_REFRESH_KEY + userId) && redisUtil.getExpire(Constant.JWT_REFRESH_KEY + userId, TimeUnit.MILLISECONDS) > JWTUtil.getRemainingTime(accessToken)){
            List<String> roleNames = roleService.getRoleNames(userId);
            if(roleNames != null && !roleNames.isEmpty()){
                info.addRoles(roleNames);
            }
            Set<String> permissions = permissionService.getPerms(userId).stream().collect(Collectors.toSet());
            if(permissions != null){
                info.addStringPermissions(permissions);
            }
        }

        /** 否则解析业务token，获取用户的角色和权限信息*/
        else {
            /** 返回roles */
            Claims claims = JWTUtil.getClaimsFromToken(accessToken);
            if(claims.get(Constant.JWT_ROLES_KEY) != null){
                info.addRoles((Collection<String>) claims.get(Constant.JWT_ROLES_KEY));
            }

            /** 返回permissions*/
            if(claims.get(Constant.JWT_PERMISSIONS_KEY) != null){
                info.addStringPermissions((Collection<String>) claims.get(Constant.JWT_PERMISSIONS_KEY));
            }
        }
        return info;
    }

    /**
     * 认证：
     *   会去CustomHashedCredentialsMatcher中进行认证
     *   认证通过后会从redisCache中获取权限信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken customUsernamePasswordToken = (JwtToken) authenticationToken;
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(customUsernamePasswordToken.getPrincipal(),customUsernamePasswordToken.getCredentials(),CustomRealm.class.getName());
        return info;
    }
}
