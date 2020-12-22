package com.zhaojufei.bizline.example.service.repository;

import com.zhaojufei.bizline.example.dao.entity.CompanyEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zhaojufei.bizline.example.dao.entity.CompanyEntityExample;
import com.zhaojufei.bizline.example.dao.mapper.CompanyEntityMapper;
import com.zhaojufei.bizline.example.service.domain.CompanyDetail;

@Repository
public class CompanyRepository {
    @Autowired
    private CompanyEntityMapper entityMapper;

    public CompanyDetail getOne(){
        CompanyEntity companyEntity = entityMapper.selectOneByExample(new CompanyEntityExample());
        CompanyDetail companyDetail = new CompanyDetail();
        if(companyEntity !=null) {
            BeanUtils.copyProperties(companyEntity, companyDetail);
        }
        return companyDetail;
    }
}
