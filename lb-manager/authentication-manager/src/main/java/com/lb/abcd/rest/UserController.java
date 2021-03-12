package com.lb.abcd.rest;

import com.lb.abcd.shiro.entity.User;
import com.lb.abcd.shiro.service.UserService;
import com.lb.abcd.shiro.vo.request.*;
import com.lb.abcd.shiro.vo.response.UserOwnRoleRespVO;
import com.lb.abcd.system.annotation.MyLog;
import com.lb.abcd.system.constants.Constant;
import com.lb.abcd.system.rest.BaseController;
import com.lb.abcd.system.result.Rs;
import com.lb.abcd.system.vo.response.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author Terran
 * @Date 2021/2/1 14:27
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("api")
@Api(tags = "用户管理")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    @ApiOperation("获取当前用户信息")
    @MyLog(title = "用户管理",action = "获取当前用户信息")
    @RequiresPermissions("sys:user:list")
    public Rs<User> getUser(HttpServletRequest request){
        Rs result = Rs.success();
        String userId = getUserId(request);
        result.setData(this.userService.getUser(userId));
        return result;
    }

    @PutMapping("user/info")
    @ApiOperation("修改当前用户信息")
    @MyLog(title = "用户管理",action = "修改当前用户信息")
    @RequiresPermissions("sys:user:update")
    public Rs updateUser(@RequestBody User user,HttpServletRequest request){
        Rs result = Rs.success();
        String userId = getUserId(request);
        this.userService.updateUser(user,userId);
        return result;
    }

    @PutMapping("user/pwd")
    @ApiOperation("修改当前用户密码")
    @MyLog(title = "用户管理",action = "修改当前用户密码")
    @RequiresPermissions("sys:user:update")
    public Rs updateUserPWD(@RequestBody UserUpdatePwdReqVO vo, HttpServletRequest request){
        Rs result = Rs.success();
        String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
        String refreshToken = request.getHeader(Constant.REFRESH_TOKEN);
        this.userService.updatePwd(vo,accessToken,refreshToken);
        return result;
    }

    @GetMapping("/user/logout")
    @ApiOperation("退出系统")
    @MyLog(title = "用户管理",action = "退出系统")
    public Rs logout(HttpServletRequest request){
        Rs result = Rs.success();
        String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
        String refreshToken = request.getHeader(Constant.REFRESH_TOKEN);
        this.userService.logout(accessToken,refreshToken);
        return result;
    }

    @PostMapping("/users")
    @ApiOperation("用户列表")
    @MyLog(title = "用户管理",action = "获取用户列表")
    @RequiresPermissions("sys:user:list")
    public Rs<PageVO<User>> getAllUsers(@RequestBody UserPageReqVO vo){
        Rs result = Rs.success();
        result.setData(this.userService.pageInfo(vo));
        return result;
    }

    @PostMapping("/user")
    @ApiOperation("新增")
    @MyLog(title = "用户管理",action = "新增用户信息")
    @RequiresPermissions("sys:user:add")
    public Rs add(@Valid @RequestBody UserAddReqVO vo, HttpServletRequest request) {
        Rs result = Rs.success();
        String userId = getUserId(request);
        this.userService.add(vo,userId);
        return result;
    }

    @PutMapping("/user")
    @ApiOperation("更新")
    @MyLog(title = "用户管理",action = "更新用户信息")
    @RequiresPermissions("sys:user:update")
    public Rs update(@Valid @RequestBody UserUpdateReqVO vo, HttpServletRequest request) {
        Rs result = Rs.success();
        String userId = getUserId(request);
        this.userService.update(vo,userId);
        return result;
    }

    @DeleteMapping(value = "/user")
    @ApiOperation("删除")
    @MyLog(title = "用户管理",action = "删除用户信息")
    @RequiresPermissions("sys:user:delete")
    public Rs remove(@RequestBody List<String> userIds, HttpServletRequest request) {
        Rs result = Rs.success();
        String userId = getUserId(request);
        this.userService.delete(userIds,userId);
        return result;
    }

    @GetMapping("/user/token")
    @ApiOperation("获取业务token")
    @MyLog(title = "用户管理",action = "获取业务token")
    public Rs<String> token(HttpServletRequest request){
        Rs result = Rs.success();
        String refreshToken = request.getHeader(Constant.REFRESH_TOKEN);
        result.setData(this.userService.token(refreshToken));
        return result;
    }

    @GetMapping("/user/roles/{userId}")
    @RequiresPermissions("sys:user:role:update")
    @ApiOperation("查询用户拥有的角色")
    @MyLog(title = "用户管理",action = "查询用户拥有的角色")
    public Rs<UserOwnRoleRespVO> getRoles(@PathVariable("userId") String userId){
        Rs result = Rs.success();
        result.setData(this.userService.getUserOwnRole(userId));
        return result;
    }

    @PutMapping("/user/roles")
    @ApiOperation("赋予用户角色")
    @MyLog(title = "用户管理",action = "赋予用户角色")
    @RequiresPermissions("sys:user:role:update")
    public Rs roles(@RequestBody @Valid UserOwnRoleReqVO vo){
        Rs result = Rs.success();
        this.userService.updateUserRole(vo);
        return result;
    }
}
