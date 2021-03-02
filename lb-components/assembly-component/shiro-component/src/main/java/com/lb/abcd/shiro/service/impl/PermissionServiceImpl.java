package com.lb.abcd.shiro.service.impl;

import com.lb.abcd.shiro.dao.PermissionDao;
import com.lb.abcd.shiro.dao.RoleDao;
import com.lb.abcd.shiro.entity.Permission;
import com.lb.abcd.shiro.entity.Role;
import com.lb.abcd.shiro.service.PermissionService;
import com.lb.abcd.shiro.service.RoleService;
import com.lb.abcd.shiro.vo.request.PermissionAddReqVO;
import com.lb.abcd.shiro.vo.request.PermissionUpdateReqVO;
import com.lb.abcd.shiro.vo.response.PermissionRespNodeVO;
import com.lb.abcd.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PermissionServiceImpl
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/1 14:55
 * @Version 1.0
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionDao, Permission> implements PermissionService {

    @Override
    public List<Permission> getPermissionByUserId(String userId) {
        return null;
    }

    @Override
    public List<String> getPerms(String userId) {
        return null;
    }

    @Override
    public List<PermissionRespNodeVO> menu() {
        return null;
    }

    @Override
    public List<Permission> getAllPermission() {
        return null;
    }

    @Override
    public List<PermissionRespNodeVO> tree() {
        return null;
    }

    @Override
    public List<PermissionRespNodeVO> treeAll() {
        return null;
    }

    @Override
    public void add(PermissionAddReqVO vo) {

    }

    @Override
    public void update(PermissionUpdateReqVO vo) {

    }

    @Override
    public void delete(String menuId) {

    }
}
