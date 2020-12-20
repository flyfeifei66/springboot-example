package com.zhaojufei.bizline.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.ApplicationContext;

@Slf4j
//@Configuration
//@EnableApolloConfig(value = "application")
public class ApolloConfig {
    private static final String LOGGER_TAG = "logging.level.";
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private LoggingSystem loggingSystem;
/*
    @ApolloConfigChangeListener("application")
    public void applicationOnChange(ConfigChangeEvent changeEvent) {
        Set<String> changedKeys = changeEvent.changedKeys();
        changedKeys.forEach(configStr -> log.info("apollo 变更字段key: {}", configStr));
        applicationContext.publishEvent(new EnvironmentChangeEvent(changedKeys));
        for (String changedKey : changedKeys) {
            if (changedKey.startsWith(LOGGER_TAG)) {
                ConfigChange configChange = changeEvent.getChange(changedKey);
                String oldValue = configChange.getOldValue();
                String newValue = configChange.getNewValue();
                LogLevel level = LogLevel.valueOf(newValue.toUpperCase());
                loggingSystem.setLogLevel(changedKey.replace(LOGGER_TAG, ""), level);
                log.info("【Apollo动态修改日志级别】changedKey:【{}】, oldValue=【{}】, newValue:【{}】", changedKey, oldValue,
                        newValue);
            }
        }
    }*/
}
