package com.biocome.clearing.core.entity.account;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * 
 * @author 谷家良
 * @date 2015年4月29日 下午1:56:58
 * @Description: TODO
 */
@Entity
@Table(name = "WCC_FILE_ANALYSIS_CONFIG_MAIN")
@IdClass(WccFileAnalysisConfigMainId.class)
public class WccFileAnalysisConfigMain implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(columnDefinition = "varchar(10) comment '数据来源类型代号'")
	private String sourceType;
	@Id
	@Column(columnDefinition = "varchar(8) comment '城市代号'")
	private String cityCode;
	@Column(columnDefinition = "tinyint(1) comment '是否校验长度'")
	private Boolean checkLength;
	@Column(columnDefinition = "tinyint(1) comment '是否校验数量'")
	private Boolean checkSize;
	@Column(columnDefinition = "tinyint(1) comment '是否启用转换器'")
	private Boolean isParsed;
	@Column(columnDefinition = "varchar(20) comment '文件名格式'")
	private String fileNameFormat;
	@Column(columnDefinition = "varchar(6) comment '文件处理方式，read读，write写'")
	private String fileDealType;
	@Column(columnDefinition = "varchar(20) comment '文件监听条件表达式'")
	private String fileAcceptExpression;
	@Column(columnDefinition = "int comment '数据最小长度'")
	private Integer dataMinLength;
	@Column(columnDefinition = "int comment '数据最大长度'")
	private Integer dataMaxLength;
	@Column(columnDefinition = "int comment '分页每页数量'")
	private Integer pageSize;
	@Column(columnDefinition = "int comment '文件每个数量'")
	private Integer fileSize;
	@Column(columnDefinition = "varchar(6) comment '字段分隔符'")
	private String fieldDivision;
	@Column(columnDefinition = "varchar(6) comment '行分隔符'")
	private String lineDivision;
	@Column(columnDefinition = "varchar(500) comment '备注'")
	private String remark;
	@ForeignKey(name = "null")
	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.REMOVE }, fetch = FetchType.EAGER)
	@JoinColumns(value = {
			@JoinColumn(name = "sourceType", referencedColumnName = "sourceType"),
			@JoinColumn(name = "cityCode", referencedColumnName = "cityCode") })
	private List<WccFileAnalysisConfigList> configList = new ArrayList<WccFileAnalysisConfigList>();

	@JsonIgnore
	public List<WccFileAnalysisConfigList> getConfigList() {
		return configList;
	}

	@Transient
	public WccFileAnalysisConfigMainId getId() {
		return new WccFileAnalysisConfigMainId(this.sourceType, this.cityCode);
	}

	public void setConfigList(List<WccFileAnalysisConfigList> configList) {
		this.configList = configList;
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

	public String getFieldDivision() {
		return fieldDivision;
	}

	public void setFieldDivision(String fieldDivision) {
		this.fieldDivision = fieldDivision;
	}

	public Boolean getCheckLength() {
		return checkLength;
	}

	public void setCheckLength(Boolean checkLength) {
		this.checkLength = checkLength;
	}

	public Boolean getCheckSize() {
		return checkSize;
	}

	public void setCheckSize(Boolean checkSize) {
		this.checkSize = checkSize;
	}

	public Boolean getIsParsed() {
		return isParsed;
	}

	public void setIsParsed(Boolean isParsed) {
		this.isParsed = isParsed;
	}

	public String getFileNameFormat() {
		return fileNameFormat;
	}

	public void setFileNameFormat(String fileNameFormat) {
		this.fileNameFormat = fileNameFormat;
	}

	public String getFileDealType() {
		return fileDealType;
	}

	public void setFileDealType(String fileDealType) {
		this.fileDealType = fileDealType;
	}

	public String getFileAcceptExpression() {
		return fileAcceptExpression;
	}

	public void setFileAcceptExpression(String fileAcceptExpression) {
		this.fileAcceptExpression = fileAcceptExpression;
	}

	public Integer getDataMinLength() {
		return dataMinLength;
	}

	public void setDataMinLength(Integer dataMinLength) {
		this.dataMinLength = dataMinLength;
	}

	public Integer getDataMaxLength() {
		return dataMaxLength;
	}

	public void setDataMaxLength(Integer dataMaxLength) {
		this.dataMaxLength = dataMaxLength;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public String getLineDivision() {
		return lineDivision;
	}

	public void setLineDivision(String lineDivision) {
		this.lineDivision = lineDivision;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "WccFileAnalysisConfigMain [sourceType=" + sourceType
				+ ", cityCode=" + cityCode + ", checkLength=" + checkLength
				+ ", checkSize=" + checkSize + ", isParsed=" + isParsed
				+ ", fileNameFormat=" + fileNameFormat + ", fileDealType="
				+ fileDealType + ", fileAcceptExpression="
				+ fileAcceptExpression + ", dataMinLength=" + dataMinLength
				+ ", dataMaxLength=" + dataMaxLength + ", pageSize=" + pageSize
				+ ", fileSize=" + fileSize + ", fieldDivision=" + fieldDivision
				+ ", lineDivision=" + lineDivision + ", remark=" + remark
				+ ", configList=" + configList + "]";
	}

}
