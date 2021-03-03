package com.lb.abcd.system.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lb.abcd.system.vo.response.PageVO;

/**
 * @ClassName PageUtil
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/3 10:28
 * @Version 1.0
 */
public class PageUtil {

    private PageUtil(){}
    public static <T> PageVO<T> getPage(IPage<T> list){
        PageVO<T> result = new PageVO<>();
        if(list instanceof Page){
            Page<T> page= (Page<T>) list;
            result.setTotalRows(page.getTotal());
            result.setList(page.getRecords());
        }
        return result;
    }
}
