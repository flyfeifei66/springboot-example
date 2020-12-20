package com.zhaojufei.bizline.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zhaojufei.bizline.example.core.properties.BasicDataProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * 三码合一陪配置项
 */
@Slf4j
@Configuration
public class PropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "example.basicdata")
    public BasicDataProperties basicDataProperties() {
        return new BasicDataProperties();
    }

}
