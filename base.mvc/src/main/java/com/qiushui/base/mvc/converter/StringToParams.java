package com.qiushui.base.mvc.converter;

import org.springframework.core.convert.converter.Converter;

import com.qiushui.base.model.Params;

/**
 * 字符串转换成Params转换器。
 */
public class StringToParams implements Converter<String, Params> {
	@Override
	public Params convert(String source) {
		Params params = new Params();
		params.fromString(source);
		return params;
	}
}
