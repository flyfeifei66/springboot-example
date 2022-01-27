package com.zhaojufei.bizline.example.config;


import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Java
 * @date 2020/2/11 9:12
 */
@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

    /**
     * 使用fastjson代替jackson
     */
    @Override

    /**
     * 自定义消息转换器
     *
     * @param converters
     */
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        // 1.构建了一个HttpMessageConverter FastJson 消息转换器
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        // 2.定义一个配置，设置编码方式，和格式化的形式
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // 3.设置成了PrettyFormat格式
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue, // 是否输出值为null的字段,默认为false
                SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null,输出为false,而非null
                SerializerFeature.WriteNullListAsEmpty, // List字段如果为null,输出为[],而非nul
                SerializerFeature.DisableCircularReferenceDetect, // 消除对同一对象循环引用的问题，默认为false
                SerializerFeature.WriteNullStringAsEmpty// 字符类型字段如果为null,输出为"",而非null
        );
        // 4.处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastMediaTypes.add(MediaType.APPLICATION_JSON);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);

        // 5.将fastJsonConfig加到消息转换器中
        fastConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastConverter);
    }

}

