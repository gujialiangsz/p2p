package com.biocome.clearing.core.entity.account;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.biocome.base.model.AutoIncrementEntity;

@Entity
@Table(name = "WCC_FILE_SEND_LIST")
public class WccFileSendList extends AutoIncrementEntity {

	@Column(columnDefinition = "datetime comment 'WCC->外部，发送日期'")
	private Date sendDate;
	@Column(length = 8, columnDefinition = "char(8) comment '城市代码'")
	private String cityCode;
	@Column(columnDefinition = "varchar(50) comment '文件名'")
	private String fileName;
	@Column(columnDefinition = "int comment '记录总数'")
	private int recordNum;
	@Column(columnDefinition = "int comment '发送次数'")
	private int sendTimes;

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

	public int getSendTimes() {
		return sendTimes;
	}

	public void setSendTimes(int sendTimes) {
		this.sendTimes = sendTimes;
	}

}
