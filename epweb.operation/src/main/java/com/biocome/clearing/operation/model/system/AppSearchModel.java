package com.biocome.clearing.operation.model.system;

import com.biocome.base.annotations.Restriction;
import com.biocome.base.model.SearchModel;

@Restriction
public class AppSearchModel extends SearchModel {

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
