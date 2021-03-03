package com.lb.abcd.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lb.abcd.shiro.dao.DeptDao;
import com.lb.abcd.shiro.entity.Dept;
import com.lb.abcd.shiro.service.DeptService;
import com.lb.abcd.shiro.util.DeptTreeUtil;
import com.lb.abcd.shiro.vo.request.DeptAddReqVO;
import com.lb.abcd.shiro.vo.request.DeptUpdateReqVO;
import com.lb.abcd.shiro.vo.response.DeptRespNodeVO;
import com.lb.abcd.system.exception.APIException;
import com.lb.abcd.system.result.RsCode;
import com.lb.abcd.system.util.IDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName DeptServiceImpl
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/3 10:01
 * @Version 1.0
 */
@Slf4j
@Service
public class DeptServiceImpl extends ServiceImpl<DeptDao, Dept> implements DeptService {

    @Override
    public List<Dept> getDepts() {
        List<Dept> depts = this.baseMapper.selectList(new QueryWrapper<Dept>().eq("deleted", 1));
        for(Dept dept : depts){
            Dept parent = this.baseMapper.selectById(dept.getPid());
            if(parent!=null)
                dept.setPidName(parent.getName());
        }
        return depts;
    }

    @Override
    public List<DeptRespNodeVO> deptTreeList(String deptId) {
        List<DeptRespNodeVO> tree = new ArrayList<>();
        List<Dept> depts = this.baseMapper.selectList(new QueryWrapper<Dept>().eq("deleted",1));
        DeptRespNodeVO parent = new DeptRespNodeVO();
        parent.setId("0");
        parent.setTitle("默认顶级部门");
        parent.setChildren(DeptTreeUtil.getTree(depts));
        tree.add(parent);
        return tree;
    }

    @Override
    @Transactional
    public void add(DeptAddReqVO vo) {
        Dept dept = new Dept();
        BeanUtils.copyProperties(vo,dept);
        dept.setId(IDUtils.generateId());
        dept.setCreateTime(new Date());
        int i = this.baseMapper.insert(dept);
        if(i != 1){
            throw new APIException(RsCode.OPERATION_ERROR);
        }
    }

    @Override
    @Transactional
    public void update(DeptUpdateReqVO vo) {
        Dept dept = new Dept();
        BeanUtils.copyProperties(vo,dept);
        dept.setUpdateTime(new Date());
        int i = this.baseMapper.updateById(dept);
        if(i != 1){
            throw new APIException(RsCode.OPERATION_ERROR);
        }
    }

    @Override
    @Transactional
    public void delete(String deptId) {
        Ydept(deptId);
        QueryWrapper<Dept> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted",1).eq("id",deptId);
        Dept dept = this.baseMapper.selectOne(wrapper);
        dept.setDeleted(0);
        int i = this.baseMapper.updateById(dept);
        if(i != 1){
            throw new APIException(RsCode.OPERATION_ERROR);
        }
    }

    @Transactional
    public void Ydept(String deptId){
        log.info("删除部门时也删除子集:[{}]",deptId);
        QueryWrapper<Dept> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted",1).eq("pid",deptId);
        List<Dept> depts = this.baseMapper.selectList(wrapper);
        if(!depts.isEmpty()){
            for(Dept dept:depts){
                dept.setDeleted(0);
                int i = this.baseMapper.updateById(dept);
                if(i != 1){
                    throw new APIException(RsCode.OPERATION_ERROR);
                }
                Ydept(dept.getId());
            }
        }
    }

    private void Ndept(String deptId){
        log.info("删除部门时不删除子集:[{}]",deptId);
        QueryWrapper<Dept> wrapper = new QueryWrapper<>();
        wrapper.eq("deleted",1).eq("pid",deptId);
        List<Dept> depts = this.baseMapper.selectList(wrapper);
        if(!depts.isEmpty()){
            throw new APIException(RsCode.NOT_PERMISSION_DELETED_DEPT);
        }
    }
}
