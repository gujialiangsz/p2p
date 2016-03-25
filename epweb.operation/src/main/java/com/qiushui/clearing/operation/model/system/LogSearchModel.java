package com.qiushui.clearing.operation.model.system;

import java.util.Date;

import com.qiushui.base.annotations.Restriction;
import com.qiushui.base.db.SqlExpression;
import com.qiushui.base.model.SearchModel;

public class LogSearchModel extends SearchModel {
	@Restriction
	private String operatorId;
	@Restriction
	private String operatorName;
	@Restriction(type = SqlExpression.LIKE, propertyPreffix = "%", propertySuffix = "%")
	private String message;
	@Restriction(type = SqlExpression.GE, propertyName = "operateTime")
	private Date startTime;
	@Restriction(type = SqlExpression.LT, propertyName = "operateTime")
	private Date endTime;

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
