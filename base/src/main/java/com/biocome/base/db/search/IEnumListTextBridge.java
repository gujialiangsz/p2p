package com.biocome.base.db.search;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.search.bridge.StringBridge;

import com.biocome.base.model.IEnum;
import com.biocome.base.util.CollectionUtils;
import com.biocome.base.util.StringUtils;

/**
 * IEnumList枚举类型字段对text属性进行全文索引的桥接器。
 */
public class IEnumListTextBridge implements StringBridge {
	@Override
	public String objectToString(Object object) {
		@SuppressWarnings("unchecked")
		List<IEnum> ienums = (ArrayList<IEnum>) object;
		if (CollectionUtils.isEmpty(ienums)) {
			return "";
		}
		List<String> enumTexts = new ArrayList<String>();
		for (IEnum ienum : ienums) {
			enumTexts.add(ienum.getText());
		}
		return StringUtils.join(enumTexts, " ");
	}
}
