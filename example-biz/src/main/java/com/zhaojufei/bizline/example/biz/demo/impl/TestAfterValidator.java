package com.zhaojufei.bizline.example.biz.demo.impl;

import com.zhaojufei.bizline.example.biz.demo.context.TestContext;
import com.zhaojufei.bizline.example.core.businessflow.BaseContext;
import com.zhaojufei.bizline.example.core.businessflow.protocal.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestAfterValidator implements Validator {

    @Override
    public void validate(BaseContext baseContext) {
        TestContext context = (TestContext) baseContext;
        String result = context.getResult();
        if (result == null) {
            throw new RuntimeException("result不能等于null");
        }
    }
}
