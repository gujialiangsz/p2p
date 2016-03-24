package com.biocome.clearing.core.entity.system;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

import com.biocome.base.model.UuidEntity;
import com.biocome.clearing.core.enums.EmployeeStatus;
import com.biocome.clearing.core.enums.Sex;

/**
 * 
 * 
 * @author 谷家良
 * @date 2015年5月7日 下午5:20:23
 * @Description: TODO
 */
@Entity
@Table(name = "WCC_SYSTEM_EMPLOYEE")
public class Employee extends UuidEntity {

	/** 员工号 */
	@NotBlank
	@Column(columnDefinition = "varchar(20) comment '员工号'")
	private String number;
	/** ID/IC卡号 */
	@Column(columnDefinition = "varchar(20) comment '员工卡号'")
	private String cardNumber;
	/** 姓名 */
	@NotBlank
	@Column(columnDefinition = "varchar(20) comment '姓名'")
	private String name;
	/** 性别 （m-男 l-女） */
	@Type(type = "IEnum")
	@Column(columnDefinition = "char(2) comment '性别'")
	private Sex sex = Sex.MALE;
	/** 联系电话 */
	@Column(columnDefinition = "varchar(20) comment '联系电话'")
	private String contactNumber;
	/** 身份证号 */
	@Column(columnDefinition = "varchar(20) comment '身份id'")
	private String idCard;
	/** 籍贯 */
	@Column(columnDefinition = "varchar(50) comment '籍贯'")
	private String hometown;
	/** 办公电话 */
	@Column(columnDefinition = "varchar(20) comment '办公室电话'")
	private String officePhone;
	/** 邮箱 */
	@Column(columnDefinition = "varchar(20) comment '邮箱'")
	private String email;
	/** qq号 */
	@Column(columnDefinition = "varchar(20) comment 'QQ号'")
	private String qq;
	/** 基本工资 */
	@Column(columnDefinition = "varchar(20) comment '基本工资'")
	private String baseSalary;
	/** 聘用形式 */
	@Column(columnDefinition = "varchar(20) comment '聘用形式'")
	private String hireType;
	/** 入职时间 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "datetime comment '入职时间'")
	private Date hireDate;
	/** 学历(数据字典) */
	@Column(columnDefinition = "varchar(20) comment '学历，参照数据字典'")
	private String dEduBackground;
	/** 专业 */
	@Column(columnDefinition = "varchar(20) comment '专业'")
	private String major;
	/** 工作年限 */
	@Column(columnDefinition = "int comment '毕业年限'")
	private Integer workYear;
	/** 毕业院校 */
	@Column(columnDefinition = "varchar(36) comment '毕业学校'")
	private String graduateSchool;
	/** 在职状态 （Y-在职 N-离职） */
	@Type(type = "IEnum")
	@Column(columnDefinition = "char(2) comment '在职状态，Y在职，N离职'")
	private EmployeeStatus workStatus = EmployeeStatus.WORK;

	/** 民族 */
	@Column(columnDefinition = "varchar(20) comment '民族'")
	private String national;
	/** 常住地址 */
	@Column(columnDefinition = "varchar(50) comment '常住地址'")
	private String address;
	/** 备注 */
	@Column(name = "remark", length = 200)
	private String remark;
	/** 创建人 */
	@NotBlank
	@Column(name = "creatorId", length = 36)
	private String creatorId;
	/** 创建时间 */
	@Column(name = "createDate")
	private Date createDate;
	/** 修改人 */
	@Column(name = "modifierId", length = 36)
	private String modifierId;
	/** 修改时间 */
	@Column(name = "modifyDate")
	private Date modifyDate;
	/**
	 * 页面显示
	 */
	@Transient
	private String showNumber;
	/** 页面显示（员工登录账户） */
	@Transient
	private String username;

	public EmployeeStatus getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(EmployeeStatus workStatus) {
		this.workStatus = workStatus;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(String baseSalary) {
		this.baseSalary = baseSalary;
	}

	public String getHireType() {
		return hireType;
	}

	public void setHireType(String hireType) {
		this.hireType = hireType;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getdEduBackground() {
		return dEduBackground;
	}

	public void setdEduBackground(String dEduBackground) {
		this.dEduBackground = dEduBackground;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public Integer getWorkYear() {
		return workYear;
	}

	public void setWorkYear(Integer workYear) {
		this.workYear = workYear;
	}

	public String getGraduateSchool() {
		return graduateSchool;
	}

	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool;
	}

	public String getNational() {
		return national;
	}

	public void setNational(String national) {
		this.national = national;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getShowNumber() {
		return showNumber;
	}

	public void setShowNumber(String showNumber) {
		this.showNumber = showNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 
	 * 方法用途: 获取中文性别<br>
	 * 实现步骤: <br>
	 * 
	 * @return
	 */
	public String getTempSex() {
		return this.getSex().getText();
	}

	/**
	 * 
	 * 方法用途: 获取入职日期<br>
	 * 实现步骤: <br>
	 * 
	 * @return
	 */
	public String getTempDate() {
		Date hireDate = this.getHireDate();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(hireDate);
		return dateString;
	}

	/**
	 * 
	 * 方法用途: 获取在职状态中文表示<br>
	 * 实现步骤: <br>
	 * 
	 * @return
	 */
	public String getTempWorkStatus() {
		return this.getWorkStatus().getText();
	}

	/**
	 * 
	 * 方法用途: 获取学历中文表示<br>
	 * 实现步骤: <br>
	 * 
	 * @return
	 */
	public String getTempEdu() {
		if ("0".equals(this.getdEduBackground())) {
			return "小学";
		} else if ("1".equals(this.getdEduBackground())) {
			return "初中";
		} else if ("2".equals(this.getdEduBackground())) {
			return "高中";
		} else if ("3".equals(this.getdEduBackground())) {
			return "中专";
		} else if ("4".equals(this.getdEduBackground())) {
			return "高职";
		} else if ("5".equals(this.getdEduBackground())) {
			return "专科";
		} else if ("6".equals(this.getdEduBackground())) {
			return "本科";
		} else if ("7".equals(this.getdEduBackground())) {
			return "硕士";
		} else if ("8".equals(this.getdEduBackground())) {
			return "博士";
		} else {
			return " ";
		}

	}
}
