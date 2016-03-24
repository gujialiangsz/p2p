package com.biocome.clearing.core.entity.account;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.biocome.base.model.AutoIncrementEntity;

@Entity
@Table(name = "WCC_ORIGINAL_TRADE_LIST")
@DynamicUpdate
public class WccOriginalTradeRecord extends AutoIncrementEntity {

	@Column(columnDefinition = "varchar(36) comment '用户ID'")
	private String userId;
	@Column(columnDefinition = "varchar(36) comment '卡号'")
	private String cardNo;
	@Column(columnDefinition = "char(8) comment '城市代码'")
	private String cityCode;
	@Column(columnDefinition = "char(8) comment '行业类型'")
	private String industryType;
	@Column(columnDefinition = "char(8) comment '设备Id'")
	private String deviceId;
	@Column(columnDefinition = "datetime comment '同步时间'")
	private Date syncDate;
	@Column(columnDefinition = "tinyint(2) comment '处理标识，默认值0未处理，1正常，2异常（可细化）'")
	private int dealStatus;
	@Column(columnDefinition = "datetime comment '处理日期'")
	private Date dealDate;
	@Column(columnDefinition = "varchar(1000) comment '报文内容'")
	private String content;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public Date getSyncDate() {
		return syncDate;
	}

	public void setSyncDate(Date syncDate) {
		this.syncDate = syncDate;
	}

	public Date getDealDate() {
		return dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(int dealStatus) {
		this.dealStatus = dealStatus;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
