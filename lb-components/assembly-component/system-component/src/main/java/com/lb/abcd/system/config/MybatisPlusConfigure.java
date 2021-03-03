package com.lb.abcd.system.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MybatisPlusConfigure
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/2 17:18
 * @Version 1.0
 */
@Configuration
public class MybatisPlusConfigure {

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }
}
