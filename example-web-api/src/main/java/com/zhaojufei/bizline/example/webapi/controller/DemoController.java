package com.zhaojufei.bizline.example.webapi.controller;

import com.zhaojufei.bizline.example.biz.demo.DemoBiz;
import com.zhaojufei.bizline.example.core.web.domain.MsgBody;
import com.zhaojufei.bizline.example.core.web.domain.dto.TestDto;
import com.zhaojufei.bizline.example.core.web.enums.ResCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @Autowired
    private DemoBiz demoBiz;

    @PostMapping("/demo")
    public MsgBody test(@RequestBody  TestDto testDto) {
        return MsgBody.getInstance(ResCodeEnum.SUCCESS, demoBiz.getOne(testDto));
    }
}
