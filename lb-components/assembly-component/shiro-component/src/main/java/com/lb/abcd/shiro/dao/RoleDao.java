package com.lb.abcd.shiro.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lb.abcd.shiro.entity.Role;
import com.lb.abcd.shiro.vo.request.RolePageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @ClassName RoleDao
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/1 14:43
 * @Version 1.0
 */

@Mapper
public interface RoleDao extends BaseMapper<Role> {

    /*
    根据userId获取角色
     */
    public List<Role> getRoleByUserId(String userId);

    /*
    分页获取所有角色
     */
    public IPage<Role> getRoles(Page page, @Param("vo") RolePageReqVO vo);
}
