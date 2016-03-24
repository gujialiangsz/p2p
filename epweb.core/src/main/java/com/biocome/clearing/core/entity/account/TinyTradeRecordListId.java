package com.biocome.clearing.core.entity.account;

import java.io.Serializable;
import java.util.Date;

public class TinyTradeRecordListId implements Serializable {
	private Date tradeDate;
	private String cityCode;
	private String tradeType;
	private String cardNo;
	private int tradeSn;

	public TinyTradeRecordListId() {
		super();
	}

	public TinyTradeRecordListId(Date tradeDate, String cityCode,
			String tradeType, String cardNo, int tradeSn) {
		super();
		this.tradeDate = tradeDate;
		this.cityCode = cityCode;
		this.tradeType = tradeType;
		this.cardNo = cardNo;
		this.tradeSn = tradeSn;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public int getTradeSn() {
		return tradeSn;
	}

	public void setTradeSn(int tradeSn) {
		this.tradeSn = tradeSn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cardNo == null) ? 0 : cardNo.hashCode());
		result = prime * result
				+ ((cityCode == null) ? 0 : cityCode.hashCode());
		result = prime * result
				+ ((tradeDate == null) ? 0 : tradeDate.hashCode());
		result = prime * result + tradeSn;
		result = prime * result
				+ ((tradeType == null) ? 0 : tradeType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TinyTradeRecordListId other = (TinyTradeRecordListId) obj;
		if (cardNo == null) {
			if (other.cardNo != null)
				return false;
		} else if (!cardNo.equals(other.cardNo))
			return false;
		if (cityCode == null) {
			if (other.cityCode != null)
				return false;
		} else if (!cityCode.equals(other.cityCode))
			return false;
		if (tradeDate == null) {
			if (other.tradeDate != null)
				return false;
		} else if (!tradeDate.equals(other.tradeDate))
			return false;
		if (tradeSn != other.tradeSn)
			return false;
		if (tradeType == null) {
			if (other.tradeType != null)
				return false;
		} else if (!tradeType.equals(other.tradeType))
			return false;
		return true;
	}

}
