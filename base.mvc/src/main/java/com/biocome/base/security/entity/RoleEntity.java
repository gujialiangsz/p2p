package com.biocome.base.security.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import com.biocome.base.model.BitCode;

/**
 * 角色实体基类。
 * 
 * @param <U>
 *            用户类型
 * @param <A>
 *            职务类型
 */
@MappedSuperclass
public abstract class RoleEntity<U extends UserEntity<U, A, ?>, A extends ActorEntity<?, ?, ?>>
		extends ResourceEntity<U> {
	/** 名称 */
	@NotEmpty
	@Column(columnDefinition = "varchar(50) comment '名称'")
	private String name;
	/** 权限 */
	@NotNull
	@Type(type = "BitCode")
	@Column(columnDefinition = "varchar(1000) comment '权限，二进制按位表示'")
	private BitCode permissions;
	/** 关联职务 */
	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<A> actors = new ArrayList<A>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BitCode getPermissions() {
		return permissions;
	}

	public void setPermissions(BitCode permissions) {
		this.permissions = permissions;
	}

	public List<A> getActors() {
		return actors;
	}

	public void setActors(List<A> actors) {
		this.actors = actors;
	}
}
