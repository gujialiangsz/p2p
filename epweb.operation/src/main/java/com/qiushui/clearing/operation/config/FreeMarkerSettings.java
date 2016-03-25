package com.qiushui.clearing.operation.config;

import org.springframework.stereotype.Component;

import com.qiushui.base.mvc.config.AbstractFreeMarkerSettings;

/**
 * FreeMarker设置组件。
 */
@Component("com.qiushui.clearing.operation.config.FreeMarkerSettings")
public class FreeMarkerSettings extends AbstractFreeMarkerSettings {
	/**
	 * 构造方法。
	 */
	public FreeMarkerSettings() {
		setOrder(100);
		addTemplatePath("classpath:/com/qiushui/clearing/operation/macros/");
		addTemplatePath("classpath:/com/qiushui/clearing/operation/actions/");
		addAutoImport("system", "system.ftl");
		addAutoImport("resource", "resource.ftl");
		addEnumPackage("com.qiushui.clearing.operation.enums");
		addEnumPackage("com.qiushui.clearing.core.enums");
	}
}