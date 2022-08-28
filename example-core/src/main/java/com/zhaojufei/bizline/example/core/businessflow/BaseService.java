package com.zhaojufei.bizline.example.core.businessflow;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhaojufei.bizline.example.core.businessflow.annotation.ProcessorConfig;
import com.zhaojufei.bizline.example.core.businessflow.annotation.ServiceConfig;
import com.zhaojufei.bizline.example.core.businessflow.protocal.Processor;
import com.zhaojufei.bizline.example.core.businessflow.protocal.Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * Service基类
 * 继承该基类，然后添加@ServiceConfig注解，配置processor,调用execute方法即可按照顺序执行processor
 *
 * @param <C>
 */
@Slf4j
public abstract class BaseService<C extends BaseContext> {
    @Autowired
    private ApplicationContext applicationContext;

    private List<Processor> processorList = Lists.newArrayList();
    private Map<Processor, List<Validator>> beforeValidatorsMap = Maps.newHashMap();
    private Map<Processor, List<Validator>> afterValidatorsMap = Maps.newHashMap();

    @PostConstruct
    public void init() {
        ServiceConfig serviceConfig = AnnotationUtils.findAnnotation(this.getClass(), ServiceConfig.class);
        if (serviceConfig == null) {
            throw new RuntimeException("需要添加@ServiceConfig注解");
        }
        Class<? extends Processor>[] processors = serviceConfig.processors();
        for (Class<? extends Processor> clazz : processors) {
            Processor beanOfType = getBeanOfType(clazz);
            this.processorList.add(beanOfType);

            ProcessorConfig processorConfig = AnnotationUtils.findAnnotation(clazz, ProcessorConfig.class);
            if (processorConfig != null) {
                List<Validator> beforeValidators = getBeanListByTypeArray(processorConfig.beforeValidators());
                beforeValidatorsMap.put(beanOfType, beforeValidators);

                List<Validator> afterValidators = getBeanListByTypeArray(processorConfig.afterValidators());
                afterValidatorsMap.put(beanOfType, afterValidators);
            }
        }
    }


    /**
     * 顺序执行processors
     *
     * @param context
     */
    protected void execute(C context) {
        for (int i = 0; i < processorList.size(); i++) {
            Processor processor = processorList.get(i);
            this.executeValidators("前置", beforeValidatorsMap.get(processor), context);
            this.executeProcessor(processor, context, i);
            this.executeValidators("后置", beforeValidatorsMap.get(processor), context);
        }
    }

    private void executeProcessor(Processor processor, C context, int i) {
        String logStr = String.format("[处理器执行][%d/%d][%s]", i + 1, processorList.size(), processor.getClass());
        log.info("开始执行,{}", logStr);
        try {
            processor.process(context);
            log.info("处理完成,{}", logStr);
        } catch (Exception e) {
            log.info("处理失败,{}", logStr);
            throw e;
        }
    }

    private void executeValidators(String prefix, List<Validator> validatorList, C context) {
        if (CollectionUtils.isEmpty(validatorList)) {
            return;
        }
        for (int i = 0; i < validatorList.size(); i++) {
            Validator validator = validatorList.get(i);
            String logStr = String.format("[%s校验器执行][%d/%d][%s]", prefix, i + 1, validatorList.size(), validator.getClass());
            log.info("开始校验,{}", logStr);

            try {
                validator.validate(context);
                log.info("校验通过,{}", logStr);
            } catch (Exception e) {
                log.info("校验不通过,{}", logStr);
                throw e;
            }

        }
    }


    /**
     * 根据类型获取Bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> T getBeanOfType(Class clazz) {
        Map<String, ?> beansOfType = applicationContext.getBeansOfType(clazz);
        if (MapUtils.isEmpty(beansOfType)) {
            throw new RuntimeException("找不到类实例,for " + clazz);
        }
        if (beansOfType.size() > 1) {
            throw new RuntimeException("类实例只能有一个,for " + clazz);
        }

        return (T) beansOfType.values().toArray()[0];
    }

    /**
     * 根据类型数据获取多个bean
     *
     * @param clazzArray
     * @param <T>
     * @return
     */
    private <T> List<T> getBeanListByTypeArray(Class<? extends T>[] clazzArray) {
        List<T> beanList = Lists.newArrayList();
        for (Class<? extends T> clazz : clazzArray) {
            T bean = getBeanOfType(clazz);
            beanList.add(bean);
        }
        return beanList;
    }

}
