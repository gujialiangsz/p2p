package com.biocome.clearing.core.entity.account;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.biocome.base.model.AutoIncrementEntity;

@Entity
@Table(name = "WCC_FILE_RECEIVE_LIST")
public class WccFileReceiveList extends AutoIncrementEntity {
	@Column(columnDefinition = "datetime comment '外部->WCC，发送日期'")
	private Date sendDate;
	@Column(columnDefinition = "char(8) comment '城市代码'")
	private String cityCode;
	@Column(columnDefinition = "varchar(50) comment '文件名称'")
	private String fileName;
	@Column(columnDefinition = "datetime comment '接收日期'")
	private Date receiveDate;
	@Column(columnDefinition = "int comment '记录总数'")
	private int recordNum;
	@Column(columnDefinition = "int comment '接收次数'")
	private int receiveTimes;

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(int recordNum) {
		this.recordNum = recordNum;
	}

	public int getReceiveTimes() {
		return receiveTimes;
	}

	public void setReceiveTimes(int receiveTimes) {
		this.receiveTimes = receiveTimes;
	}

}