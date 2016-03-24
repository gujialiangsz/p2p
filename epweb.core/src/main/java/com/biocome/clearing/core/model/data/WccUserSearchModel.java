package com.biocome.clearing.core.model.data;

import com.biocome.base.annotations.Restriction;
import com.biocome.base.model.SearchModel;

public class WccUserSearchModel extends SearchModel {

	@Restriction
	private Long id;
	@Restriction
	private String mobile;
	@Restriction(propertyName = "userinfo.nickname")
	private String nickname;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
