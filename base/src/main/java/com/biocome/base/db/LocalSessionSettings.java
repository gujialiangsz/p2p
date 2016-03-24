package com.biocome.base.db;

import org.springframework.stereotype.Component;

/**
 * SessionFactory设置。
 */
@Component("com.biocome.base.db.LocalSessionSettings")
public class LocalSessionSettings extends AbstractLocalSessionSettings {
	/**
	 * 构造方法。
	 */
	public LocalSessionSettings() {
		addAnnotatedPackage("com.biocome.base.db.usertype");
	}
}
