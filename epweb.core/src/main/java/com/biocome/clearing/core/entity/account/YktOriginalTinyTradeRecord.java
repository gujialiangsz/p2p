package com.biocome.clearing.core.entity.account;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.biocome.base.model.AutoIncrementEntity;

@Entity
@Table(name = "YKT_ORIGINAL_TINY_TRADE_LIST")
public class YktOriginalTinyTradeRecord extends AutoIncrementEntity {

	@Column(columnDefinition = "varchar(36) comment '用户ID'")
	private String userId;
	@Column(columnDefinition = "varchar(36) comment '卡号'")
	private String cardNo;
	@Column(columnDefinition = "char(8) comment '城市代码'")
	private String cityCode;
	@Column(columnDefinition = "char(8) comment '卡类型'")
	private String cardType;
	@Column(columnDefinition = "datetime comment '交易时间'")
	private Date tradeDate;
	@Column(columnDefinition = "char(4) comment '交易类型'")
	private String tradeType;
	@Column(columnDefinition = "char(4) comment '行业类型，交通卡，加油卡'")
	private String industryType;
	@Column(columnDefinition = "double comment '交易金额'")
	private double tradeMoney;
	@Column(columnDefinition = "double comment '交易前金额'")
	private double tradeBefore;
	@Column(columnDefinition = "double comment '交易后金额'")
	private double tradeAfter;
	@Column(columnDefinition = "varchar(20) comment '终端编码'")
	private String terminalNo;
	@Column(columnDefinition = "int comment '卡交易序号'")
	private int tradeSn;
	@Column(columnDefinition = "varchar(20) comment 'sam卡号'")
	private String samNo;
	@Column(columnDefinition = "datetime comment '数据处理日期'")
	private Date dealDate;

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

	public double getTradeMoney() {
		return tradeMoney;
	}

	public void setTradeMoney(double tradeMoney) {
		this.tradeMoney = tradeMoney;
	}

	public double getTradeBefore() {
		return tradeBefore;
	}

	public void setTradeBefore(double tradeBefore) {
		this.tradeBefore = tradeBefore;
	}

	public double getTradeAfter() {
		return tradeAfter;
	}

	public void setTradeAfter(double tradeAfter) {
		this.tradeAfter = tradeAfter;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
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

}
