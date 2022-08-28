package com.zhaojufei.bizline.example.biz.demo.impl;

import com.zhaojufei.bizline.example.biz.demo.context.TestContext;
import com.zhaojufei.bizline.example.core.businessflow.BaseService;
import com.zhaojufei.bizline.example.core.businessflow.annotation.ServiceConfig;
import com.zhaojufei.bizline.example.facade.domain.dto.TestDto;
import org.springframework.stereotype.Service;


/**
 * 本类就是普通的service，它的作用是把入参转化为context，然后通过BaseService的exxcute方法执行业务逻辑。
 */
@Service
@ServiceConfig(
        processors = {TestProcessor.class}
)
public class TestFlowService extends BaseService<TestContext> {

    /**
     * 这个方法是可以实现一个接口的
     *
     * @param testDto
     * @return
     */
    public String test(TestDto testDto) {
        TestContext testContext = new TestContext();
        testContext.setRequest(testDto);


        this.execute(testContext);
        return testContext.getResult();

    }
}
