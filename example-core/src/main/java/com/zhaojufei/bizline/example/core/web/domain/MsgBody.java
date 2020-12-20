package com.zhaojufei.bizline.example.core.web.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.zhaojufei.bizline.example.core.web.enums.ResCodeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
@ToString
public class MsgBody<T> implements Serializable {
    private static final long serialVersionUID = 6556009690114121528L;
    private String code;
    private String msg;
    private T data;

    public MsgBody(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public MsgBody(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    /**
     * 通过ResponseCodeEnum构造接口返回体
     */
    public static MsgBody getInstance(ResCodeEnum res) {
        MsgBody body = new MsgBody();
        body.setCode(res.getCode());
        body.setMsg(res.getDesc());
        return body;
    }

    /**
     * 通过ResponseCodeEnum构造接口返回体
     */
    public static MsgBody getInstance(ResCodeEnum res, Object data) {
        MsgBody body = getInstance(res);
        body.setData(data);
        return body;
    }

    /**
     * 方法名与两个参数的getResponseData区分开来，防止程序调用错。（否则不同jdk版本的选择可能不同）
     */
    public static MsgBody getInstance(ResCodeEnum res, String msg) {
        MsgBody body = getInstance(res);
        body.setMsg(msg);
        return body;
    }

    /**
     * 通过ResponseCodeEnum构造接口返回体
     */
    public static MsgBody getInstance(ResCodeEnum res, String msg, Object data) {
        MsgBody body = getInstance(res);
        body.setMsg(msg);
        body.setData(data);
        return body;
    }

    /**
     * 完全自定义
     */
    public static MsgBody getInstance(String code, String msg, Object data) {
        MsgBody body = new MsgBody();
        body.setCode(code);
        body.setMsg(msg);
        body.setData(data);
        return body;
    }

    /**
     * 返回成功
     */
    public static MsgBody getSuccessInstance() {
        MsgBody body = getInstance(ResCodeEnum.SUCCESS);
        return body;
    }

    /**
     * 返回成功
     */
    public static MsgBody getSuccessInstance(Object data) {
        MsgBody body = getSuccessInstance(ResCodeEnum.SUCCESS);
        body.setData(data);
        return body;
    }

    /**
     * 是否成功
     *
     * @return
     */
    @JSONField(serialize = false)
    public boolean isSuccess() {
        return ResCodeEnum.SUCCESS.equals(code);
    }
}