package com.qiushui.clearing.core.enums;

import com.qiushui.base.model.IEnum;

public enum AccessAuthority implements IEnum {
	OPEN("开放", "0"), APP("应用", "1"), USER("用户", "2"), SPECIES("设备", "3");
	private String text;
	private String value;

	private AccessAuthority(String text, String value) {
		this.text = text;
		this.value = value;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public String getValue() {
		return value;
	}
}
