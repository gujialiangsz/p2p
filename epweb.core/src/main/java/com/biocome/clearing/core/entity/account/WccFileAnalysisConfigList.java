package com.biocome.clearing.core.entity.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.biocome.clearing.core.enums.DataTypeEnum;
import com.biocome.clearing.core.enums.FillRule;

/**
 * 
 * 
 * @author 谷家良
 * @date 2015年4月29日 下午3:56:17
 * @Description: TODO
 */
@Entity
@Table(name = "WCC_FILE_ANALYSIS_CONFIG_LIST")
@IdClass(WccFileAnalysisConfigListId.class)
public class WccFileAnalysisConfigList implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(columnDefinition = "varchar(10) comment '数据来源类型代号'")
	private String sourceType;
	@Id
	@Column(columnDefinition = "varchar(8) comment '城市代号'")
	private String cityCode;
	@Id
	@Column(columnDefinition = "varchar(20) comment '字段名称'")
	private String fieldName;
	@Column(columnDefinition = "int comment '字段序号，从0开始'")
	private int columnNo;
	@Type(type = "IEnum")
	@Column(columnDefinition = "varchar(20) comment '数据类型，INT,LONG,DATE,STRING,BIGDECIMAL,CHAR,DOUBLE'")
	private DataTypeEnum dataType;
	@Column(columnDefinition = "varchar(20) comment '数据格式，多指日期格式'")
	private String dataFormat;
	@Column(columnDefinition = "int comment '数据开始位置，0开始'")
	private int dataBegin;
	@Column(columnDefinition = "int comment '数据结束位置'")
	private int dataEnd;
	@Column(columnDefinition = "char(1) comment '填充字符，空格也要填写'")
	private Character fillFlag;
	@Column(columnDefinition = "varchar(2) comment '填充规则，L左填充，R右填充'")
	@Type(type = "IEnum")
	private FillRule fillRule;
	@Column(columnDefinition = "varchar(50) comment '默认值'")
	private String defaultVal;
	@Column(columnDefinition = "int comment '填充长度'")
	private int fillLength;
	@Column(columnDefinition = "varchar(20) comment '最大值'")
	private String max;
	@Column(columnDefinition = "varchar(20) comment '最小值'")
	private String min;

	@Transient
	public WccFileAnalysisConfigListId getId() {
		return new WccFileAnalysisConfigListId(this.sourceType, this.cityCode,
				this.fieldName);
	}

	@Transient
	public int getDataLength() {
		return dataEnd - dataBegin;
	}

	public int getFillLength() {
		return fillLength;
	}

	public void setFillLength(int fillLength) {
		this.fillLength = fillLength;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setColumnNo(int columnNo) {
		this.columnNo = columnNo;
	}

	public Integer getColumnNo() {
		return columnNo;
	}

	public void setColumnNo(Integer columnNo) {
		this.columnNo = columnNo;
	}

	public DataTypeEnum getDataType() {
		return dataType;
	}

	public void setDataType(DataTypeEnum dataType) {
		this.dataType = dataType;
	}

	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	public int getDataBegin() {
		return dataBegin;
	}

	public void setDataBegin(int dataBegin) {
		this.dataBegin = dataBegin;
	}

	public int getDataEnd() {
		return dataEnd;
	}

	public void setDataEnd(int dataEnd) {
		this.dataEnd = dataEnd;
	}

	public String getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public Character getFillFlag() {
		return fillFlag;
	}

	public void setFillFlag(Character fillFlag) {
		this.fillFlag = fillFlag;
	}

	public FillRule getFillRule() {
		return fillRule;
	}

	public void setFillRule(FillRule fillRule) {
		this.fillRule = fillRule;
	}

}
