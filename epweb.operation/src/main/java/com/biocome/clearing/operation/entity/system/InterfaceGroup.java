package com.biocome.clearing.operation.entity.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import com.biocome.base.model.AutoIncrementEntity;
import com.biocome.clearing.core.enums.InterfaceStatus;

@Entity
@Table(name = "biocome_interfacegroup")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "myCacheConf")
@DynamicUpdate
public class InterfaceGroup extends AutoIncrementEntity {

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

	@OneToMany(mappedBy = "group", cascade = { CascadeType.REFRESH,
			CascadeType.REMOVE }, fetch = FetchType.EAGER)
	private List<InterfaceConfigMain> mainList = new ArrayList<InterfaceConfigMain>();

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

	public List<InterfaceConfigMain> getMainList() {
		return mainList;
	}

	public void setMainList(List<InterfaceConfigMain> mainList) {
		this.mainList = mainList;
	}

}
