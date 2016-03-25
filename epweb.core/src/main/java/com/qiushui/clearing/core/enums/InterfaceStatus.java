package com.qiushui.clearing.core.enums;

import com.qiushui.base.model.IEnum;

public enum InterfaceStatus implements IEnum {

	PREPARE("准备", "0"), CODE("编码", "1"), DEBUG("调试", "2"), TEST("测试", "3"), STABLE(
			"稳定", "4");
	private String text;
	private String value;

	private InterfaceStatus(String text, String value) {
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