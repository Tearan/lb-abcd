package com.lb.abcd.system.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName EnableParam
 * @Description 这个注解是为了entity查询时，可作为查询条件的字段
 * @Author Terran
 * @Date 2021/3/2 10:59
 * @Version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EnableParam {

    /**
     * 默认是like
     * 值可以是>_ 或者 <_ 或者 =
     * 当上面的值是>或<需要绑定对应的数据库字段，因为其自身不是数据库的字段
     * >_后面为绑定的字段
     * 如createdateStart >_createdate
     */
    String value() default "";
}
