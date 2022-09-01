package com.zhaojufei.bizline.example.core.businessflow.protocal;

import com.zhaojufei.bizline.example.core.businessflow.BaseContext;

/**
 * 处理器协议
 */
public interface Processor {

    /**
     * 执行处理器
     *
     * @param baseContext 通用上下文
     */
    void process(BaseContext baseContext);
}
