package com.qiushui.base.db;

import org.springframework.stereotype.Component;

/**
 * SessionFactory设置。
 */
@Component("com.qiushui.base.db.LocalSessionSettings")
public class LocalSessionSettings extends AbstractLocalSessionSettings {
	/**
	 * 构造方法。
	 */
	public LocalSessionSettings() {
		addAnnotatedPackage("com.qiushui.base.db.usertype");
	}
}
