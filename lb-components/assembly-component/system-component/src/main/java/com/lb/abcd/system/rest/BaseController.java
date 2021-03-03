package com.lb.abcd.system.rest;

import com.lb.abcd.jwt.util.JWTUtil;
import com.lb.abcd.system.constants.Constant;
import com.lb.abcd.system.entity.BaseEntity;
import com.lb.abcd.system.service.BaseService;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName BaseController
 * @Description 公用的controller
 *                      基本CRUD方法 特殊需要重写即可
 * @Author Terran
 * @Date 2021/3/2 11:15
 * @Version 1.0
 */

public class BaseController {

    public String getUserId(HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String userId= JWTUtil.getUserId(accessToken);
        return userId;
    }
}
