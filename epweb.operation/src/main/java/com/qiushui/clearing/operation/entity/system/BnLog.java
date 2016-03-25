package com.qiushui.clearing.operation.entity.system;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.qiushui.base.security.entity.BnLogEntity;

/**
 * 业务日志。
 */
@Entity
@Table(name = "WCC_SYSTEM_LOG")
public class BnLog extends BnLogEntity {

}
