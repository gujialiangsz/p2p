package com.biocome.clearing.core.entity.account;

import java.io.Serializable;

public class CardInfoId implements Serializable {

	private String userId;
	private String cardNo;

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

}
