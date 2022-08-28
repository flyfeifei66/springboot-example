package com.zhaojufei.bizline.example.webapi.controller;

import com.zhaojufei.bizline.example.biz.demo.DemoBiz;
import com.zhaojufei.bizline.example.biz.demo.impl.TestFlowService;
import com.zhaojufei.bizline.example.facade.domain.MsgBody;
import com.zhaojufei.bizline.example.facade.domain.dto.TestDto;
import com.zhaojufei.bizline.example.facade.domain.enums.ResCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @Autowired
    private DemoBiz demoBiz;

    @Autowired
    private TestFlowService testFlowService;

    @PostMapping("/demo")
    public MsgBody test(@RequestBody TestDto testDto) {
        return MsgBody.getInstance(ResCodeEnum.SUCCESS, demoBiz.getOne(testDto));
    }

    @PostMapping("/demo2")
    public MsgBody test2(@RequestBody TestDto testDto) {
        return MsgBody.getInstance(ResCodeEnum.SUCCESS, testFlowService.test(testDto));
    }
}
