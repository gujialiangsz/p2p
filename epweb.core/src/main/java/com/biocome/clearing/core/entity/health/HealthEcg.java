package com.biocome.clearing.core.entity.health;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.biocome.base.model.AutoIncrementEntity;
import com.biocome.clearing.core.entity.account.WccUser;

@Entity
@Table(name = "BIOCOME_HEALTH_ECG")
@BatchSize(size = 100)
@DynamicUpdate
public class HealthEcg extends AutoIncrementEntity {

	private Long deviceId;
	@OneToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "userId")
	@NotFound(action = NotFoundAction.IGNORE)
	private WccUser user;
	private int type;
	private String data;
	private Long beginTime;
	private Long endTime;
	private String remark;
	@Transient
	private Date beginDate;
	@Transient
	private Date endDate;
	@Transient
	private String bluetooth;

	public String getRemark() {
		if (remark == null)
			remark = "";
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getBeginDate() {
		beginDate = new Date(beginTime);
		return beginDate;
	}

	public Date getEndDate() {
		endDate = new Date(endTime);
		return endDate;
	}

	public String getBluetooth() {
		if (deviceId != null) {
			bluetooth = Long.toString(deviceId, 16).toUpperCase();
			StringBuilder sb = new StringBuilder(bluetooth);
			int len = sb.length();
			int is = 0;
			while ((len -= 2) > 0) {
				is += 2;
				sb.insert(is, ":");
				is += 1;
			}
			bluetooth = sb.toString();
		}
		return bluetooth;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public WccUser getUser() {
		return user;
	}

	public void setUser(WccUser user) {
		this.user = user;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
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
