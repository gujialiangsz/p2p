package com.biocome.base.security.config;

import org.springframework.stereotype.Component;

import com.biocome.base.mvc.config.AbstractFreeMarkerSettings;

/**
 * FreeMarker设置组件。
 */
@Component("com.biocome.base.mvc.config.security.config.FreeMarkerSettings")
public class FreeMarkerSettings extends AbstractFreeMarkerSettings {
	/**
	 * 构造方法。
	 */
	public FreeMarkerSettings() {
		addTemplatePath("classpath:/com/biocome/base/security/macros/");
		addTemplatePath("classpath:/com/biocome/base/security/actions/");
		addEnumPackage("com.biocome.base.security.enums");
		addAutoInclude("security.ftl");
		addAutoImport("sec", "sec.ftl");
	}
}