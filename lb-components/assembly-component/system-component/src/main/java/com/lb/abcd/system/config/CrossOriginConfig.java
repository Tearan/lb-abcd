package com.lb.abcd.system.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName CrossOriginConfig
 * @Description TODO
 * @Author Terran
 * @Date 2021/3/1 15:12
 * @Version 1.0
 */

@Configuration
public class CrossOriginConfig {

    @Bean
    public CrossOriginfilter crossOriginfilter(){
        return new CrossOriginfilter();
    }

    @Bean
    public FilterRegistrationBean getFilterRegistrationBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(crossOriginfilter());
        //设置拦截路径
        bean.addUrlPatterns("/api/v1/*");
        //设置名称
        bean.setName("crossOriginfilter");
        return bean;
    }
}
