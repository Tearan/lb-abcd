package com.lb.abcd.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lb.abcd.jwt.config.JwtConfig;
import com.lb.abcd.redis.util.RedisUtil;
import com.lb.abcd.shiro.dao.RoleDao;
import com.lb.abcd.shiro.dao.UserRoleDao;
import com.lb.abcd.shiro.entity.Role;
import com.lb.abcd.shiro.entity.RolePermission;
import com.lb.abcd.shiro.service.PermissionService;
import com.lb.abcd.shiro.service.RolePermissionService;
import com.lb.abcd.shiro.service.RoleService;
import com.lb.abcd.shiro.vo.request.AddRoleReqVO;
import com.lb.abcd.shiro.vo.request.RolePageReqVO;
import com.lb.abcd.shiro.vo.request.RoleUpdateReqVO;
import com.lb.abcd.shiro.vo.response.PermissionRespNodeVO;
import com.lb.abcd.system.constants.Constant;
import com.lb.abcd.system.exception.APIException;
import com.lb.abcd.system.result.RsCode;
import com.lb.abcd.system.util.IDUtils;
import com.lb.abcd.system.util.PageUtil;
import com.lb.abcd.system.vo.response.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName RoleServiceImpl
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/1 14:42
 * @Version 1.0
 */

@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private PermissionService permissionService;

    @Override
    public List<Role> getRoleByUserId(String userId) {
        return this.baseMapper.getRoleByUserId(userId);
    }

    @Override
    public List<String> getRoleNames(String userId) {
        List<String> roleNames = new ArrayList<>();
        List<Role> sysRoles = getRoleByUserId(userId);
        sysRoles.stream().forEach(role->{
            roleNames.add(role.getName());
        });
        return roleNames;
    }

    @Override
    public PageVO<Role> getRoles(RolePageReqVO vo) {
        Page<Role> page = new Page<>(vo.getPageNum(),vo.getPageSize());
        IPage<Role> roles = this.baseMapper.getRoles(page,vo);
        return PageUtil.getPage(roles);
    }

    @Override
    @Transactional
    public void add(AddRoleReqVO vo) {
        Role role = new Role();
        String roleId = IDUtils.generateId();
        role.setCreateTime(new Date());
        role.setId(roleId);
        BeanUtils.copyProperties(vo,role);
        this.save(role);

        /** 关联角色权限关系*/
        List<String> permissionIds = vo.getPermissions();
        if(!permissionIds.isEmpty()) {
            List<RolePermission> rolePermissions = new ArrayList<>();
            for (String permissionId : permissionIds) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setId(IDUtils.generateId());
                rolePermission.setCreateTime(new Date());
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(permissionId);
                rolePermissions.add(rolePermission);
            }
            this.rolePermissionService.saveBatch(rolePermissions);
        }
    }

    @Override
    @Transactional
    public void delete(String id) {
        this.removeById(id);
    }

    @Override
    public Role getRole(String roleId) {
        Role role = this.baseMapper.selectById(roleId);
        if(role==null){
            throw new APIException(RsCode.DATA_ERROR);
        }

        /** 获取权限树-按钮*/
        List<PermissionRespNodeVO> permissionList = permissionService.treeAll();

        /** 获取该角色拥有的菜单权限*/
        Set<String> permissionIds = rolePermissionService.getPermissionIds(roleId).stream().collect(Collectors.toSet());
        this.setChecked(permissionList,permissionIds);
        role.setPermissionRespNode(permissionList);
        return role;
    }

    /**
     * 拥有的菜单标记为选中
     * 子集选中从它往上到跟目录都被选中，父级选中从它到它所有的叶子节点都会被选中
     * @param permissionList
     * @param permissionIds
     */
    private void setChecked(List<PermissionRespNodeVO> permissionList,Set<String> permissionIds){
        for(PermissionRespNodeVO node:permissionList) {
            if (permissionIds.contains(node.getId()) && (node.getChildren() == null || node.getChildren().isEmpty())){
                node.setChecked(true);
            }
            setChecked((List<PermissionRespNodeVO>) node.getChildren(),permissionIds);
        }
    }

    @Override
    @Transactional
    public void update(RoleUpdateReqVO vo) {
        Role role = this.baseMapper.selectById(vo.getId());
        if(role == null){
            throw new APIException(RsCode.DATA_ERROR);
        }
        BeanUtils.copyProperties(vo,role);
        role.setUpdateTime(new Date());
        this.updateById(role);

        /***
         * 关联角色和权限关系
         * 先删除角色和权限关系
         */
        this.rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("role_id",role.getId()));

        /** 后新增角色和权限关系 */
        List<String> permissionIds = vo.getPermissions();
        if(!permissionIds.isEmpty()) {
            List<RolePermission> rolePermissions = new ArrayList<>();
            for (String permissionId : permissionIds) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setId(IDUtils.generateId());
                rolePermission.setRoleId(role.getId());
                rolePermission.setCreateTime(new Date());
                rolePermission.setPermissionId(permissionId);
                rolePermissions.add(rolePermission);
            }
            this.rolePermissionService.saveBatch(rolePermissions);
        }

        /** 通知拥有该角色的用户更新业务token*/
        List<String> userIds = this.userRoleDao.getUserIds(vo.getId());
        for(String userId : userIds){

            /** 通知拥有该角色的用户更新业务token*/
            redisUtil.set(Constant.JWT_REFRESH_KEY + userId,userId,jwtConfig.getAccessTokenExpireTime().toMillis(), TimeUnit.MILLISECONDS);

            /** 清楚用户授权数据缓存*/
            redisUtil.delete(Constant.IDENTIFY_CACHE_KEY + userId);
        }
    }
}
