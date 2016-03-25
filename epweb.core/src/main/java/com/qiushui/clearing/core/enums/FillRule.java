package com.qiushui.clearing.core.enums;

import com.qiushui.base.model.IEnum;

public enum FillRule implements IEnum {

	LEFT("左补齐", "L"), RIGHT("右补齐", "R");
	private String text;
	private String value;

	private FillRule(String text, String value) {
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