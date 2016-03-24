package com.biocome.clearing.core.entity.account;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

@Table(name = "BIOCOME_DEVICE")
@Entity
@BatchSize(size = 100)
public class WccDevice {
	@Id
	private Long id;
	private int type;
	private String bluetooth;
	private String version;
	@OneToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "uid")
	private WccUser user;
	private String secretKey;
	private String deviceSign;
	private String nrf;
	private String st;
	private String remark;
	private String chipNo;

	public String getChipNo() {
		return chipNo;
	}

	public void setChipNo(String chipNo) {
		this.chipNo = chipNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeviceSign() {
		return deviceSign;
	}

	public void setDeviceSign(String deviceSign) {
		this.deviceSign = deviceSign;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getBluetooth() {
		return bluetooth;
	}

	public void setBluetooth(String bluetooth) {
		this.bluetooth = bluetooth;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public WccUser getUser() {
		return user;
	}

	public void setUser(WccUser user) {
		this.user = user;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getNrf() {
		return nrf;
	}

	public void setNrf(String nrf) {
		this.nrf = nrf;
	}

	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}

}
