package com.qiushui.clearing.core.entity.system;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;

import com.qiushui.base.model.AutoIncrementEntity;

/**
 * 
 * 
 * @author 谷家良
 * @date 2015年5月6日 下午8:08:46
 * @Description: 字典
 */
@Entity
@Table(name = "WCC_SYSTEM_CODING")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "myCacheConf")
public class SystemCoding extends AutoIncrementEntity {
	/** 字典类别编码 */
	@NotBlank
	@Column(columnDefinition = "varchar(20) comment '字典类别编码'")
	private String type;
	/** 字典编码 */
	@NotBlank
	@Column(columnDefinition = "varchar(20) comment '字典编码或参数值'")
	private String code;
	/** 字典名称 */
	@Column(columnDefinition = "varchar(50) comment '字典名称或参数名'")
	private String codeName;
	/**
	 * 数据字典类型（0系统级（不许修改）、1用户自定义级（可以修改））
	 */
	@NotNull
	@Column(columnDefinition = "tinyint(1) default 0 comment '级别，0系统级，1用户级，系统级不可通过界面编辑'")
	private int systemLevel = 0;
	/** 删除禁用 */
	@Column(columnDefinition = "tinyint(1) default 0 comment '是否禁用，1标识禁用，0启用'")
	private boolean disable = Boolean.FALSE;
	/** 创建人id */
	@Column(columnDefinition = "varchar(36) comment '创建人id'")
	private String creatorId;
	/** 创建时间 */
	@Temporal(TemporalType.DATE)
	@Column(columnDefinition = "datetime comment '创建时间'")
	private Date createDate;
	/** 修改人id */
	@Column(columnDefinition = "varchar(36) comment '修改人id'")
	private String modifierId;
	/** 修改时间 */
	@Temporal(TemporalType.DATE)
	@Column(columnDefinition = "datetime comment '修改时间'")
	private Date modifyDate;

	/** 备注 */
	@Column(columnDefinition = "varchar(200) comment '备注'")
	private String remark;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getSystemLevel() {
		return systemLevel;
	}

	public void setSystemLevel(int systemLevel) {
		this.systemLevel = systemLevel;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getModifierId() {
		return modifierId;
	}

	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

}
