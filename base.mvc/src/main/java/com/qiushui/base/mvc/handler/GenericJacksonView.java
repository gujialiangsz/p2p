package com.qiushui.base.mvc.handler;

import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.qiushui.base.jackson.GenericObjectMapper;

/**
 * 自定义基于Jackson2的json视图。
 */
public class GenericJacksonView extends MappingJackson2JsonView {
	/**
	 * 构造方法。
	 */
	public GenericJacksonView() {
		setObjectMapper(new GenericObjectMapper());
		setExtractValueFromSingleKeyModel(true);
	}
}
