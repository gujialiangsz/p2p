package com.biocome.base.security.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.biocome.base.model.IEnum;

/**
 * 日志注解。
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {
	/**
	 * 日志记录目标对象位于方法参数中的位置，默认第1个参数，下标为0。
	 */
	int target() default 0;

	/**
	 * 日志信息编码，对应资源文件中定义的日志信息。
	 */
	String code() default "操作信息";

	/**
	 * 高级日志类型，默认不记录高级日志。
	 */
	LogType type() default LogType.NORMAL;

	/**
	 * 日志类型。
	 */
	public enum LogType implements IEnum {
		/** 普通日志 */
		NORMAL("普通日志", "0"),
		/** 登录日志 */
		LOGIN("登录日志", "1"),
		/** 对账日志 */
		ACCOUNT("对账日志", "2");
		String text;
		String value;

		private LogType(String text, String value) {
			this.text = text;
			this.value = value;
		}

		@Override
		public String getText() {
			return this.text;
		}

		@Override
		public String getValue() {
			return this.value;
		}
	}
}
