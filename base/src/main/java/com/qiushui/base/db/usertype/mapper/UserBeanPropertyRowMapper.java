package com.qiushui.base.db.usertype.mapper;

import org.springframework.beans.BeanWrapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.qiushui.base.model.IEnum;

public class UserBeanPropertyRowMapper<T> extends BeanPropertyRowMapper<T> {
	@Override
	protected void initBeanWrapper(BeanWrapper bw) {
		super.initBeanWrapper(bw);
		bw.registerCustomEditor(IEnum.class, "status", new IEnumEditor());
	}
}
