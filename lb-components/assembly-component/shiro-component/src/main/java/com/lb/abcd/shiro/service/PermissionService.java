package com.lb.abcd.shiro.service;

import com.lb.abcd.shiro.entity.Permission;
import com.lb.abcd.shiro.entity.Role;
import com.lb.abcd.shiro.vo.request.PermissionAddReqVO;
import com.lb.abcd.shiro.vo.request.PermissionUpdateReqVO;
import com.lb.abcd.shiro.vo.response.PermissionRespNodeVO;
import com.lb.abcd.system.service.BaseService;

import java.util.List;

/**
 * @ClassName PermissionService
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/1 14:42
 * @Version 1.0
 */
public interface PermissionService extends BaseService<Permission> {

    /**
     * 根据userId获取权限信息
     * @param userId
     * @return
     */
    public List<Permission> getPermissionByUserId(String userId);

    /**
     * 根据userId获取权限标记符
     * @param userId
     * @return
     */
    public List<String> getPerms(String userId);

    /**
     * 左侧菜单栏
     * @return
     */
    public List<PermissionRespNodeVO> menu();

    /**
     * 获取所有权限数据
     * @return
     */
    public List<Permission> getAllPermission();

    /**
     * 权限树-菜单
     * @return
     */
    public List<PermissionRespNodeVO> tree();

    /**
     * 权限树-按钮
     * @return
     */
    public List<PermissionRespNodeVO> treeAll();

    /**
     * 新增权限
     * @param vo
     */
    public void add(PermissionAddReqVO vo);

    /**
     * 修改权限
     * @param vo
     */
    public void update(PermissionUpdateReqVO vo);

    /**
     * 删除权限
     * @param menuId
     */
    public void delete(String menuId);
}
