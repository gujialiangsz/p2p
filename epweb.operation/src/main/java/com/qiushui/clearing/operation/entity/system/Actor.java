package com.qiushui.clearing.operation.entity.system;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.qiushui.base.security.entity.ActorEntity;

/**
 * 职务。
 */
@Entity
@Table(name = "WCC_SYSTEM_ACTOR")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "myCacheConf")
public class Actor extends ActorEntity<Organ, User, Role> {

}
