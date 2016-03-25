package com.qiushui.base.mvc.converter;

import org.springframework.core.convert.converter.Converter;

import com.qiushui.base.model.UuidEntity;

/**
 * UuidEntity转换为字符串转换器。
 */
public class UuidEntityToString implements Converter<UuidEntity, String> {
	@Override
	public String convert(UuidEntity source) {
		return source.getId();
	}
}
