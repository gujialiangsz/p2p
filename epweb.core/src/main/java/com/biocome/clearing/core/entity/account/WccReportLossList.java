package com.biocome.clearing.core.entity.account;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.biocome.base.model.AutoIncrementEntity;

/**
 * 
 * 
 * @author 谷家良
 * @date 2015年5月14日 下午6:21:31
 * @Description: 挂失表
 */
@Table(name = "WCC_REPORT_LOSS_LIST")
@Entity
public class WccReportLossList extends AutoIncrementEntity {

	@Column(columnDefinition = "varchar(36) comment '用户id'")
	private String userId;
	@Column(columnDefinition = "varchar(36) comment '卡号'")
	private String cardNo;
	@Column(columnDefinition = "char(8) comment '城市代码'")
	private String cityCode;
	@Column(columnDefinition = "char(8) comment '卡类型'")
	private String cardType;
	@Column(columnDefinition = "datetime comment '操作日期'")
	private Date operateDate;
	@Column(length = 2, columnDefinition = "char(2) comment '状态'")
	private String dealStatus;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

}
