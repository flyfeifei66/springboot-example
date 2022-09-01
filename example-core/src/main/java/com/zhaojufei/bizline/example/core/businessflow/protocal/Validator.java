package com.zhaojufei.bizline.example.core.businessflow.protocal;

import com.zhaojufei.bizline.example.core.businessflow.BaseContext;

/**
 * 校验器
 */
public interface Validator {

    /**
     * 执行校验器，校验失败则抛出异常。
     * @param baseContext
     */
    void validate(BaseContext baseContext);
}
