package com.lb.abcd.shiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lb.abcd.shiro.entity.Dept;
import com.lb.abcd.shiro.vo.request.DeptAddReqVO;
import com.lb.abcd.shiro.vo.request.DeptUpdateReqVO;
import com.lb.abcd.shiro.vo.response.DeptRespNodeVO;

import java.util.List;

/**
 * @ClassName DeptService
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/3 9:58
 * @Version 1.0
 */
public interface DeptService extends IService<Dept> {

    /*
    获取部门列表
     */
    public List<Dept> getDepts();

    /*
    获取部门树
     */
    public List<DeptRespNodeVO> deptTreeList(String deptId);

    /*
    新增部门
     */
    public void add(DeptAddReqVO vo);

    /*
    更新部门
     */
    public void update(DeptUpdateReqVO vo);

    /*
    删除部门
     */
    public void delete(String deptId);
}
