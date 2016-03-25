package com.qiushui.base.security.config;

import org.springframework.stereotype.Component;

import com.qiushui.base.mvc.config.AbstractFreeMarkerSettings;

/**
 * FreeMarker设置组件。
 */
@Component("com.qiushui.base.mvc.config.security.config.FreeMarkerSettings")
public class FreeMarkerSettings extends AbstractFreeMarkerSettings {
	/**
	 * 构造方法。
	 */
	public FreeMarkerSettings() {
		addTemplatePath("classpath:/com/qiushui/base/security/macros/");
		addTemplatePath("classpath:/com/qiushui/base/security/actions/");
		addEnumPackage("com.qiushui.base.security.enums");
		addAutoInclude("security.ftl");
		addAutoImport("sec", "sec.ftl");
	}
}