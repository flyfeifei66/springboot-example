package com.zhaojufei.bizline.example.biz.demo.impl;

import com.zhaojufei.bizline.example.biz.demo.context.TestContext;
import com.zhaojufei.bizline.example.core.businessflow.BaseContext;
import com.zhaojufei.bizline.example.core.businessflow.protocal.Validator;
import com.zhaojufei.bizline.example.facade.domain.dto.TestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestBeforeValidator implements Validator {

    @Override
    public void validate(BaseContext baseContext) {
        TestContext context = (TestContext) baseContext;
        TestDto request = context.getRequest();
        if (request == null) {
            throw new RuntimeException("入参不能等于null");
        }
    }
}
