package com.lb.abcd.jwt.config;

import com.lb.abcd.jwt.util.JWTUtil;
import org.springframework.stereotype.Component;

/**
 * @ClassName InitializerUtil
 * @Description 初始化JwtTokenUtil中的成员变量
 * @Author Terran
 * @Date 2021/3/1 16:17
 * @Version 1.0
 */

@Component
public class InitializerUtil {

    public InitializerUtil(JwtConfig jwtConfig){
        JWTUtil.setTokenSettings(jwtConfig);
    }
}
