package com.biocome.clearing.core.enums;

import com.biocome.base.model.IEnum;

/**
 * 
 * 
 * @author 谷家良
 * @date 2015年5月7日 下午5:11:18
 * @Description: TODO
 */
public enum Sex implements IEnum {
	MALE("男", "M"), FEMALE("女", "F");

	private String text;
	private String value;

	/**
	 * 构造方法。
	 * 
	 * @param text
	 *            文本
	 * @param value
	 *            值
	 */
	private Sex(String text, String value) {
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
