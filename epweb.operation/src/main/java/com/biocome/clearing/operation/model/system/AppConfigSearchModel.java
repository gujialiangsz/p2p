package com.biocome.clearing.operation.model.system;

import com.biocome.base.annotations.Restriction;
import com.biocome.base.model.SearchModel;

@Restriction
public class AppConfigSearchModel extends SearchModel {

	private Long id;
	private Boolean disable = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean isDisable() {
		return disable;
	}

	public void setDisable(Boolean disable) {
		if (disable == null)
			this.disable = false;
		this.disable = disable;
	}
}
