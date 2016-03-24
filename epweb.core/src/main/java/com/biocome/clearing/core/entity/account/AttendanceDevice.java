package com.biocome.clearing.core.entity.account;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.biocome.base.model.AutoIncrementEntity;

@Entity
@Table(name = "WCC_ATTENDANCE_DEVICE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "myCacheConf")
public class AttendanceDevice extends AutoIncrementEntity {

	private String name;
	private String secretKey;
	private String sn;
	private String remark;
	private boolean enabled = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
