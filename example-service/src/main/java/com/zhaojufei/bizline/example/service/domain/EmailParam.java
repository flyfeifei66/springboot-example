package com.zhaojufei.bizline.example.service.domain;

import lombok.Data;

@Data
public class EmailParam {
    /**
     * 日期
     */
    private String date;

    /**
     * 时间
     */
    private String time;

    /**
     * 系统名称
     */
    private String sysName;
}
