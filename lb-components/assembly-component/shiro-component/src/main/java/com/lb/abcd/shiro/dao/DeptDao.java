package com.lb.abcd.shiro.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lb.abcd.shiro.entity.Dept;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName DeptDao
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/2 13:55
 * @Version 1.0
 */
@Mapper
public interface DeptDao extends BaseMapper<Dept> {
}
