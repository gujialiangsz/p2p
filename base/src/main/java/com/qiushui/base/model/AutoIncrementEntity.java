package com.qiushui.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.search.annotations.DocumentId;

/**
 * 自增实体基类
 */
@MappedSuperclass
public abstract class AutoIncrementEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@DocumentId
	@Column(columnDefinition = "bigint comment 'ID,递增连续，插入快'")
	protected long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AutoIncrementEntity other = (AutoIncrementEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
