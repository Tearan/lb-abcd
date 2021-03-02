package com.lb.abcd.system.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lb.abcd.system.entity.BaseEntity;
import com.lb.abcd.system.service.BaseService;

/**
 * @ClassName BaseServiceImpl
 * @Description 公用的service
 * @Author Terran
 * @Date 2021/3/2 11:14
 * @Version 1.0
 */
public class BaseServiceImpl <M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {
}
