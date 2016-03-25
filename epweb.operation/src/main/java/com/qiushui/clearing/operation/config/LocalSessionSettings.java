package com.qiushui.clearing.operation.config;

import org.springframework.stereotype.Component;

import com.qiushui.base.db.AbstractLocalSessionSettings;

/**
 * SessionFactory设置。
 */
@Component("com.qiushui.clearing.operation.config.LocalSessionSettings")
public class LocalSessionSettings extends AbstractLocalSessionSettings {
	/**
	 * 构造方法。
	 */
	public LocalSessionSettings() {
		addPackageToScan("com.qiushui.clearing.operation.entity");
	}
}