package com.biocome.clearing.core.entity.account;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.biocome.base.model.AutoIncrementEntity;

@Table(name = "BIOCOME_USER")
@Entity
@BatchSize(size = 100)
public class WccUser extends AutoIncrementEntity {

	private int siteid = 1;
	private String username;
	private String email;
	private String mobile;
	private String password;
	private long reg_time;
	private String reg_ip;
	private long last_login_time;
	private String last_login_ip;
	private int status;
	private int type;
	private Date update_time;

	@OneToOne(cascade = { CascadeType.REFRESH })
	@PrimaryKeyJoinColumn(referencedColumnName = "user_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private WccUserInfo userinfo;

	public WccUserInfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(WccUserInfo userinfo) {
		this.userinfo = userinfo;
	}

	public int getSiteid() {
		return siteid;
	}

	public void setSiteid(int siteid) {
		this.siteid = siteid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getReg_time() {
		return reg_time;
	}

	public void setReg_time(long reg_time) {
		this.reg_time = reg_time;
	}

	public String getReg_ip() {
		return reg_ip;
	}

	public void setReg_ip(String reg_ip) {
		this.reg_ip = reg_ip;
	}

	public long getLast_login_time() {
		return last_login_time;
	}

	public void setLast_login_time(long last_login_time) {
		this.last_login_time = last_login_time;
	}

	public String getLast_login_ip() {
		return last_login_ip;
	}

	public void setLast_login_ip(String last_login_ip) {
		this.last_login_ip = last_login_ip;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

}
