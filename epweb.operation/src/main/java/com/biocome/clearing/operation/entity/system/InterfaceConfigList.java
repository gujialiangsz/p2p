package com.biocome.clearing.operation.entity.system;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.biocome.base.model.AutoIncrementEntity;

/**
 * 接口参数表
 * 
 * @author 谷家良
 * @date 2015年7月4日 下午3:14:04
 * @Description: TODO
 */
@Entity
@Table(name = "biocome_interfaceparameter")
@DynamicUpdate
public class InterfaceConfigList extends AutoIncrementEntity {

	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinColumn(name = "faceid")
	private InterfaceConfigMain mainConfig;
	private int superior;
	@Column
	private String name;
	@Column
	private int type;
	@Column
	private int species;
	@Column
	private String nametext;
	@Column
	private String text = "0";
	@Column
	private String value;
	@Column
	private boolean isnull;
	@Column
	private Date createtime;
	@Column
	private Date updatetime;
	@Column(name = "\"order\"")
	private int order;
	@Column
	private String reg;

	public InterfaceConfigMain getMainConfig() {
		return mainConfig;
	}

	public void setMainConfig(InterfaceConfigMain mainConfig) {
		this.mainConfig = mainConfig;
	}

	public int getSuperior() {
		return superior;
	}

	public void setSuperior(int superior) {
		this.superior = superior;
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

	public String getNametext() {
		return nametext;
	}

	public void setNametext(String nametext) {
		this.nametext = nametext;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isIsnull() {
		return isnull;
	}

	public void setIsnull(boolean isnull) {
		this.isnull = isnull;
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

	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getSpecies() {
		return species;
	}

	public void setSpecies(int species) {
		this.species = species;
	}

}
