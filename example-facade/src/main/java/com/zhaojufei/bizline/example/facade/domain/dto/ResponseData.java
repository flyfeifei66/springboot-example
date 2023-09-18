package com.zhaojufei.bizline.example.facade.domain.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.zhaojufei.bizline.example.facade.domain.enums.ResCodeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@ToString
public class ResponseData<T> implements Serializable {
	private static final long serialVersionUID = 6556009690114121528L;
	private String code;
	private String msg;
	private T data;

	public ResponseData(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ResponseData(String code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}


	/**
	 * 通过ResponseCodeEnum构造接口返回体，推荐使用
	 */
	public static ResponseData getResponseData(ResCodeEnum res) {
		ResponseData body = new ResponseData();
		body.setCode(res.getCode());
		body.setMsg(res.getDesc());
		return body;
	}

	/**
	 * 通过ResponseCodeEnum构造接口返回体，推荐使用
	 */
	public static ResponseData getResponseData(ResCodeEnum res, Object data) {
		ResponseData body = getResponseData(res);
		body.setData(data);
		return body;
	}

	/**
	 * 方法名与两个参数的getResponseData区分开来，防止程序调用错。（否则不同jdk版本的选择可能不同）
	 */
	public static ResponseData getResponseDataByMsg(ResCodeEnum res, String msg) {
		ResponseData body = getResponseData(res);
		body.setMsg(msg);
		return body;
	}

	/**
	 * 通过ResponseCodeEnum构造接口返回体，推荐使用
	 */
	public static ResponseData getResponseData(ResCodeEnum res, String msg, Object data) {
		ResponseData body = getResponseData(res);
		body.setMsg(msg);
		body.setData(data);
		return body;
	}

	/**
	 * 完全自定义
	 */
	public static ResponseData getResponseData(String code, String msg, Object data) {
		ResponseData body = new ResponseData();
		body.setCode(code);
		body.setMsg(msg);
		body.setData(data);
		return body;
	}

	/**
	 * 返回成功
	 */
	public static ResponseData getSuccessRes() {
		ResponseData body = getResponseData(ResCodeEnum.SUCCESS);
		return body;
	}

	/**
	 * 返回成功
	 */
	public static ResponseData getSuccessResData(Object data) {
		ResponseData body = getResponseData(ResCodeEnum.SUCCESS);
		body.setData(data);
		return body;
	}

	/**
	 * 是否成功
	 *
	 * @return
	 */
	@JsonIgnore
	public boolean isSuccess() {
		return this.code.equals(ResCodeEnum.SUCCESS.getCode());
	}
}