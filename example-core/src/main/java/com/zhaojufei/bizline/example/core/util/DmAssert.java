package com.zhaojufei.bizline.example.core.util;


import java.util.Collection;

import com.zhaojufei.bizline.example.core.exception.BizException;
import com.zhaojufei.bizline.example.facade.domain.enums.ResCodeEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * 断言工具类
 *
 * @author zhaojufei
 * @since 2019-6-21 16:45
 */
public class DmAssert {

	/**
	 * 字符串不为空断言
	 * 
	 * @param para
	 * @param res
	 */
	public static void notBlank(String para, ResCodeEnum res) {
		if (StringUtils.isBlank(para)) {
			throw new BizException(res);
		}
	}

	/**
	 * 集合不为空断言
	 * 
	 * @param collection
	 * @param res
	 */
	public static void notEmpty(Collection<?> collection, ResCodeEnum res) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new BizException(res);
		}
	}

	/**
	 * 不为null断言
	 * 
	 * @param object
	 * @param res
	 */
	public static void notNull(Object object, ResCodeEnum res) {
		if (object == null) {
			throw new BizException(res);
		}
	}

	/**
	 * 判断给定值知否符合给定的正则表达式
	 */
	public static void regex(String value, String regex, ResCodeEnum res) {
		if (!value.matches(regex)) {
			throw new BizException(res);
		}
	}

}
