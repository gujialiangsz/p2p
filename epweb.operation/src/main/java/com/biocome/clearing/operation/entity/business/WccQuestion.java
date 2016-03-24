package com.biocome.clearing.operation.entity.business;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.biocome.base.model.AutoIncrementEntity;

@Entity
@Table(name = "WCC_QUESTION")
public class WccQuestion extends AutoIncrementEntity {

	private String name;
	private String phone;
	private String product;
	private String question;
	private Integer status = 1;
	private Date operateDate;
	private String answer;

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

}
