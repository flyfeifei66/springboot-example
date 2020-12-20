package com.zhaojufei.bizline.example.dao.repository;

import com.zhaojufei.bizline.example.dao.entity.CompanyEntity;
import com.zhaojufei.bizline.example.dao.entity.CompanyEntityExample;
import com.zhaojufei.bizline.example.dao.mapper.CompanyEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyRepository {
    @Autowired
    private CompanyEntityMapper entityMapper;

    public CompanyEntity getOne(){
        return entityMapper.selectOneByExample(new CompanyEntityExample());
    }
}
