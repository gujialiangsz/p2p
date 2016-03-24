package com.biocome.clearing.operation.entity.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;

import com.biocome.base.security.annotations.LogField;
import com.biocome.base.security.entity.UserEntity;

/**
 * 用户。
 */
@Entity
@Table(name = "WCC_SYSTEM_USER")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "myCacheConf")
@DynamicUpdate(true)
public class User extends UserEntity<User, Actor, UserSettings> {

	@LogField(text = "联系电话")
	@Column(length = 20)
	private String phone;
	@NotEmpty
	@Column(length = 20)
	private String userNo;
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date invalidDate;
	@NotNull
	@LogField(text = "是否删除")
	@Column(columnDefinition = "tinyint(1) default 0")
	private boolean isDeleted = Boolean.FALSE;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
