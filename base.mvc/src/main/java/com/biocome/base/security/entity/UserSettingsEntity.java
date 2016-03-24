package com.biocome.base.security.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

/**
 * 用户设置基类。
 * 
 * @param <A>
 *            职务类型
 */
@MappedSuperclass
public abstract class UserSettingsEntity<A extends ActorEntity<?, ?, ?>>
		implements Serializable {
	@Id
	@Column(length = 36)
	private String id;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "defaultActorId", columnDefinition = "varchar(36) comment '默认职务ID'")
	private A defaultActor;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public A getDefaultActor() {
		return defaultActor;
	}

	public void setDefaultActor(A defaultActor) {
		this.defaultActor = defaultActor;
	}
}
