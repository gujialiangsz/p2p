package com.biocome.clearing.core.config;

import org.springframework.stereotype.Component;

import com.biocome.base.db.AbstractLocalSessionSettings;

/**
 * SessionFactory设置。
 */
@Component("com.biocome.clearing.core.config.LocalSessionSettings")
public class LocalSessionSettings extends AbstractLocalSessionSettings {
	/**
	 * 构造方法。
	 */
	public LocalSessionSettings() {
		addPackageToScan("com.biocome.clearing.core.entity.*");
	}
}