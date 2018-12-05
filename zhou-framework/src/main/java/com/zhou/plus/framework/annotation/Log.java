package com.zhou.plus.framework.annotation;


import java.lang.annotation.*;

/**
 * 操作日志注解、
 *
 * @since 1.3.0 2018-12-01 *
 * @author zhoushengyuan
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    String value() default "";
}
