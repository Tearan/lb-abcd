package com.lb.abcd.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lb.abcd.system.entity.BaseEntity;

/**
 * @ClassName IMapper
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/2 11:15
 * @Version 1.0
 */
public interface IMapper<T extends BaseEntity> extends BaseMapper<T> {
}
