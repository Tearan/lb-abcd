package com.lb.abcd.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lb.abcd.system.entity.BaseEntity;

/**
 * @ClassName BaseService
 * @Description 公用的service接口
 * @Author Terran
 * @Date 2021/3/2 11:05
 * @Version 1.0
 */
public interface BaseService<T extends BaseEntity> extends IService<T> {
}
