package com.zhaojufei.bizline.example.biz.demo;


import com.zhaojufei.bizline.example.facade.domain.dto.TestDto;
import com.zhaojufei.bizline.example.service.domain.CompanyDetail;

public interface DemoBiz {
    CompanyDetail getOne(TestDto testDto);
}
