package com.biocome.base.mvc.config;

import org.springframework.stereotype.Component;

import com.biocome.base.util.DateUtils;
import com.biocome.base.util.StringUtils;

/**
 * FreeMarker配置组件。
 */
@Component("com.biocome.base.mvc.config.FreeMarkerSettings")
public class FreeMarkerSettings extends AbstractFreeMarkerSettings {
	/**
	 * 构造方法。
	 */
	public FreeMarkerSettings() {
		addTemplatePath("classpath:/com/biocome/base/mvc/macros/");
		addTemplatePath("classpath:/com/biocome/base/mvc/templates/");
		addAutoImport("s", "mvc.ftl");
		addAutoImport("std", "std.ftl");
		addAutoImport("dwz", "dwz.ftl");
		addStaticClass(StringUtils.class);
		addStaticClass(DateUtils.class);
	}
}
