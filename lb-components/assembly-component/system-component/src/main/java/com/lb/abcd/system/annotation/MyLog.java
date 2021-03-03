package com.lb.abcd.system.annotation;

import java.lang.annotation.*;

/**
 * @ClassName MyLog
 * @Description 注解类
 * @Author Terran
 * @Date 2021/2/26 16:49
 * @Version 1.0
 */

/** 用于方法*/
@Target(ElementType.METHOD)
/** 运行时有效*/
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyLog {

    /** 用户操作哪个模块*/
    String title() default "";

    /** 记录用户操作的动作*/
    String action() default "";
}
