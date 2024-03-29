package com.zhaojufei.bizline.example.core.formrepeat.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author zhaojufei
 */
@Data
public class Token implements Serializable {

    private static final long serialVersionUID = 8041311864329467031L;

    /**
     * token字符串
     */
    private String token;

    /**
     * token生成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    /**
     * 构造方法
     */
    public Token(String token, Date date) {
        this.token = token;
        this.date = date;
    }
}
