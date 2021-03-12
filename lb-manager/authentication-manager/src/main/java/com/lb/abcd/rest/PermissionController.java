package com.lb.abcd.rest;

import com.lb.abcd.shiro.entity.Permission;
import com.lb.abcd.shiro.service.PermissionService;
import com.lb.abcd.shiro.vo.request.PermissionAddReqVO;
import com.lb.abcd.shiro.vo.request.PermissionUpdateReqVO;
import com.lb.abcd.shiro.vo.response.PermissionRespNodeVO;
import com.lb.abcd.system.annotation.MyLog;
import com.lb.abcd.system.rest.BaseController;
import com.lb.abcd.system.result.Rs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
* @author XueHeng
* @since 2020-09-17
*/
@RestController
@Api(tags = "权限管理")
@RequestMapping("api")
public class PermissionController extends BaseController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/menu")
    @ApiOperation("左侧菜单栏")
    public Rs<List<PermissionRespNodeVO>> menu(HttpServletRequest request){
        Rs result = Rs.success();
        String userId = getUserId(request);
        List<PermissionRespNodeVO> menu = this.permissionService.menu();
        result.setData(menu);
        return result;
    }

    @GetMapping("/permission/tree")
    @ApiOperation("权限树-菜单")
    @MyLog(title = "权限管理",action = "获取权限树-菜单")
    @RequiresPermissions(value = {"sys:permission:update","sys:permission:add"},logical = Logical.OR)
    public Rs<List<PermissionRespNodeVO>> getTree(){
        Rs result = Rs.success();
        result.setData(permissionService.tree());
        return result;
    }

    @GetMapping("/permission/tree/all")
    @ApiOperation("权限树-按钮")
    @MyLog(title = "权限管理",action = "获取权限树-按钮")
    @RequiresPermissions(value = {"sys:permission:update","sys:permission:add"},logical = Logical.OR)
    public Rs<List<PermissionRespNodeVO>> getTreeAll(){
        Rs result = Rs.success();
        result.setData(permissionService.treeAll());
        return result;
    }

    @GetMapping("/permissions")
    @ApiOperation("权限列表")
    @MyLog(title = "权限管理",action = "获取权限列表")
    @RequiresPermissions("sys:permission:list")
    public Rs<List<Permission>> getAllPermission(){
        Rs result = Rs.success();
        result.setData(this.permissionService.getAllPermission());
        return result;
    }

    @PostMapping("/permission")
    @ApiOperation("新增")
    @MyLog(title = "权限管理",action = "新增权限")
    @RequiresPermissions("sys:permission:add")
    public Rs add(@RequestBody @Valid PermissionAddReqVO vo) {
        Rs result = Rs.success();
        this.permissionService.add(vo);
        return result;
    }

    @PutMapping("/permission")
    @ApiOperation("编辑")
    @MyLog(title = "权限管理",action = "编辑权限")
    @RequiresPermissions("sys:permission:update")
    public Rs update(@Valid @RequestBody PermissionUpdateReqVO vo) {
        Rs result = Rs.success();
        this.permissionService.update(vo);
        return result;
    }

    @DeleteMapping(value = "/permission/{menuId}")
    @ApiOperation("删除")
    @MyLog(title = "权限管理",action = "删除权限")
    @RequiresPermissions("sys:permission:delete")
    public Rs remove(@NotBlank(message = "{required}") @PathVariable String menuId) {
        Rs result = Rs.success();
        this.permissionService.delete(menuId);
        return result;
    }
}
