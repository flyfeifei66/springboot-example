package com.zhaojufei.bizline.example.core.businessflow.annotation;

import com.zhaojufei.bizline.example.core.businessflow.protocal.Validator;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 处理器配置，可以配置前置校验器和后置校验器
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProcessorConfig {
    /**
     * 前置校验器
     */
    @AliasFor(value = "beforeValidators")
    Class<? extends Validator>[] validators() default {};

    /**
     * 前置校验器
     */
    @AliasFor(value = "validators")
    Class<? extends Validator>[] beforeValidators() default {};

    /**
     * 前置校验器
     */
    Class<? extends Validator>[] afterValidators() default {};


}
