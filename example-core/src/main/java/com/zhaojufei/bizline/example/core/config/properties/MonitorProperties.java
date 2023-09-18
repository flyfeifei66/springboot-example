package com.zhaojufei.bizline.example.core.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * 配置项
 */
@Slf4j
@Configuration
public class MonitorProperties {
    @Bean
    @ConfigurationProperties(prefix = "monitor.basic")
    public BasicProperties basicProperties() {
        return new BasicProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "monitor.task.mail-remind")
    public MailRemindTaskProperties mailRemindTaskProperties() {
        return new MailRemindTaskProperties();
    }

}
