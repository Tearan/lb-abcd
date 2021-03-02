package com.lb.abcd.shiro.service;

import com.lb.abcd.shiro.entity.Role;
import com.lb.abcd.shiro.entity.User;
import com.lb.abcd.shiro.vo.request.AddRoleReqVO;
import com.lb.abcd.shiro.vo.request.RolePageReqVO;
import com.lb.abcd.shiro.vo.request.RoleUpdateReqVO;
import com.lb.abcd.shiro.vo.response.PageVO;
import com.lb.abcd.system.service.BaseService;

import java.util.List;

/**
 * @ClassName RoleService
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/1 14:40
 * @Version 1.0
 */
public interface RoleService extends BaseService<Role> {

    /**
     * 根据userId获取角色
     * @param userId
     * @return
     */
    public List<Role> getRoleByUserId(String userId);

    /**
     * 根据userId获取角色名称
     * @param userId
     * @return
     */
    public List<String> getRoleNames(String userId);

    /**
     * 分页获取所有角色信息
     * @param vo
     * @return
     */
    public PageVO<Role> getRoles(RolePageReqVO vo);

    /**
     * 新增角色
     * @param vo
     */
    public void add(AddRoleReqVO vo);

    /**
     * 删除角色
     * @param id
     */
    public void delete(String id);

    /**
     * 获取当个角色
     * @param roleId
     * @return
     */
    public Role getRole(String roleId);

    /**
     * 更新角色
     * @param vo
     */
    public void update(RoleUpdateReqVO vo);
}
