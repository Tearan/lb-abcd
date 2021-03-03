package com.lb.abcd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lb.abcd.entity.SysLog;
import com.lb.abcd.system.vo.response.PageVO;
import com.lb.abcd.vo.request.SysLogPageReqVO;

import java.util.List;

/**
 * @ClassName SysLogService
 * @Description TODO
 * @Author Terran
 * @Date 2021/2/26 17:11
 * @Version 1.0
 */
public interface SysLogService extends IService<SysLog> {

    /*
    获取所有的日志信息（分页）
     */
    public PageVO<SysLog> selectAll(SysLogPageReqVO vo);

    /*
    删除
     */
    public void delete(List<String> logIds);
}
