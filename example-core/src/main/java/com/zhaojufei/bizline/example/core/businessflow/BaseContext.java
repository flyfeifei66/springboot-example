package com.zhaojufei.bizline.example.core.businessflow;

import lombok.Data;

/**
 * 上下文基类
 * 业务继承该基类，子类添加业务所需要的属性
 *
 * @param <P>
 * @param <R>
 */
@Data
public abstract class BaseContext<P, R> {

    /**
     * 入参
     */
    private P request;

    /**
     * 出参
     */
    private R result;
}
