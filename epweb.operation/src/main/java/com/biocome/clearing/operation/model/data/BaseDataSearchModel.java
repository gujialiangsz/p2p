package com.biocome.clearing.operation.model.data;

import com.biocome.base.annotations.Restriction;
import com.biocome.base.constants.SqlExpression;
import com.biocome.base.model.SearchModel;

public class BaseDataSearchModel extends SearchModel {

	@Restriction(propertyName = "user.id")
	private Long userId;
	private String nickname;
	@Restriction(type = SqlExpression.LIKE, propertySuffix = "%")
	private String bluetooth;
	@Restriction(type = SqlExpression.LIKE, propertySuffix = "%")
	private String name;
	@Restriction
	private String sn;
	@Restriction
	private Boolean enabled;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getBluetooth() {
		return bluetooth;
	}

	public void setBluetooth(String bluetooth) {
		this.bluetooth = bluetooth;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
