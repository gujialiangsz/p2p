package com.biocome.clearing.operation.model.business;

import java.util.Date;

import com.biocome.base.annotations.Restriction;
import com.biocome.base.constants.SqlExpression;
import com.biocome.base.model.SearchModel;

@Restriction
public class BusinessSearchModel extends SearchModel {

	private String name;
	private Date operateDate;
	private String phone;
	private String product;
	private Long uid;
	@Restriction(propertyName = "operateDate", type = SqlExpression.GE)
	private Date beginDate;
	@Restriction(propertyName = "operateDate", type = SqlExpression.LE)
	private Date endDate;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
