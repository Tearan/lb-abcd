package com.lb.abcd.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lb.abcd.jwt.config.JwtConfig;
import com.lb.abcd.redis.util.RedisUtil;
import com.lb.abcd.shiro.dao.PermissionDao;
import com.lb.abcd.shiro.entity.Permission;
import com.lb.abcd.shiro.service.PermissionService;
import com.lb.abcd.shiro.util.MenuTreeUtil;
import com.lb.abcd.shiro.vo.request.PermissionAddReqVO;
import com.lb.abcd.shiro.vo.request.PermissionUpdateReqVO;
import com.lb.abcd.shiro.vo.response.PermissionRespNodeVO;
import com.lb.abcd.system.constants.Constant;
import com.lb.abcd.system.exception.APIException;
import com.lb.abcd.system.result.RsCode;
import com.lb.abcd.system.util.IDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName PermissionServiceImpl
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/1 14:55
 * @Version 1.0
 */

@Slf4j
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, Permission> implements PermissionService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public List<Permission> getPermissionByUserId(String userId) {
        return this.baseMapper.getPermissionByUserId(userId);
    }

    @Override
    public List<String> getPerms(String userId) {
        List<String> perms = new ArrayList<>();
        List<Permission> sysPermissions = getPermissionByUserId(userId);
        sysPermissions.stream().forEach(sysPermission -> {
            perms.add(sysPermission.getPerms());
        });
        return perms;
    }

    @Override
    public List<PermissionRespNodeVO> menu() {
        List<Permission> permissions = this.baseMapper.getMenu();
        return MenuTreeUtil.getMenuTree(permissions,true);
    }

    @Override
    public List<Permission> getAllPermission() {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted",1).orderByDesc("order_num");
        List<Permission> permissions = this.baseMapper.selectList(wrapper);
        if(!permissions.isEmpty()){
            for(Permission permission:permissions){
                Permission parent = this.baseMapper.selectById(permission.getPid());
                if(parent!=null){
                    permission.setPidName(parent.getName());
                }
            }
        }
        return permissions;
    }

    @Override
    public List<PermissionRespNodeVO> tree() {
        List<PermissionRespNodeVO> tree=new ArrayList<>();
        List<Permission> permissions = this.baseMapper.getMenu();
        PermissionRespNodeVO parent = new PermissionRespNodeVO();
        parent.setId("0");
        parent.setTitle("默认顶级菜单");
        parent.setChildren(MenuTreeUtil.getMenuTree(permissions,true));
        tree.add(parent);
        return tree;
    }

    @Override
    public List<PermissionRespNodeVO> treeAll() {
        List<PermissionRespNodeVO> tree=new ArrayList<>();
        List<Permission> permissions = this.baseMapper.getMenu();
        PermissionRespNodeVO parent = new PermissionRespNodeVO();
        parent.setId("0");
        parent.setTitle("默认顶级菜单");
        parent.setChildren(MenuTreeUtil.getMenuTree(permissions,false));
        tree.add(parent);
        return tree;
    }

    @Override
    @Transactional
    public void add(PermissionAddReqVO vo) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(vo,permission);
        /** 验证父级类型*/
        verifyForm(permission);
        permission.setId(IDUtils.generateId());
        permission.setCreateTime(new Date());
        int i = this.baseMapper.insert(permission);
        if(i != 1){
            throw new APIException(RsCode.DATA_ERROR);
        }
    }

    @Override
    @Transactional
    public void update(PermissionUpdateReqVO vo) {
        Permission permission = this.baseMapper.selectById(vo.getId());
        if(permission==null){
            log.info("传入的id在数据库中不存在");
            throw new APIException(RsCode.DATA_ERROR);
        }

        /** 如果更新的权限下面有子集或者权限状态不同，则禁止更新*/
        if(!permission.getPid().equals(vo.getPid())||!permission.getStatus().equals(vo.getStatus())){
            QueryWrapper<Permission> wrapper = new QueryWrapper<>();
            wrapper.eq("deleted",1).eq("pid",vo.getId());
            List<Permission> permissions = this.baseMapper.selectList(wrapper);
            if(!permissions.isEmpty()){
                throw new APIException(RsCode.OPERATION_MENU_PERMISSION_UPDATE);
            }
        }

        Permission update = new Permission();
        BeanUtils.copyProperties(vo,update);
        update.setUpdateTime(new Date());
        int i = this.baseMapper.updateById(update);
        if(i != 1){
            throw new APIException(RsCode.OPERATION_ERROR);
        }

        /** 当权限更新的时候，redis中的权限也应该更新*/
        if(!permission.getPerms().equals(vo.getPerms()) || permission.getStatus() != vo.getStatus()){
            List<String> userIds = this.baseMapper.getUserIds(vo.getId());
            for(String userId : userIds){

                /** 主动去刷新 token*/
                redisUtil.set(Constant.JWT_REFRESH_KEY + userId,userId,jwtConfig.getAccessTokenExpireTime().toMillis(), TimeUnit.MILLISECONDS);

                /** 清除用户授权数据缓存*/
                redisUtil.delete(Constant.IDENTIFY_CACHE_KEY + userId);
            }
        }
    }

    @Override
    @Transactional
    public void delete(String menuId) {
        /** 如果该权限下面有子权限，则不允许删除*/
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted",1).eq("pid",menuId);
        List<Permission> permissions = this.baseMapper.selectList(wrapper);
        if(!permissions.isEmpty()){
            throw new APIException(RsCode.ROLE_PERMISSION_RELATION);
        }
        /** 删除权限*/
        Permission permission = new Permission();
        permission.setId(menuId);
        permission.setUpdateTime(new Date());
        permission.setDeleted(0);
        int i = this.baseMapper.updateById(permission);
        if(i != 1){
            throw new APIException(RsCode.OPERATION_ERROR);
        }
        /** 当权限删除的时候，redis中的权限也应该更新*/
        List<String> userIds = this.baseMapper.getUserIds(menuId);
        for(String userId : userIds){

            /** 主动去刷新 token*/
            redisUtil.set(Constant.JWT_REFRESH_KEY + userId,userId,jwtConfig.getAccessTokenExpireTime().toMillis(), TimeUnit.MILLISECONDS);

            /** 清除用户授权数据缓存*/
            redisUtil.delete(Constant.IDENTIFY_CACHE_KEY + userId);
        }
    }

    /**
     * 验证父级类型
     * @param permission
     */
    private void verifyForm(Permission permission){
        Permission parent = this.baseMapper.selectById(permission.getPid());
        if(parent != null){
            switch (parent.getType()){
                case 1:

                    if(parent != null){
                        if(parent.getType() != 1){
                            throw new APIException(RsCode.OPERATION_MENU_PERMISSION_CATALOG_ERROR);
                        }
                    }else if (!permission.getPid().equals("0")){
                        throw new APIException(RsCode.OPERATION_MENU_PERMISSION_CATALOG_ERROR);
                    }
                    break;

                case 2:
                    if(parent==null||parent.getType() != 2){
                        throw new APIException(RsCode.OPERATION_MENU_PERMISSION_MENU_ERROR);
                    }
                    if(StringUtils.isEmpty(permission.getUrl())){
                        throw new APIException(RsCode.OPERATION_MENU_PERMISSION_URL_NOT_NULL);
                    }
                    break;

                case 3:
                    if(parent == null || parent.getType() != 3){
                        throw new APIException(RsCode.OPERATION_MENU_PERMISSION_BTN_ERROR);
                    }
                    if(StringUtils.isEmpty(permission.getPerms())){
                        throw new APIException(RsCode.OPERATION_MENU_PERMISSION_URL_PERMS_NULL);
                    }
                    if(StringUtils.isEmpty(permission.getUrl())){
                        throw new APIException(RsCode.OPERATION_MENU_PERMISSION_URL_NOT_NULL);
                    }
                    if(StringUtils.isEmpty(permission.getMethod())){
                        throw new APIException(RsCode.OPERATION_MENU_PERMISSION_URL_METHOD_NULL);
                    }
                    if(StringUtils.isEmpty(permission.getCode())){
                        throw new APIException(RsCode.OPERATION_MENU_PERMISSION_URL_CODE_NULL);
                    }
                    break;
            }
        }
    }
}
