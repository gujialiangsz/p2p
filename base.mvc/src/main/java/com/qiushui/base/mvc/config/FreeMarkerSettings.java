package com.qiushui.base.mvc.config;

import org.springframework.stereotype.Component;

import com.qiushui.base.util.DateUtils;
import com.qiushui.base.util.StringUtils;

/**
 * FreeMarker配置组件。
 */
@Component("com.qiushui.base.mvc.config.FreeMarkerSettings")
public class FreeMarkerSettings extends AbstractFreeMarkerSettings {
	/**
	 * 构造方法。
	 */
	public FreeMarkerSettings() {
		addTemplatePath("classpath:/com/qiushui/base/mvc/macros/");
		addTemplatePath("classpath:/com/qiushui/base/mvc/templates/");
		addAutoImport("s", "mvc.ftl");
		addAutoImport("std", "std.ftl");
		addAutoImport("dwz", "dwz.ftl");
		addStaticClass(StringUtils.class);
		addStaticClass(DateUtils.class);
	}
}
