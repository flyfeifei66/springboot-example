package com.zhaojufei.bizline.example.biz.demo.impl;

import com.zhaojufei.bizline.example.biz.demo.DemoBiz;
import com.zhaojufei.bizline.example.core.web.domain.dto.TestDto;
import com.zhaojufei.bizline.example.dao.entity.CompanyEntity;
import com.zhaojufei.bizline.example.dao.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
public class DemoBizImpl implements DemoBiz {
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public CompanyEntity getOne(@Validated TestDto testDto) {
        return companyRepository.getOne();
    }
}
