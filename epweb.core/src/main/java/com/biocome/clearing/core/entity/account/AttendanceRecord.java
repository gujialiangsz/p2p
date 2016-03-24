package com.biocome.clearing.core.entity.account;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "WCC_ATTENDANCE_LIST")
@IdClass(AttendanceRecordId.class)
@BatchSize(size = 100)
public class AttendanceRecord {

	@Id
	private Date recordDay;
	@Id
	private String terminalNo;
	@Id
	private String chipNo;
	private Long starttime;
	private Long endtime;
	@OneToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "uid")
	private WccUser user;

	@Transient
	private Date startDate;
	@Transient
	private Date endDate;

	public Date getStartDate() {
		if (starttime != null && starttime != 0)
			startDate = new Date(starttime);
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		if (endtime != null & endtime != 0)
			endDate = new Date(endtime);
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public WccUser getUser() {
		return user;
	}

	public void setUser(WccUser user) {
		this.user = user;
	}

	public Long getStarttime() {
		return starttime;
	}

	public void setStarttime(Long starttime) {
		this.starttime = starttime;
	}

	public Long getEndtime() {
		return endtime;
	}

	public void setEndtime(Long endtime) {
		this.endtime = endtime;
	}

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
