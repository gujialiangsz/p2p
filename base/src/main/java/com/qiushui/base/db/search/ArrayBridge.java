package com.qiushui.base.db.search;

import org.hibernate.search.bridge.StringBridge;

import com.qiushui.base.util.CollectionUtils;
import com.qiushui.base.util.StringUtils;

/**
 * 字符串数组字段全文索引桥接器。
 */
public class ArrayBridge implements StringBridge {
	@Override
	public String objectToString(Object object) {
		String[] strs = (String[]) object;
		if (CollectionUtils.isEmpty(strs)) {
			return "";
		}
		return StringUtils.join(strs, " ");
	}
}
