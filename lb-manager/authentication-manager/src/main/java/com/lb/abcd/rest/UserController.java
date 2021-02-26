package com.lb.abcd.rest;

import com.lb.abcd.shiro.entity.User;
import com.lb.abcd.shiro.service.UserService;
import com.lb.abcd.system.result.Rs;
import com.lb.abcd.system.result.RsCode;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author Terran
 * @Date 2021/2/1 14:27
 * @Version 1.0
 */
@Slf4j
@Api(value = "用户操作【增,删,改,查,设置权限】",tags = "用户操作【增,删,改,查,设置权限】")
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("save")
    public Rs save(@Valid @RequestBody User user){
        String salt = UUID.randomUUID().toString().substring(0,24);
        String password = new SimpleHash("MD5",user.getPassword(),salt,32).toString();
        user.setSalt(salt);
        user.setPassword(password);
        userService.save(user);
        return new Rs(RsCode.SUCCESS);
    }

    @GetMapping("2003")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Rs<String> unauthorized(HttpServletRequest httpServletRequest){
        String message = (String) httpServletRequest.getAttribute("msg");
        return new Rs<>(RsCode.NO_PERMISSION,message);
    }


}
