package com.zhaojufei.bizline.example.biz.demo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhaojufei.bizline.example.biz.demo.DemoBiz;
import com.zhaojufei.bizline.example.core.web.domain.dto.TestDto;
import com.zhaojufei.bizline.example.service.domain.CompanyDetail;
import com.zhaojufei.bizline.example.service.repository.CompanyRepository;

@Service
public class DemoBizImpl implements DemoBiz {
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public CompanyDetail getOne(TestDto testDto) {
        return companyRepository.getOne();
    }
}
