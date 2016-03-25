package com.qiushui.base.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ForeignKey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.qiushui.base.model.UuidEntity;
import com.qiushui.base.security.annotations.LogBean;
import com.qiushui.base.security.annotations.LogField;
import com.qiushui.base.security.service.AbstractSecurityService;
import com.qiushui.base.util.DateUtils;
import com.qiushui.base.util.SpringUtils;
import com.qiushui.base.util.StringUtils;

/**
 * 资源实体基类。
 * 
 * @param <U>
 *            用户类型
 */
@MappedSuperclass
@JsonIgnoreProperties({ "creator", "createDate", "modifier", "modifyDate" })
public abstract class ResourceEntity<U extends UserEntity<U, ?, ?>> extends
		UuidEntity {
	@LogBean(@LogField(text = "创建人", property = "username"))
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creatorId", columnDefinition = "varchar(36) comment '创建人ID'")
	@NotNull
	@ForeignKey(name = "null")
	protected U creator;
	@LogField(text = "创建时间", format = DateUtils.SECOND)
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Column(columnDefinition = "datetime comment '创建时间'")
	protected Date createDate;
	@LogBean(@LogField(text = "修改人", property = "username"))
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modifierId", columnDefinition = "varchar(36) comment '修改人ID'")
	@ForeignKey(name = "null")
	protected U modifier;
	@LogField(text = "修改时间", format = DateUtils.SECOND)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "datetime comment '修改时间'")
	protected Date modifyDate;

	/**
	 * 根据上下文自动填充自身属性。
	 */
	public void autoFillIn() {
		if (StringUtils.isEmpty(getId())) {
			creator = getCurrentUser();
			createDate = new Date();
			modifier = getCurrentUser();
			modifyDate = new Date();
		} else {
			modifier = getCurrentUser();
			modifyDate = new Date();
		}
	}

	/**
	 * 从当前上下文中获取用户对象。
	 * 
	 * @return 返回当前上下文中的用户对象。
	 */
	private U getCurrentUser() {
		AbstractSecurityService<?, U, ?, ?, ?> securityService = SpringUtils
				.getBean("securityService");
		return securityService.getCurrentUser();
	}

	public U getCreator() {
		return creator;
	}

	public void setCreator(U creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public U getModifier() {
		return modifier;
	}

	public void setModifier(U modifier) {
		this.modifier = modifier;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
}
