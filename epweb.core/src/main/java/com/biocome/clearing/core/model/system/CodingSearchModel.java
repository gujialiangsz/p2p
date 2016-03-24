package com.biocome.clearing.core.model.system;

import com.biocome.base.annotations.Restriction;
import com.biocome.base.constants.SqlExpression;
import com.biocome.base.model.SearchModel;

@Restriction
public class CodingSearchModel extends SearchModel {
	private String code;
	private String value;
	@Restriction(type = SqlExpression.LIKE, propertySuffix = "%")
	private String type;
	@Restriction(type = SqlExpression.LIKE, propertyPreffix = "%", propertySuffix = "%")
	private String codeName;
	private Integer systemLevel;
	private boolean disable;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public Integer getSystemLevel() {
		return systemLevel;
	}

	public void setSystemLevel(Integer systemLevel) {
		this.systemLevel = systemLevel;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

}
