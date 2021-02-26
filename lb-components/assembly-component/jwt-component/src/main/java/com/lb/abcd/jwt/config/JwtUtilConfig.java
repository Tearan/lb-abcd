package com.lb.abcd.jwt.config;

import com.lb.abcd.jwt.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @ClassName JwtUtilConfig
 * @Description TODO
 * @Author Terran
 * @Date 2021/2/25 13:48
 * @Version 1.0
 */
@Slf4j
@Configuration
public class JwtUtilConfig {

    @Value("${jwt.EXPIRE_TIME}")
    private Long expireTime;

    @Value("${jwt.REFRESH_EXPIRE_TIME}")
    private Long refreshExpireTime;

    @Value("${jwt.TOKEN_SECRET}")
    private String tokenSecret;

    @PostConstruct
    public void init() {
        log.info("设置token过期时间及密钥盐");
        JWTUtil.setProperties(expireTime * 1000 * 60, refreshExpireTime * 60, tokenSecret);
    }
}
