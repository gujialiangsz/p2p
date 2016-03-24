package com.biocome.clearing.core.entity.account;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "WCC_ACCOUNT_TINY_TRADE_LIST")
@IdClass(TinyTradeRecordListId.class)
@DynamicUpdate
public class TinyTradeRecordList implements Serializable {

	@Id
	@Column(columnDefinition = "datetime comment '交易时间'")
	private Date tradeDate;
	@Id
	@Column(columnDefinition = "char(8) comment '城市代码'")
	private String cityCode;
	@Id
	@Column(columnDefinition = "varchar(8)  comment '交易类型'")
	private String tradeType;
	@Id
	@Column(columnDefinition = "varchar(36) comment '卡号'")
	private String cardNo;
	@Id
	@Column(columnDefinition = "int comment '卡交易序号'")
	private int tradeSn;
	@Column(columnDefinition = "varchar(36) comment '用户ID'")
	private String userId;
	@Column(columnDefinition = "char(4) comment '行业类型，交通卡，加油卡'")
	private String industryType;
	@Column(columnDefinition = "int comment '交易金额'")
	private int tradeMoney;
	@Column(columnDefinition = "int comment '交易前金额'")
	private int tradeBefore;
	@Column(columnDefinition = "int comment '交易后金额'")
	private int tradeAfter;
	@Column(columnDefinition = "varchar(20) comment '设备ID'")
	private String deviceId;
	@Column(columnDefinition = "varchar(20) comment 'sam卡号'")
	private String samNo;
	@Column(columnDefinition = "datetime comment '数据处理日期'")
	private Date dealDate;
	@Transient
	private double changeTradeMoney;

	// @Transient
	// private double changeTradeAfter;
	//
	// @Transient
	// public double getChangeTradeAfter() {
	// changeTradeAfter = (double) changeTradeAfter / 100;
	// return changeTradeAfter;
	// }

	@Transient
	public double getChangeTradeMoney() {
		changeTradeMoney = (double) tradeMoney / 100;
		return changeTradeMoney;
	}

	@Transient
	public TinyTradeRecordListId getId() {
		return new TinyTradeRecordListId(tradeDate, cityCode, tradeType,
				cardNo, tradeSn);
	}

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

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public Date getDealDate() {
		return dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public int getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(int tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public int getTradeBefore() {
		return tradeBefore;
	}

	public void setTradeBefore(int tradeBefore) {
		this.tradeBefore = tradeBefore;
	}

	public int getTradeAfter() {
		return tradeAfter;
	}

	public void setTradeAfter(int tradeAfter) {
		this.tradeAfter = tradeAfter;
	}

	public int getTradeSn() {
		return tradeSn;
	}

	public void setTradeSn(int tradeSn) {
		this.tradeSn = tradeSn;
	}

	public String getSamNo() {
		return samNo;
	}

	public void setSamNo(String samNo) {
		this.samNo = samNo;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setChangeTradeMoney(double changeTradeMoney) {
		this.changeTradeMoney = changeTradeMoney;
	}

}
