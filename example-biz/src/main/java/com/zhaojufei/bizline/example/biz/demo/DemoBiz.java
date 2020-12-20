package com.zhaojufei.bizline.example.biz.demo;

import com.zhaojufei.bizline.example.core.web.domain.dto.TestDto;
import com.zhaojufei.bizline.example.dao.entity.CompanyEntity;

public interface DemoBiz {
    CompanyEntity getOne(TestDto testDto);
}
