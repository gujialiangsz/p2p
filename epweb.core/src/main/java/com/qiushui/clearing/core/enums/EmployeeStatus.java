package com.qiushui.clearing.core.enums;

import com.qiushui.base.model.IEnum;

/**
 * 
 * 
 * @author 谷家良
 * @date 2015年5月7日 下午5:11:18
 * @Description: TODO
 */
public enum EmployeeStatus implements IEnum {
	WORK("在职", "Y"), OFFWORK("离职", "N");

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
	private EmployeeStatus(String text, String value) {
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
