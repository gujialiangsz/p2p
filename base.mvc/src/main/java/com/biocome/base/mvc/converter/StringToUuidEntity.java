package com.biocome.base.mvc.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import com.biocome.base.db.dao.DaoUtils;
import com.biocome.base.model.UuidEntity;

/**
 * 字符串转换成UuidEntity转换器工厂。
 */
public class StringToUuidEntity implements ConverterFactory<String, UuidEntity> {
	@Override
	public <T extends UuidEntity> Converter<String, T> getConverter(
			Class<T> targetType) {
		return new StringToUuidEntityConverter<T>(targetType);
	}

	/**
	 * 字符串转换成UuidEntity转换器。
	 */
	private class StringToUuidEntityConverter<T extends UuidEntity> implements
			Converter<String, T> {
		private final Class<T> toClass;

		/**
		 * 构造方法。
		 * 
		 * @param toClass
		 *            转换目标类
		 */
		public StringToUuidEntityConverter(Class<T> toClass) {
			this.toClass = toClass;
		}

		/**
		 * 将字符串值转换为UuidEntity对象。
		 * 
		 * @param source
		 *            字符串值
		 * @return 返回UuidEntity对象。
		 */
		public T convert(String source) {
			if (source.length() == 0) {
				return null;
			}
			return (T) DaoUtils.getUuidEntity(toClass, source.trim());
		}
	}
}
