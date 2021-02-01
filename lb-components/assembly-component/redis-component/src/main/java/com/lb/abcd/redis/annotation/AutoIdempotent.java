package com.lb.abcd.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName AutoIdempotent
 * @Description 自定义注解，定义此注解的目的是把它添加到需要实现幂等的方法上，
 *              只要某个方法注解了其，都会自动实现幂等操作
 * @Author Terran
 * @Date 2021/1/31 13:42
 * @Version 1.0
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoIdempotent {
}
