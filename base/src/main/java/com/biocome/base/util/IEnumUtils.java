package com.biocome.base.util;

import java.lang.reflect.Method;

import com.biocome.base.exception.UncheckedException;
import com.biocome.base.model.IEnum;

/**
 * 自定义枚举工具类。
 */
public class IEnumUtils {
	/**
	 * 根据值获取对应的枚举值。
	 * 
	 * @param <T>
	 *            枚举类型
	 * 
	 * @param enumClass
	 *            枚举类
	 * @param value
	 *            值
	 * @return 返回值对应的枚举值。
	 */
	@SuppressWarnings("unchecked")
	public static <T extends IEnum> T getIEnumByValue(Class<T> enumClass,
			String value) {
		try {
			Method method = enumClass.getMethod("values");
			for (T item : (T[]) method.invoke(enumClass)) {
				if (item.getValue().equals(value)) {
					return item;
				}
			}
		} catch (Exception e) {
			throw new UncheckedException("获取枚举值时发生异常。", e);
		}
		return null;
	}

	/**
	 * 根据文本获取对应的枚举值。
	 * 
	 * @param <T>
	 *            枚举类型
	 * @param enumClass
	 *            枚举类
	 * @param text
	 *            文本
	 * @return 返回文本对应的枚举值。
	 */
	@SuppressWarnings("unchecked")
	public static <T extends IEnum> T getIEnumByText(Class<T> enumClass,
			String text) {
		try {
			Method method = enumClass.getMethod("values");
			for (T item : (T[]) method.invoke(enumClass)) {
				if (item.getText().equals(text)) {
					return item;
				}
			}
		} catch (Exception e) {
			throw new UncheckedException("获取枚举值时发生异常。", e);
		}
		return null;
	}
}
