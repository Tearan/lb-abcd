package com.lb.abcd.rest;

import com.lb.abcd.shiro.entity.Role;
import com.lb.abcd.shiro.service.RoleService;
import com.lb.abcd.shiro.vo.request.AddRoleReqVO;
import com.lb.abcd.shiro.vo.request.RolePageReqVO;
import com.lb.abcd.shiro.vo.request.RoleUpdateReqVO;
import com.lb.abcd.system.annotation.MyLog;
import com.lb.abcd.system.result.Rs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @ClassName RoleController
 * @Description TODO
 * @Author Terran
 * @Date 2021/2/23 15:00
 * @Version 1.0
 */
@Slf4j
@Api(tags = "角色管理")
@RequestMapping("api")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/roles")
    @RequiresPermissions("sys:role:list")
    @ApiOperation("角色列表")
    @MyLog(title = "角色管理",action = "获取角色列表")
    public Rs<List<Role>> getAllRoles(@RequestBody @Valid RolePageReqVO vo){
        Rs result = Rs.success();
        result.setData(this.roleService.getRoles(vo));
        return result;
    }

    @GetMapping("/role/{id}")
    @RequiresPermissions("sys:role:list")
    @ApiOperation("单个角色信息")
    @MyLog(title = "角色管理",action = "获取单个角色信息")
    public Rs<Role> getRole(@NotBlank(message = "{required}") @PathVariable String id){
        Rs result = Rs.success();
        result.setData(this.roleService.getRole(id));
        return result;
    }

    @PostMapping("/role")
    @ApiOperation("新增")
    @MyLog(title = "角色管理",action = "新增角色")
    @RequiresPermissions("sys:role:add")
    public Rs add(@Valid @RequestBody AddRoleReqVO vo) {
        Rs result = Rs.success();
        this.roleService.add(vo);
        return result;
    }

    @PutMapping("/role")
    @ApiOperation("修改")
    @MyLog(title = "角色管理",action = "修改角色")
    @RequiresPermissions("sys:role:update")
    public Rs update(@Valid @RequestBody RoleUpdateReqVO vo) {
        Rs result = Rs.success();
        this.roleService.update(vo);
        return result;
    }

    @DeleteMapping(value = "/role/{id}")
    @ApiOperation("删除")
    @MyLog(title = "角色管理",action = "删除角色")
    @RequiresPermissions("sys:role:delete")
    public Rs remove(@NotBlank(message = "{required}") @PathVariable String id) {
        Rs result = Rs.success();
        this.roleService.delete(id);
        return result;
    }
}
