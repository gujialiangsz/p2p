package com.biocome.clearing.core.model.data;

import java.util.Date;

import com.biocome.base.annotations.Restriction;
import com.biocome.base.constants.SqlExpression;
import com.biocome.base.model.SearchModel;

public class HealthSearchModel extends SearchModel {
	@Restriction(type = SqlExpression.GE)
	private Long beginTime;
	@Restriction(type = SqlExpression.LT)
	private Long endTime;
	@Restriction
	private int type = 21;
	@Restriction
	private Long deviceId;
	@Restriction(propertyName = "user.id")
	private Long userId;
	private Date beginDate;
	private Date endDate;
	private String bluetooth;
	private String nickname;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getBluetooth() {
		return bluetooth;
	}

	public void setBluetooth(String bluetooth) {
		this.bluetooth = bluetooth;
	}

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

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

}
