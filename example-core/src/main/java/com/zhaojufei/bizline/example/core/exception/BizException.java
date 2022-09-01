package com.zhaojufei.bizline.example.core.exception;


import com.zhaojufei.bizline.example.facade.domain.enums.ResCodeEnum;

/**
 * 业务异常类
 *
 * @author zhaojufei
 */
public class BizException extends RuntimeException {

    private String code = "500";

    private Object data;

    public BizException() {
    }

    public BizException(ResCodeEnum ResCodeEnum) {
        super(ResCodeEnum.getDesc());
        this.code = ResCodeEnum.getCode();
    }

    public BizException(ResCodeEnum ResCodeEnum, String message) {
        super(message);
        this.code = ResCodeEnum.getCode();
    }

    public BizException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BizException(String code, String message, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
