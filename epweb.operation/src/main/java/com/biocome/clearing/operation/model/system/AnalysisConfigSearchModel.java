package com.biocome.clearing.operation.model.system;

import com.biocome.base.annotations.Restriction;
import com.biocome.base.model.SearchModel;

@Restriction
public class AnalysisConfigSearchModel extends SearchModel {

	private String cityCode;
	private String sourceType;
	private String fieldName;

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
