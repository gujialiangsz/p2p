package com.biocome.clearing.core.entity.account;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "WCC_TERMINAL_INFO_LIST")
@IdClass(value = WccTerminalListId.class)
public class WccTerminalList implements Serializable {

	@Id
	@Column(columnDefinition = "char(8) comment '城市代码'")
	private String cityCode;
	@Id
	@Column(columnDefinition = "varchar(20) comment '终端编码'")
	private String terminalNo;
	@Column(columnDefinition = "varchar(50) comment '终端名称'")
	private String terminalName;
	@Column(columnDefinition = "varchar(8) comment '终端类型'")
	private String terminalType;
	@Column(columnDefinition = "varchar(8) comment '运营商代码'")
	private String operatorNo;
	@Column(columnDefinition = "datetime comment '生产日期'")
	private Date productDate;

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public String getTerminalName() {
		return terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

	public Date getProductDate() {
		return productDate;
	}

	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}

}
