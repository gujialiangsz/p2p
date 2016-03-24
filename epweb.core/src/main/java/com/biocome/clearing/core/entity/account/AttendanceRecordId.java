package com.biocome.clearing.core.entity.account;

import java.io.Serializable;
import java.util.Date;

public class AttendanceRecordId implements Serializable {

	private Date recordDay;
	private String terminalNo;
	private String chipNo;

	public Date getRecordDay() {
		return recordDay;
	}

	public void setRecordDay(Date recordDay) {
		this.recordDay = recordDay;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public String getChipNo() {
		return chipNo;
	}

	public void setChipNo(String chipNo) {
		this.chipNo = chipNo;
	}

}
