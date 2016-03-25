package com.qiushui.base.db.usertype.mapper;

import java.beans.PropertyEditorSupport;

import com.qiushui.base.model.IEnum;

public class IEnumEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(final String text) throws IllegalArgumentException {
		setValue(text);
	}

	@Override
	public void setValue(final Object value) {
		super.setValue(value);
	}

	@Override
	public IEnum getValue() {
		return (IEnum) super.getValue();
	}

	@Override
	public String getAsText() {
		return getValue().getValue();
	}
}
