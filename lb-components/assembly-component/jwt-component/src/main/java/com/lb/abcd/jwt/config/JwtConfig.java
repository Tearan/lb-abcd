package com.lb.abcd.jwt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;


/**
 * @ClassName JwtUtilConfig
 * @Description TODO
 * @Author Terran
 * @Date 2021/2/25 13:48
 * @Version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    private Duration accessTokenExpireTime;

    private Duration refreshTokenExpirePcTime;

    private Duration refreshTokenExpireAppTime;

    private String tokenSecret;

    private String issuer;
}
