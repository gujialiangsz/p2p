package com.qiushui.base.xstream;

import com.qiushui.base.model.IEnum;
import com.qiushui.base.util.IEnumUtils;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

/**
 * IEnum枚举转换器。
 */
public class IEnumConverter extends AbstractSingleValueConverter {
	private final Class<? extends IEnum> enumType;

	/**
	 * 构造方法。
	 * 
	 * @param type
	 *            枚举类型
	 */
	public IEnumConverter(Class<? extends IEnum> type) {
		enumType = type;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return enumType.isAssignableFrom(type);
	}

	@Override
	public String toString(Object obj) {
		return ((IEnum) obj).getValue();
	}

	@Override
	public Object fromString(String str) {
		return IEnumUtils.getIEnumByValue(enumType, str);
	}
}