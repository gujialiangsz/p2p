package com.biocome.clearing.core.entity.account;

/**
 * 
 * 
 * @author 谷家良
 * @date 2015年4月29日 下午3:55:43
 * @Description: TODO
 */
public class WccFileAnalysisConfigListId implements java.io.Serializable {

	private String cityCode;
	private String sourceType;
	private String fieldName;

	public WccFileAnalysisConfigListId() {
	}

	public WccFileAnalysisConfigListId(String sourceType, String cityCode,
			String fieldName) {
		super();
		this.sourceType = sourceType;
		this.cityCode = cityCode;
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cityCode == null) ? 0 : cityCode.hashCode());
		result = prime * result
				+ ((fieldName == null) ? 0 : fieldName.hashCode());
		result = prime * result
				+ ((sourceType == null) ? 0 : sourceType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WccFileAnalysisConfigListId other = (WccFileAnalysisConfigListId) obj;
		if (cityCode == null) {
			if (other.cityCode != null)
				return false;
		} else if (!cityCode.equals(other.cityCode))
			return false;
		if (fieldName == null) {
			if (other.fieldName != null)
				return false;
		} else if (!fieldName.equals(other.fieldName))
			return false;
		if (sourceType == null) {
			if (other.sourceType != null)
				return false;
		} else if (!sourceType.equals(other.sourceType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WccFileAnalysisConfigListId [cityCode=" + cityCode
				+ ", sourceType=" + sourceType + ", fieldName=" + fieldName
				+ "]";
	}

}
