package com.lb.abcd.system.annotation;

import java.lang.annotation.*;

/**
 * @ClassName MyLog
 * @Description TODO
 * @Author Terran
 * @Date 2021/2/26 16:49
 * @Version 1.0
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyLog {

    /** 用户操作哪个模块*/
    String title() default "";

    /** 记录用户操作的动作*/
    String action() default "";
}
