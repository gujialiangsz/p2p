package com.biocome.clearing.operation.entity.system;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.biocome.base.security.entity.UserSettingsEntity;

/**
 * 用户设置。
 */
@Entity
@Table(name = "WCC_SYSTEM_USERSETTINGS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "myCacheConf")
public class UserSettings extends UserSettingsEntity<Actor> {

}
