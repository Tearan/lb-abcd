package com.lb.abcd.rest;

import com.lb.abcd.shiro.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author Terran
 * @Date 2021/2/1 14:27
 * @Version 1.0
 */
@Slf4j
@Api(value = "获取token",tags = "获取token")
@RestController
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;
}
