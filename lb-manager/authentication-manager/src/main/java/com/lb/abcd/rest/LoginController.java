package com.lb.abcd.rest;

import com.lb.abcd.jwt.util.JWTUtil;
import com.lb.abcd.redis.util.RedisUtil;
import com.lb.abcd.shiro.entity.User;
import com.lb.abcd.shiro.service.UserService;
import com.lb.abcd.system.result.Rs;
import com.lb.abcd.system.result.RsCode;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

    @PostMapping("login")
    public Rs login(@Valid @RequestBody User user, HttpServletResponse response){
        String password = user.getPassword();
        User user1 = userService.findUserByUserName(user.getUsername());
        if (user1 == null){
            return new Rs(RsCode.USER_NOT_EXIST,"用户不存在");
        }

        password = new SimpleHash("MD5",password,user1.getSalt(),32).toString();
        if (password.equals(user1.getPassword())){
            /** 密码正确*/
            long timeMillis = System.currentTimeMillis();
            String token = JWTUtil.sign(user1.getUsername(), timeMillis);
            user1.setPassword(null);
            /** token放入redis*/
            redisUtil.set(user1.getUsername(), timeMillis, JWTUtil.REFRESH_EXPIRE_TIME);
            response.setHeader("Authorization", token);
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            return new Rs(RsCode.SUCCESS, user1);
        }
        return new Rs(RsCode.USER_LOGIN_FAIL,"密码错误");

    }

    /**
     * 登出
     * @return 是否登出
     */
    @GetMapping("/logout")
    public Rs logout(HttpServletRequest request){
        /** 清除redis中的RefreshToken即可 */
        String userId = JWTUtil.getUserId(request);
        redisUtil.del(userId);
        return new Rs(RsCode.SUCCESS);
    }
}
