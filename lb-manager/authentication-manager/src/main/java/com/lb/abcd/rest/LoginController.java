package com.lb.abcd.rest;

import com.lb.abcd.redis.util.RedisUtil;
import com.lb.abcd.shiro.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author Terran
 * @Date 2021/2/25 15:34
 * @Version 1.0
 */

@Slf4j
@Api(value = "系统【登入登出】",tags = "系统【登入登出】")
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    /*@PostMapping("login")
    public Rs login(@Valid @RequestBody SysUser sysUser, HttpServletResponse response){
        String password = sysUser.getPassword();
        SysUser sysUser1 = userService.findUserByUserName(sysUser.getUsername());
        if (sysUser1 == null){
            return new Rs(RsCode.SUCCESS,"用户不存在");
        }

        password = new SimpleHash("MD5",password, sysUser1.getSalt(),32).toString();
        if (password.equals(sysUser1.getPassword())){
            *//** 密码正确*//*
            long timeMillis = System.currentTimeMillis();
            String token = JWTUtil.sign(sysUser1.getUsername(), timeMillis);
            sysUser1.setPassword(null);
            *//** token放入redis*//*
            redisUtil.set(sysUser1.getUsername(), timeMillis, JWTUtil.REFRESH_EXPIRE_TIME);
            response.setHeader("Authorization", token);
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            return new Rs(RsCode.SUCCESS, sysUser1);
        }
        return new Rs(RsCode.USER_LOGIN_FAIL,"密码错误");

    }

    *//**
     * 登出
     * @return 是否登出
     *//*
    @GetMapping("/logout")
    public Rs logout(HttpServletRequest request){
        *//** 清除redis中的RefreshToken即可 *//*
        String userId = JWTUtil.getUserId(request);
        redisUtil.del(userId);
        return new Rs(RsCode.SUCCESS);
    }*/
}
