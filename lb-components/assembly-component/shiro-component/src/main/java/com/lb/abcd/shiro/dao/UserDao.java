package com.lb.abcd.shiro.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lb.abcd.shiro.entity.User;
import com.lb.abcd.shiro.vo.request.UserPageReqVO;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName UserDao
 * @Description TODO
 * @Author Terran
 * @Date 2021/2/3 13:54
 * @Version 1.0
 */

public interface UserDao extends BaseMapper<User> {

    /**
     * 获取单个用户信息
     * @param userId
     * @return
     */
    public User getUser(String userId);

    /**
     * 获取所有用户
     * @param page
     * @param vo
     * @return
     */
    public IPage<User> selectAll(Page page, @Param("vo") UserPageReqVO vo);
}
