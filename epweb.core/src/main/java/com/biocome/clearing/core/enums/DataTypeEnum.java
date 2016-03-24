package com.biocome.clearing.core.enums;

import com.biocome.base.model.IEnum;

public enum DataTypeEnum implements IEnum {
	INT("", "INT"), LONG("", "LONG"), DATE("", "DATE"), STRING("", "STRING"), BIGDECIMAL(
			"", "BIGDECIMAL"), CHAR("", "CHAR"), DOUBLE("", "DOUBLE"), LOW_BYTE(
			"", "LOW_BYTE"), HIGH_BYTE("", "HIGH_BYTE"), BCD("", "BCD");

	private String text;
	private String value;

	private DataTypeEnum(String text, String value) {
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
