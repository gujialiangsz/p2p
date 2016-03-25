package com.qiushui.base.db;

import com.qiushui.base.model.IEnum;

public enum SqlExpression implements IEnum {
	EQ("等于", " = "), LIKE("LIKE", " LIKE "), NQ("不等于", " <> "), GT("大于", " > "), GE(
			"大于或等于", " >= "), LT("小于", " < "), LE("小于或等于", " <= "), IN("IN",
			" IN ");

	String text;
	String value;

	private SqlExpression(String text, String value) {
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
