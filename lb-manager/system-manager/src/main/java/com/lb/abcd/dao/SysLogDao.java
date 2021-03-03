package com.lb.abcd.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lb.abcd.entity.SysLog;
import com.lb.abcd.vo.request.SysLogPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

/**
 * @ClassName SysLogDao
 * @Description TODO
 * @Author Terran
 * @Date 2021/2/26 17:13
 * @Version 1.0
 */

@Mapper
public interface SysLogDao extends BaseMapper<SysLog> {

    /*
    获取所有的日志
     */
    public IPage<SysLog> selectAll(Page page, @Param("vo") SysLogPageReqVO vo);
}
