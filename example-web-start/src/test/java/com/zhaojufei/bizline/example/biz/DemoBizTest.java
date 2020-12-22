package com.zhaojufei.bizline.example.biz;

import com.alibaba.fastjson.JSON;
import com.zhaojufei.bizline.example.ExampleWebApplication;
import com.zhaojufei.bizline.example.biz.demo.DemoBiz;
import com.zhaojufei.bizline.example.core.web.domain.dto.TestDto;
import com.zhaojufei.bizline.example.service.domain.CompanyDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes= ExampleWebApplication.class)
@RunWith(SpringRunner.class)
public class DemoBizTest {
    @Autowired
    private DemoBiz demoBiz;

    @Test
    public void test(){
        CompanyDetail one = this.demoBiz.getOne(new TestDto());
        System.out.println(JSON.toJSON(one));
    }

}
