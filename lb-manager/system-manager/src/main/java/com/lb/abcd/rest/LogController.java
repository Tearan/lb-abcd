package com.lb.abcd.rest;

import com.lb.abcd.entity.SysLog;
import com.lb.abcd.service.SysLogService;
import com.lb.abcd.system.annotation.MyLog;
import com.lb.abcd.system.result.Rs;
import com.lb.abcd.system.vo.response.PageVO;
import com.lb.abcd.vo.request.SysLogPageReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @ClassName LogController
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/3 13:13
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("api")
@Api(tags = "日志管理")
public class LogController {

    @Autowired
    private SysLogService sysLogService;

    @PostMapping("/logs")
    @ApiOperation("日志列表")
    @MyLog(title = "日志管理",action = "获取日志列表")
    @RequiresPermissions("sys:log:list")
    public Rs<PageVO<SysLog>> getAllDept(@RequestBody @Valid SysLogPageReqVO vo){
        Rs result = Rs.success();
        result.setData(this.sysLogService.selectAll(vo));
        return result;
    }


    @DeleteMapping(value = "/log")
    @ApiOperation("删除日志")
    @MyLog(title = "日志管理",action = "删除日志")
    @RequiresPermissions("sys:log:delete")
    public Rs remove(@RequestBody @Valid List<String> logIds) {
        Rs result = Rs.success();
        this.sysLogService.delete(logIds);
        return result;
    }
}
