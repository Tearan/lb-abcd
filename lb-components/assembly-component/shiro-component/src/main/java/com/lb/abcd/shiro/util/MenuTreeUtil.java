package com.lb.abcd.shiro.util;

import com.lb.abcd.shiro.entity.Permission;
import com.lb.abcd.shiro.vo.response.PermissionRespNodeVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MenuTreeUtil
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/3 13:21
 * @Version 1.0
 */
public class MenuTreeUtil {

    /**
     * 获取菜单树
     *      type = true 递归遍历到菜单
     *      type = false 递归遍历到按钮
     * @param permissions
     * @param type
     * @return
     */
    public static List<PermissionRespNodeVO> getMenuTree(List<Permission> permissions, boolean type){
        List<PermissionRespNodeVO> list=new ArrayList<>();
        if(permissions==null||permissions.isEmpty()){
            return list;
        }
        for(Permission permission:permissions){
            if(permission.getPid().equals("0")){
                PermissionRespNodeVO parent = new PermissionRespNodeVO();
                BeanUtils.copyProperties(permission,parent);
                parent.setTitle(permission.getName());
                if(type){
                    parent.setChildren(getChildMenu(parent.getId(),permissions));
                }else{
                    parent.setChildren(getChildBtn(parent.getId(),permissions));
                }
                list.add(parent);
            }
        }
        return list;
    }

    /**
     * 递归遍历到按钮
     * @param pid
     * @param permissions
     * @return
     */
    private static List<PermissionRespNodeVO> getChildBtn(String pid,List<Permission> permissions){
        List<PermissionRespNodeVO> list=new ArrayList<>();
        for(Permission permission:permissions){
            if(permission.getPid().equals(pid)){
                PermissionRespNodeVO node=new PermissionRespNodeVO();
                BeanUtils.copyProperties(permission,node);
                node.setTitle(permission.getName());
                node.setChildren(getChildBtn(permission.getId(),permissions));
                list.add(node);
            }
        }
        return list;
    }

    /**
     * 递归遍历到菜单
     * @param pid
     * @param permissions
     * @return
     */
    private static List<PermissionRespNodeVO> getChildMenu(String pid,List<Permission> permissions){
        List<PermissionRespNodeVO> list=new ArrayList<>();
        for(Permission permission:permissions){
            if(permission.getPid().equals(pid)&&permission.getType()!=3){
                PermissionRespNodeVO node=new PermissionRespNodeVO();
                BeanUtils.copyProperties(permission,node);
                node.setTitle(permission.getName());
                node.setChildren(getChildMenu(permission.getId(),permissions));
                list.add(node);
            }
        }
        return list;
    }

}
