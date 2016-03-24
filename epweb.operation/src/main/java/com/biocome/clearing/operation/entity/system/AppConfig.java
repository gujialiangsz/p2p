package com.biocome.clearing.operation.entity.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;

import com.biocome.base.model.AutoIncrementEntity;

@Entity
@Table(name = "biocome_systemapp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "myCacheConf")
@DynamicUpdate
public class AppConfig extends AutoIncrementEntity {

	private String name;
	private String secret;
	@Column(name = "\"desc\"")
	private String desc;
	private long expire;
	@Column(name = "\"disable\"")
	private boolean disable = false;
	private Date operateDate;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "operator")
	private User operator;

	@Transient
	private long day;

	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	public long getDay() {
		day = expire / 86400;
		return day;
	}

	public void setDay(long day) {
		this.day = day;
		expire = this.day * 86400;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public long getExpire() {
		return expire;
	}

	public void setExpire(long expire) {
		this.expire = expire;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

}
