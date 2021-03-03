package com.lb.abcd.rest;

import com.lb.abcd.shiro.entity.Dept;
import com.lb.abcd.shiro.service.DeptService;
import com.lb.abcd.shiro.vo.request.DeptAddReqVO;
import com.lb.abcd.shiro.vo.request.DeptUpdateReqVO;
import com.lb.abcd.shiro.vo.response.DeptRespNodeVO;
import com.lb.abcd.system.annotation.MyLog;
import com.lb.abcd.system.result.Rs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @ClassName DeptController
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/3 13:10
 * @Version 1.0
 */

@Slf4j
@RestController
@RequestMapping("api")
@Api(tags = "部门管理")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping("/depts")
    @ApiOperation("部门列表")
    @MyLog(title = "部门管理",action = "获取部门列表")
    @RequiresPermissions("sys:dept:list")
    public Rs<List<Dept>> getAllDept(){
        Rs result = Rs.success();
        result.setData(this.deptService.getDepts());
        return result;
    }

    @GetMapping("/dept/tree")
    @ApiOperation("部门树")
    @MyLog(title = "部门管理",action = "获取部门树")
    @RequiresPermissions(value = {"sys:user:update","sys:user:add","sys:dept:add","sys:dept:update"},logical = Logical.OR)
    public Rs<List<DeptRespNodeVO>> getDeptTree(@RequestParam(required = false) String deptId){
        Rs result = Rs.success();
        result.setData(this.deptService.deptTreeList(deptId));
        return result;
    }

    @PostMapping("/dept")
    @ApiOperation("新增")
    @MyLog(title = "部门管理",action = "新增部门")
    @RequiresPermissions("sys:dept:add")
    public Rs add(@Valid @RequestBody DeptAddReqVO vo) {
        Rs result = Rs.success();
        this.deptService.add(vo);
        return result;
    }

    @PutMapping("/dept")
    @ApiOperation("更新")
    @MyLog(title = "部门管理",action = "更新部门")
    @RequiresPermissions("sys:dept:update")
    public Rs update(@Valid @RequestBody DeptUpdateReqVO vo) {
        Rs result = Rs.success();
        this.deptService.update(vo);
        return result;
    }


    @DeleteMapping(value = "/dept/{deptId}")
    @ApiOperation("删除")
    @MyLog(title = "部门管理",action = "删除部门")
    @RequiresPermissions("sys:dept:delete")
    public Rs remove(@NotBlank(message = "{required}") @PathVariable String deptId) {
        Rs result = Rs.success();
        this.deptService.delete(deptId);
        return result;
    }

}
