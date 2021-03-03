package com.lb.abcd.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lb.abcd.dao.SysLogDao;
import com.lb.abcd.entity.SysLog;
import com.lb.abcd.service.SysLogService;
import com.lb.abcd.system.util.PageUtil;
import com.lb.abcd.system.vo.response.PageVO;
import com.lb.abcd.vo.request.SysLogPageReqVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName SysLogServiceImpl
 * @Description TODO
 * @Author Terran
 * @Date 2021/2/26 17:12
 * @Version 1.0
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLog> implements SysLogService {

    @Override
    public PageVO<SysLog> selectAll(SysLogPageReqVO vo) {
        Page<SysLog> page = new Page<>(vo.getPageNum(),vo.getPageSize());
        IPage<SysLog> users = this.baseMapper.selectAll(page,vo);
        return PageUtil.getPage(users);
    }

    @Override
    public void delete(List<String> logIds) {
        this.baseMapper.deleteBatchIds(logIds);
    }
}
