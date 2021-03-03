package com.lb.abcd.shiro.util;

import com.lb.abcd.shiro.entity.Dept;
import com.lb.abcd.shiro.vo.response.DeptRespNodeVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DeptTreeUtil
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/3 10:25
 * @Version 1.0
 */
public class DeptTreeUtil {

    public static List<DeptRespNodeVO> getTree(List<Dept> depts){
        List<DeptRespNodeVO> list=new ArrayList<>();
        for(Dept dept:depts){
            if(dept.getPid().equals("0")) {
                DeptRespNodeVO node = new DeptRespNodeVO();
                node.setId(dept.getId());
                node.setTitle(dept.getName());
                node.setChildren(getChild(dept.getId(),depts));
                list.add(node);
            }
        }
        return list;
    }

    private static List<DeptRespNodeVO> getChild(String pid,List<Dept> depts){
        List<DeptRespNodeVO> list=new ArrayList<>();
        for(Dept dept : depts)
            if(dept.getPid().equals(pid)){
                DeptRespNodeVO node = new DeptRespNodeVO();
                node.setId(dept.getId());
                node.setTitle(dept.getName());
                node.setChildren(getChild(dept.getId(),depts));
                list.add(node);
            }
        return list;
    }
}
