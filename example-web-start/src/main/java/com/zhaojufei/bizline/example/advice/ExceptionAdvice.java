package com.zhaojufei.bizline.example.advice;

import com.zhaojufei.bizline.example.core.exception.BizException;
import com.zhaojufei.bizline.example.core.web.domain.MsgBody;
import com.zhaojufei.bizline.example.core.web.enums.ResCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常拦截器
 *
 * @author zhaojufei
 */
@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    /**
     * 业务抛出异常处理
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MsgBody handleBusinessException(HttpServletRequest request, BizException e) {
        log.info("业务异常，请求路径 = {}， 错误信息 = {}, 堆栈信息 = {}", request.getRequestURI(), e.getMessage(), e);
        return MsgBody.getInstance(
                (StringUtils.isEmpty(e.getCode()) ? ResCodeEnum.BUSINESS_ERROR.getCode() : e.getCode()), e.getMessage(),
                e.getData());
    }

    /**
     * 参数校验异常处理
     *
     * @param e e
     * @return
     */
    @ExceptionHandler(value = { MethodArgumentNotValidException.class, MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class })
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MsgBody illegalParamErrorHandler(Exception e) {
        List<String> errorMsgList = new ArrayList<>();
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
             exception.getBindingResult().getFieldErrors().forEach(err -> errorMsgList.add(err.getDefaultMessage()));
        } else if (e instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException exception = (MissingServletRequestParameterException) e;
            errorMsgList.add(exception.getParameterName());
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) e;
            errorMsgList.add(exception.getName());
        }
        return MsgBody.getInstance(ResCodeEnum.PARAM_ERROR, errorMsgList.stream().collect(Collectors.joining(";")));
    }

    /**
     * 程序异常处理
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MsgBody handleException(HttpServletRequest request, Exception e) {
        log.error("系统异常，请求路径  = {}， 错误信息 = {}，堆栈信息 = {}", request.getRequestURI(), e.getMessage(), e);
        return MsgBody.getInstance(ResCodeEnum.SYSTEM_ERROR);
    }

}
