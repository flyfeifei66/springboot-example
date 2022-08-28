package com.zhaojufei.bizline.example.core.businessflow.annotation;


import com.zhaojufei.bizline.example.core.businessflow.protocal.Processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceConfig {
    /**
     * 处理器，值为处理器实现类数组
     * @return
     */
    Class<? extends Processor>[] processors() default {};
}
