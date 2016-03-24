package com.biocome.clearing.operation.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.biocome.base.model.AutoIncrementEntity;

@Entity
@Table(name = "WCC_APP_UPDATE")
public class AppVersion extends AutoIncrementEntity {

	private String appname;
	private String versioncode;
	private String type;
	private String versionname;
	private String versioninfo;
	private String grayscaleupgrade;
	private String fromversion;
	private String url;
	private String md5;
	@Column(name = "\"force\"")
	private boolean force;

	public String getGrayscaleupgrade() {
		return grayscaleupgrade;
	}

	public void setGrayscaleupgrade(String grayscaleupgrade) {
		this.grayscaleupgrade = grayscaleupgrade;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getVersioncode() {
		return versioncode;
	}

	public void setVersioncode(String versioncode) {
		this.versioncode = versioncode;
	}

	public String getVersionname() {
		return versionname;
	}

	public void setVersionname(String versionname) {
		this.versionname = versionname;
	}

	public String getVersioninfo() {
		return versioninfo;
	}

	public void setVersioninfo(String vsersioninfo) {
		this.versioninfo = vsersioninfo;
	}

	public String getFromversion() {
		return fromversion;
	}

	public void setFromversion(String fromversion) {
		this.fromversion = fromversion;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public boolean getForce() {
		return force;
	}

	public void setForce(boolean force) {
		this.force = force;
	}

}
