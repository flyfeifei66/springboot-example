package com.zhaojufei.bizline.example.facade.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class TestDto {

    @NotEmpty(message = "name不能为空")
    private String name;

    @NotEmpty(message = "des不能为空")
    private String des;
}
