package com.biocome.clearing.operation.entity.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import com.biocome.base.model.AutoIncrementEntity;
import com.biocome.clearing.core.enums.InterfaceStatus;

@Entity
@Table(name = "biocome_interface")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "myCacheConf")
@DynamicUpdate
public class InterfaceConfigMain extends AutoIncrementEntity {
	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "groupid")
	private InterfaceGroup group;
	@Column
	private String name;
	@Column
	private int type;
	@Column
	@Type(type = "IEnum")
	private InterfaceStatus status;
	@Column
	private String text;
	@Column
	private Date createtime;
	@Column
	private Date updatetime;
	@Column
	private int version;
	@Column
	private String faceurl;
	@Column
	private String url;
	@Column
	private String methods;
	@Column
	private String returntext;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "writers")
	private User writers;
	@Column
	@Type(type = "Array")
	private String[] appid;
	@Column
	private String ip;
	@Column
	private boolean https;
	@Column
	private int freq_d;
	@Column
	private int freq_m;
	@Column
	private int access_authority;
	@OneToMany(mappedBy = "mainConfig", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("updatetime")
	private List<InterfaceConfigList> configLists = new ArrayList<InterfaceConfigList>();

	public int getAccess_authority() {
		return access_authority;
	}

	public void setAccess_authority(int access_authority) {
		this.access_authority = access_authority;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public InterfaceStatus getStatus() {
		return status;
	}

	public void setStatus(InterfaceStatus status) {
		this.status = status;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getFaceurl() {
		return faceurl;
	}

	public void setFaceurl(String faceurl) {
		this.faceurl = faceurl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethods() {
		return methods;
	}

	public void setMethods(String methods) {
		this.methods = methods;
	}

	public String getReturntext() {
		return returntext;
	}

	public void setReturntext(String returntext) {
		this.returntext = returntext;
	}

	public User getWriters() {
		return writers;
	}

	public void setWriters(User writers) {
		this.writers = writers;
	}

	public String[] getAppid() {
		return appid;
	}

	public void setAppid(String[] appid) {
		this.appid = appid;
	}

	public boolean isHttps() {
		return https;
	}

	public void setHttps(boolean https) {
		this.https = https;
	}

	public int getFreq_d() {
		return freq_d;
	}

	public void setFreq_d(int freq_d) {
		this.freq_d = freq_d;
	}

	public int getFreq_m() {
		return freq_m;
	}

	public void setFreq_m(int freq_m) {
		this.freq_m = freq_m;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public List<InterfaceConfigList> getConfigLists() {
		return configLists;
	}

	public void setConfigLists(List<InterfaceConfigList> configLists) {
		this.configLists = configLists;
	}

	public InterfaceGroup getGroup() {
		return group;
	}

	public void setGroup(InterfaceGroup group) {
		this.group = group;
	}

}
