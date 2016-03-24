package com.biocome.clearing.operation.entity.system;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.biocome.base.security.entity.RoleEntity;

/**
 * 角色。
 */
@Entity
@Table(name = "WCC_SYSTEM_ROLE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "myCacheConf")
public class Role extends RoleEntity<User, Actor> {
}
