package com.biocome.clearing.operation.entity.system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.biocome.base.security.entity.OrganEntity;

/**
 * 机构。
 */
@Entity
@Table(name = "WCC_SYSTEM_ORGAN")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "myCacheConf")
public class Organ extends OrganEntity<Organ, User, Actor> {
	private boolean isDeleted = Boolean.FALSE;

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Transient
	private List<Organ> validChilds;

	public List<Organ> getValidChilds() {
		validChilds = this.getChilds();
		if (validChilds != null && validChilds.size() > 0) {
			Iterator<Organ> it = validChilds.iterator();
			while (it.hasNext()) {
				Organ o = it.next();
				if (o.isDeleted)
					it.remove();
			}
		}
		return validChilds;
	}

	/**
	 * 获取机构完整名称(带上级机构名称)。
	 * 
	 * @return 返回机构完整名称。
	 */
	public String getFullName() {
		if (getParent() != null) {
			return getParent().getName() + "-" + getName();
		} else {
			return getName();
		}
	}

	/**
	 * 获取用于下拉列表显示的机构名称。
	 * 
	 * @return 返回机构名称。
	 */
	public String getSelectText() {
		String text = getName();
		for (int i = 0; i < getOrganLevel(); i++) {
			text = " > " + text;
		}
		return text;
	}

	/**
	 * 获取机构所在层次，根机构层次为0。
	 * 
	 * @return 返回机构所在层次。
	 */
	public Integer getOrganLevel() {
		if (getParent() != null) {
			return getParent().getOrganLevel() + 1;
		} else {
			return 0;
		}
	}

	/**
	 * 获取本机构树下的所有机构，包括本机构。
	 * 
	 * @return 返回本机构树下的所有机构，包括本机构。
	 */
	public List<Organ> getOrganTree() {
		List<Organ> organTree = new ArrayList<Organ>();
		if (!this.isDeleted) {
			organTree.add(this);
			for (Organ child : getChilds()) {
				organTree.addAll(child.getOrganTree());
			}
		}
		return organTree;
	}

	/**
	 * 获取下级子部门最大序号
	 * 
	 * @return
	 */
	public int getMaxOrdinal() {
		List<Organ> ch = getValidChilds();
		return ch == null || ch.size() == 0 ? 0 : ch.get(ch.size() - 1)
				.getOrdinal() + 1;

	}

}
