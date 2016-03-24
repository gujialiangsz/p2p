package com.biocome.clearing.core.entity.account;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Table(name = "WCC_ACCOUNT_CARDINFO")
@Entity
@IdClass(CardInfoId.class)
public class CardInfo implements Serializable {
	@Id
	@Column(columnDefinition = "varchar(36) comment '用户id'")
	private String userId;
	@Id
	@Column(columnDefinition = "varchar(36) comment '卡号'")
	private String cardNo;
	@Column(columnDefinition = "char(8) comment '城市代码'")
	private String cityCode;
	@Column(columnDefinition = "char(8) comment '卡类型'")
	private String cardType;
	@Column(columnDefinition = "varchar(8) comment '物理介质'")
	private String physicType;
	@Column(columnDefinition = "int comment '消费交易序号'")
	private Integer consumeSn;
	@Column(columnDefinition = "int comment '充值交易序号'")
	private Integer rechargeSn;
	@Column(columnDefinition = "datetime comment '开始日期'")
	private Date startDate;
	@Column(columnDefinition = "datetime comment '到期日期'")
	private Date endDate;
	@Column(columnDefinition = "double comment '押金'")
	private double deposit;
	@Column(columnDefinition = "double comment '余额'")
	private double banlance;
	@Column(columnDefinition = "double comment '累计充值'")
	private double totalRecharge;
	@Column(columnDefinition = "double comment '累计消费'")
	private double totalConsume;
	@Column(columnDefinition = "varchar(8) comment '最后一次交易类型'")
	private String lastTradeType;
	@Column(columnDefinition = "varchar(8) comment '最后一次交易日期'")
	private Date lastTradeDate;

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

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getPhysicType() {
		return physicType;
	}

	public void setPhysicType(String physicType) {
		this.physicType = physicType;
	}

	public Integer getConsumeSn() {
		return consumeSn;
	}

	public void setConsumeSn(Integer consumeSn) {
		this.consumeSn = consumeSn;
	}

	public Integer getRechargeSn() {
		return rechargeSn;
	}

	public void setRechargeSn(Integer rechargeSn) {
		this.rechargeSn = rechargeSn;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	public double getBanlance() {
		return banlance;
	}

	public void setBanlance(double banlance) {
		this.banlance = banlance;
	}

	public double getTotalRecharge() {
		return totalRecharge;
	}

	public void setTotalRecharge(double totalRecharge) {
		this.totalRecharge = totalRecharge;
	}

	public double getTotalConsume() {
		return totalConsume;
	}

	public void setTotalConsume(double totalConsume) {
		this.totalConsume = totalConsume;
	}

	public String getLastTradeType() {
		return lastTradeType;
	}

	public void setLastTradeType(String lastTradeType) {
		this.lastTradeType = lastTradeType;
	}

	public Date getLastTradeDate() {
		return lastTradeDate;
	}

	public void setLastTradeDate(Date lastTradeDate) {
		this.lastTradeDate = lastTradeDate;
	}

}
