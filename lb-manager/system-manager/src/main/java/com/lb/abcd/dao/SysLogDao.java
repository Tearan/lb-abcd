package com.lb.abcd.dao;

import com.lb.abcd.entity.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName SysLogDao
 * @Description TODO
 * @Author Terran
 * @Date 2021/2/26 17:13
 * @Version 1.0
 */
public interface SysLogDao extends JpaRepository<SysLog,String> {
}
