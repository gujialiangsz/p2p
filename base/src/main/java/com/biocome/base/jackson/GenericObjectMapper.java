package com.biocome.base.jackson;

import java.text.SimpleDateFormat;

import com.biocome.base.util.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * 对Jackson的ObjectMapper封装。设置json的输出格式、时间格式及Hibernate懒加载支持。
 */
public class GenericObjectMapper extends ObjectMapper {
	/**
	 * 构造方法。
	 */
	public GenericObjectMapper() {
		configure(SerializationFeature.INDENT_OUTPUT, true);
		setDateFormat(new SimpleDateFormat(DateUtils.SECOND));
		Hibernate4Module hibernateModule = new Hibernate4Module();
		hibernateModule.configure(Hibernate4Module.Feature.FORCE_LAZY_LOADING,
				true);
		registerModule(hibernateModule);
		registerModule(new com.fasterxml.jackson.datatype.joda.JodaModule());
	}
}