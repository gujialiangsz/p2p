package com.qiushui.base.db.search;

import java.util.List;

import org.hibernate.search.bridge.StringBridge;

import com.qiushui.base.util.CollectionUtils;
import com.qiushui.base.util.StringUtils;

/**
 * 字符串列表字段全文索引桥接器。
 */
public class ArrayListBridge implements StringBridge {
	@Override
	public String objectToString(Object object) {
		@SuppressWarnings("unchecked")
		List<String> strs = (List<String>) object;
		if (CollectionUtils.isEmpty(strs)) {
			return "";
		}
		return StringUtils.join(strs, " ");
	}
}