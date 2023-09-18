package com.zhaojufei.bizline.example.core.util;


import org.apache.commons.lang3.StringUtils;


/**
 * 自定义字符串处理类，优先使用apache common包的工具类
 * 工具类中没有的，组合使用工具类的API构建自定义的方法
 * 组合API也无法实现的，参考工具类的源码写自己的方法
 * 
 * @author zhaojufei
 */
public class DmStringUtil {

	public static void main(String[] args) {
	}

	/**
	 * 获取以空格符隔开的字符串的前缀，也就是第一段，支持多个空格)。
	 * 
	 * @param str
	 * @return
	 */
	public static String getPrefixByWhitespace(String str) {
		if (StringUtils.isNotBlank(str)) {
			int strLen = str.length();
			// 默认取到最后
			int index = strLen;
			for (int i = 0; i < strLen; ++i) {
				if (Character.isWhitespace(str.charAt(i))) {
					index = i;
					break;
				}
			}
			return str.substring(0, index);
		}

		return str;
	}
}
