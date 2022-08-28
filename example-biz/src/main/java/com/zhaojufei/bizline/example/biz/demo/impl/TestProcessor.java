package com.zhaojufei.bizline.example.biz.demo.impl;

import com.zhaojufei.bizline.example.biz.demo.context.TestContext;
import com.zhaojufei.bizline.example.core.businessflow.BaseContext;
import com.zhaojufei.bizline.example.core.businessflow.annotation.ProcessorConfig;
import com.zhaojufei.bizline.example.core.businessflow.protocal.Processor;
import org.springframework.stereotype.Component;

@Component
@ProcessorConfig(
        validators = {TestBeforeValidator.class},
        afterValidators = {TestAfterValidator.class}
)
public class TestProcessor implements Processor {
    @Override
    public void process(BaseContext baseContext) {
        TestContext context = (TestContext)baseContext;
        context.setResult(context.getRequest().getName());
    }
}
