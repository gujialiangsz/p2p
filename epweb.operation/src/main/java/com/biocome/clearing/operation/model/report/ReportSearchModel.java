package com.biocome.clearing.operation.model.report;

import com.biocome.base.annotations.Restriction;
import com.biocome.base.model.SearchModel;

@Restriction
public class ReportSearchModel extends SearchModel {

	private String startTime;
	private String endTime;
	private String cityCode;
	private String cardType;
	private String type = "pdf";

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
