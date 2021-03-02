package com.lb.abcd.rest;

import com.lb.abcd.shiro.service.UserService;
import com.lb.abcd.system.annotation.MyLog;
import com.lb.abcd.system.rest.BaseController;
import com.lb.abcd.system.result.Rs;
import com.lb.abcd.system.result.RsCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RestController
@RequestMapping("test")
@Api(tags = "用户管理")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    @ApiOperation("新增")
    @MyLog(title = "用户管理",action = "新增用户信息")
    @RequiresPermissions("sys:user:add")
    public Rs add(HttpServletRequest request) {
        Rs result = Rs.success();
        String userId = getUserId(request);
        return result;
    }

    @PostMapping("/test")
    @ApiOperation("新增")
    @MyLog(title = "用户管理",action = "新增用户信息")
    @RequiresPermissions("sys:user:add")
    public Rs test() {
        Rs rs = Rs.success();
        this.userService.getUser();
        return rs;
    }


}
