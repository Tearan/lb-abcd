package com.lb.abcd.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @ClassName JwtToken
 * @Description TODO
 * @Author Terran
 * @Date 2021/1/31 14:53
 * @Version 1.0
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
