package com.lb.abcd.rest;

import com.lb.abcd.shiro.service.UserService;
import com.lb.abcd.shiro.vo.request.LoginReqVO;
import com.lb.abcd.shiro.vo.request.RegisterReqVO;
import com.lb.abcd.shiro.vo.response.LoginRespVO;
import com.lb.abcd.system.result.Rs;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author Terran
 * @Date 2021/2/25 15:34
 * @Version 1.0
 */

@Slf4j
@Api(tags = "登录管理")
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Rs<LoginRespVO> login(@RequestBody @Valid LoginReqVO vo, HttpServletRequest request){
        Rs result = Rs.success();
       // HttpSession session = request.getSession();
       // validateCodeService.check(session.getId(),vo.getCode());
        result.setData(userService.login(vo));
        return result;
    }

    @PostMapping("/register")
    public Rs register(@RequestBody @Valid RegisterReqVO vo){
        Rs result = Rs.success();
        userService.register(vo);
        return result;
    }
}
