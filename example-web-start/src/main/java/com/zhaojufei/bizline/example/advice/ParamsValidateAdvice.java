package com.zhaojufei.bizline.example.advice;

import com.google.common.collect.Lists;
import com.zhaojufei.bizline.example.core.exception.BizException;
import com.zhaojufei.bizline.example.facade.domain.enums.ResCodeEnum;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义验证切面，适合任何地方需要校验都可以通过注解实现验证
 *
 * @author zhaojufei
 */
@Component
@Aspect
public class ParamsValidateAdvice {
    @Autowired
    private Validator validator;

    /**
     * 拦截所有参数上加了@Validated的注解的方法，使得任何地方都可以使用注解校验参数。
     * 排除掉controller，因为controller有自己的参数校验实现，不需要aop
     */
    @Pointcut("execution(* com.zhaojufei..*(.., @org.springframework.validation.annotation.Validated (*), ..)) && "
            + "!execution(* com.zhaojufei..controller..*(..)) ")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) {
        Object[] params = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();

        // 验证参数上的注解
        for (int i = 0; i < parameters.length; i++) {
            Parameter p = parameters[i];
            Validated validated = p.getAnnotation(Validated.class);
            if (validated == null) {
                continue;
            }
            Set<ConstraintViolation<Object>> violations = validator.validate(params[i]);
            if (violations.size() > 0) {
                List<String> errorMsgList = Lists.newArrayList();
                violations.forEach(bean -> errorMsgList.add(bean.getMessage()));
                throw new BizException(ResCodeEnum.PARAM_ERROR, errorMsgList.stream().collect(Collectors.joining(";")));
            }
        }
    }
}
