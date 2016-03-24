package com.biocome.clearing.core.model.data;

import java.util.Date;

import com.biocome.base.annotations.Restriction;
import com.biocome.base.constants.SqlExpression;
import com.biocome.base.model.SearchModel;

@Restriction
public class AttendanceSearchModel extends SearchModel {

	@Restriction(propertyName = "recordDay", type = SqlExpression.GE)
	private Date beginDate;
	@Restriction(propertyName = "recordDay", type = SqlExpression.LE)
	private Date endDate;
	private String terminalNo;
	private String chipNo;
	@Restriction(propertyName = "user.id")
	private Long uid;

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

}
