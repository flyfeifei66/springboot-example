package com.zhaojufei.bizline.example.biz.demo.context;

import com.zhaojufei.bizline.example.core.businessflow.BaseContext;
import com.zhaojufei.bizline.example.facade.domain.dto.TestDto;
import lombok.Data;

@Data
public class TestContext extends BaseContext<TestDto, String> {
    // 这里定义各个processor的入参、出参等。后续可以设计Map,放到BaseContext里，比如一个入参Map，一个出参Map
    // key是processor的类名。

    /**
     * 这里只模拟
     */
    private String result;
}
