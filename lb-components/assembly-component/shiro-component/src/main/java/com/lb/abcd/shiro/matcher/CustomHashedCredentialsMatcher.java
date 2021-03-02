package com.lb.abcd.shiro.matcher;

import com.lb.abcd.jwt.JwtToken;
import com.lb.abcd.jwt.util.JWTUtil;
import com.lb.abcd.redis.util.RedisUtil;
import com.lb.abcd.system.constants.Constant;
import com.lb.abcd.system.exception.APIException;
import com.lb.abcd.system.result.RsCode;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName CustomHashedCredentialsMatcher
 * @Description 用户校验
 * @Author Terran
 * @Date 2021/3/1 13:49
 * @Version 1.0
 */
public class CustomHashedCredentialsMatcher extends HashedCredentialsMatcher {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        JwtToken customUsernamePasswordToken= (JwtToken) token;

        String accessToken= (String) customUsernamePasswordToken.getPrincipal();

        String userId= JWTUtil.getUserId(accessToken);

        /** 判断用户是否被锁定*/
        if(redisUtil.hasKey(Constant.ACCOUNT_LOCK_KEY+userId)){
            throw new APIException(RsCode.ACCOUNT_LOCK);
        }

        /** 判断用户是否被删除*/
        if(redisUtil.hasKey(Constant.DELETED_USER_KEY+userId)){
            throw new APIException(RsCode.ACCOUNT_HAS_DELETED_ERROR);
        }

        /** 判断token 是否主动登出*/
        if(redisUtil.hasKey(Constant.JWT_ACCESS_TOKEN_BLACKLIST+accessToken)){
            throw new APIException(RsCode.TOKEN_ERROR);
        }

        /** 判断token是否通过校验*/
        if(!JWTUtil.validateToken(accessToken)){
            throw new APIException(RsCode.TOKEN_PAST_DUE);
        }

        /** 判断这个登录用户是否要主动去刷新*/
        if(redisUtil.hasKey(Constant.JWT_REFRESH_KEY+userId)){
            if(redisUtil.getExpire(Constant.JWT_REFRESH_KEY+userId, TimeUnit.MILLISECONDS)>JWTUtil.getRemainingTime(accessToken)){
                throw new APIException(RsCode.TOKEN_PAST_DUE);
            }
        }
        return true;
    }
}
