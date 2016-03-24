package com.biocome.clearing.operation.config;

import org.springframework.stereotype.Component;

import com.biocome.base.mvc.config.AbstractFreeMarkerSettings;

/**
 * FreeMarker设置组件。
 */
@Component("com.biocome.clearing.operation.config.FreeMarkerSettings")
public class FreeMarkerSettings extends AbstractFreeMarkerSettings {
	/**
	 * 构造方法。
	 */
	public FreeMarkerSettings() {
		setOrder(100);
		addTemplatePath("classpath:/com/biocome/clearing/operation/macros/");
		addTemplatePath("classpath:/com/biocome/clearing/operation/actions/");
		addAutoImport("system", "system.ftl");
		addAutoImport("resource", "resource.ftl");
		addEnumPackage("com.biocome.clearing.operation.enums");
		addEnumPackage("com.biocome.clearing.core.enums");
	}
}