package com.qiushui.base.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.qiushui.base.db.SqlExpression;

/**
 * 
 * 
 * @author 谷家良
 * @date 2015年5月7日 上午11:44:23
 * @Description: 查询选择器注解，标识在类上表示所有字段都会进行读取解析，否则只读取已标识的字段
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
@Documented
@Inherited
public @interface Restriction {
	SqlExpression type() default SqlExpression.EQ;

	String propertyName() default "";

	String propertyPreffix() default "";

	String propertySuffix() default "";

}
