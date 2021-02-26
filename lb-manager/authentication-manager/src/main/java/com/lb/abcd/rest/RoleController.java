package com.lb.abcd.rest;

import com.lb.abcd.system.result.Rs;
import com.lb.abcd.system.result.RsCode;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RoleController
 * @Description TODO
 * @Author Terran
 * @Date 2021/2/23 15:00
 * @Version 1.0
 */
@Slf4j
@Api(value = "获取token",tags = "获取token")
@RequestMapping("role")
@RestController
public class RoleController {

    @PostMapping("roleTest")
    public Rs save(){
        return new Rs(RsCode.SUCCESS);
    }
}
