package com.biocome.clearing.core.entity.account;

import java.io.Serializable;

public class WccTerminalListId implements Serializable {

	private String cityCode;
	private String terminalNo;

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

}
