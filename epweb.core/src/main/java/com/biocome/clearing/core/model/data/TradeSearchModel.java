package com.biocome.clearing.core.model.data;

import java.util.Date;

import com.biocome.base.annotations.Restriction;
import com.biocome.base.constants.SqlExpression;
import com.biocome.base.model.SearchModel;

@Restriction
public class TradeSearchModel extends SearchModel {

	@Restriction(type = SqlExpression.GE, propertyName = "tradeDate")
	private Date startTime;
	@Restriction(type = SqlExpression.LT, propertyName = "tradeDate")
	private Date endTime;
	private String cityCode;
	private String tradeType;
	private String cardNo;
	private String userId;
	@Restriction(type = SqlExpression.GE, propertyName = "syncDate")
	private Date syncStartDate;
	@Restriction(type = SqlExpression.LT, propertyName = "syncDate")
	private Date syncEndDate;
	private String industryType;
	private Integer dealStatus;

	public Date getSyncStartDate() {
		return syncStartDate;
	}

	public void setSyncStartDate(Date syncStartDate) {
		this.syncStartDate = syncStartDate;
	}

	public Date getSyncEndDate() {
		return syncEndDate;
	}

	public void setSyncEndDate(Date syncEndDate) {
		this.syncEndDate = syncEndDate;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public Integer getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(Integer dealStatus) {
		this.dealStatus = dealStatus;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

}
