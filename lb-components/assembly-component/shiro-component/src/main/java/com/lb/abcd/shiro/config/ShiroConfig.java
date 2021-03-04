package com.lb.abcd.shiro.config;

import com.lb.abcd.redis.cache.RedisCacheManager;
import com.lb.abcd.shiro.filter.CustomAccessControllerFilter;
import com.lb.abcd.shiro.matcher.CustomHashedCredentialsMatcher;
import com.lb.abcd.shiro.realm.CustomRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName ShiroConfig
 * @Description TODO
 * @Author Terran
 * @Date 2021/1/31 13:59
 * @Version 1.0
 */

@Configuration
public class ShiroConfig {

    @Bean
    public RedisCacheManager redisCacheManager(){
        return new RedisCacheManager();
    }

    @Bean
    public CustomHashedCredentialsMatcher customHashedCredentialsMatcher(){
        return new CustomHashedCredentialsMatcher();
    }

    @Bean
    public CustomRealm customRealm(){
        CustomRealm customRealm = new CustomRealm();

        /** 配置加密盐值  用户校验之类的*/
        customRealm.setCredentialsMatcher(customHashedCredentialsMatcher());

        /** 配置缓存管理类 redisCacheManager()*/
        customRealm.setCacheManager(redisCacheManager());
        return customRealm;
    }

    /**
     * 配置 SecurityManager,可配置一个或多个realm
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        /** 配置securityManager,并注入customRealm*/
        defaultWebSecurityManager.setRealm(customRealm());
        return defaultWebSecurityManager;
    }

    /**
     * shiro过滤器，配置拦截哪些请求
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        /** 自定义拦截器限制并发人数*/
        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();

        /** 用来校验token*/
        filtersMap.put("token", new CustomAccessControllerFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        /** 配置不会被拦截的链接 顺序判断*/
        filterChainDefinitionMap.put("/api/user/login", "anon");
        filterChainDefinitionMap.put("/upload/image/**","anon");

        /** 后端下载接口设置开放性*/
        filterChainDefinitionMap.put("/index/**","anon");
        filterChainDefinitionMap.put("/login","anon");
        filterChainDefinitionMap.put("/register","anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/layui/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/treetable-lay/**", "anon");
        filterChainDefinitionMap.put("/api/user/token", "anon");
        filterChainDefinitionMap.put("/api/v1/**", "anon");

        /** 放开swagger-ui地址*/
        filterChainDefinitionMap.put("/swagger/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/captcha.jpg", "anon");
        filterChainDefinitionMap.put("/","anon");
        filterChainDefinitionMap.put("/csrf","anon");
        filterChainDefinitionMap.put("/**","token,authc");

        /** 配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据*/
        shiroFilterFactoryBean.setLoginUrl("/api/user/login");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 开启shiro 注解支持. 使以下注解能够生效 :
     * 需要认证 {@link org.apache.shiro.authz.annotation.RequiresAuthentication RequiresAuthentication}
     * 需要用户 {@link org.apache.shiro.authz.annotation.RequiresUser RequiresUser}
     * 需要访客 {@link org.apache.shiro.authz.annotation.RequiresGuest RequiresGuest}
     * 需要角色 {@link org.apache.shiro.authz.annotation.RequiresRoles RequiresRoles}
     * 需要权限 {@link org.apache.shiro.authz.annotation.RequiresPermissions RequiresPermissions}
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
