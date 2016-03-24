package com.biocome.clearing.core.model.system;

import com.biocome.base.annotations.Restriction;
import com.biocome.base.constants.SqlExpression;
import com.biocome.base.model.SearchModel;
import com.biocome.clearing.core.enums.EmployeeStatus;

@Restriction
public class EmployeeSearchModel extends SearchModel {
	@Restriction(type = SqlExpression.LIKE, propertyPreffix = "%", propertySuffix = "%")
	private String name;
	@Restriction(type = SqlExpression.LIKE, propertyPreffix = "%", propertySuffix = "%")
	private String number;
	private EmployeeStatus workStatus;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public EmployeeStatus getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(EmployeeStatus workStatus) {
		this.workStatus = workStatus;
	}

}
