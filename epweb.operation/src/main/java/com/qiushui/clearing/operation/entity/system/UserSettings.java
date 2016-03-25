package com.qiushui.clearing.operation.entity.system;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.qiushui.base.security.entity.UserSettingsEntity;

/**
 * 用户设置。
 */
@Entity
@Table(name = "WCC_SYSTEM_USERSETTINGS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "myCacheConf")
public class UserSettings extends UserSettingsEntity<Actor> {

}
