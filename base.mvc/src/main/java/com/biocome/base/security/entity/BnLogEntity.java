package com.biocome.base.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.biocome.base.model.AutoIncrementEntity;
import com.biocome.base.security.annotations.Log.LogType;

/**
 * 业务日志基类。
 */
@MappedSuperclass
public abstract class BnLogEntity extends AutoIncrementEntity {
	/** 创建人 */
	@Column(columnDefinition = "varchar(36) comment '操作人账号'")
	private String operatorId;
	@Column(columnDefinition = "varchar(50) comment '操作人姓名'")
	private String operatorName;
	/** 创建时间 */
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "datetime comment '操作时间'")
	private Date operateTime;
	/** IP */
	@Column(columnDefinition = "varchar(20) comment 'IP'")
	private String ip;
	/** 日志信息 */
	@Column(columnDefinition = "varchar(500) comment '日志信息'")
	private String message = "";
	/** 操作函数 */
	@Column(columnDefinition = "varchar(50) comment '操作对象'")
	private String method = "";
	@Type(type = "IEnum")
	@Column(columnDefinition = "char(1) comment'日志类型，0普通，1登录，2对账'")
	private LogType logType;
	/** 是否成功执行（没有异常） */
	@Column(columnDefinition = "tinyint(1) default 0 comment '是否成功执行'")
	private boolean success = false;
	@Column(columnDefinition = "varchar(500) comment '异常信息'")
	private String exceptionMsg;

	public String getMessage() {
		return message;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public void setMessage(String message) {
		this.message += message;
		if (this.message.length() > 500)
			message = message.substring(0, 500);
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		if (exceptionMsg != null) {
			if (exceptionMsg.length() > 100)
				this.exceptionMsg = exceptionMsg.substring(0, 100);
			this.exceptionMsg = exceptionMsg.replace("\r\n", "");
		}
	}

	public LogType getLogType() {
		return logType;
	}

	public void setLogType(LogType logType) {
		this.logType = logType;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
