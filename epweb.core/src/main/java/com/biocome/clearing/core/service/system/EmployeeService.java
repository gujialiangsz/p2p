package com.biocome.clearing.core.service.system;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biocome.base.db.dao.Dao;
import com.biocome.base.exception.BusinessException;
import com.biocome.base.model.Page;
import com.biocome.base.util.BeanUtils;
import com.biocome.base.xstream.MessageSource;
import com.biocome.clearing.core.entity.system.Employee;
import com.biocome.clearing.core.enums.EmployeeStatus;
import com.biocome.clearing.core.model.system.EmployeeSearchModel;

/**
 * 
 * 
 * @author 谷家良
 * @date 2015年5月7日 下午5:24:29
 * @Description: TODO
 */
@Service
public class EmployeeService {
	@Resource
	private Dao<Employee> employeeDao;
	@Resource
	private MessageSource messageSource;

	/**
	 * 分页（查询）搜索员工。
	 * 
	 * @param searchModel
	 *            搜索条件
	 * @return 返回符合条件的分页对象。
	 */
	@Transactional(readOnly = true)
	public Page<Employee> searchEmployee(EmployeeSearchModel searchModel) {
		searchModel.setOrderBy("createDate");
		searchModel.setSort("desc");
		return employeeDao.findPage(searchModel);
	}

	/**
	 * 分页（查询）搜索员工。
	 * 
	 * @param searchModel
	 *            搜索条件
	 * @return 返回符合条件的分页对象。
	 */
	@Transactional(readOnly = true)
	public Page<Employee> searchEmployeeOnDuty(EmployeeSearchModel searchModel) {
		searchModel.setOrderBy("workStatus");
		searchModel.setSort("desc");
		searchModel.setWorkStatus(EmployeeStatus.WORK);
		return employeeDao.findPage(searchModel);
	}

	/**
	 * 新增员工信息
	 * 
	 * @param BaseDictType
	 *            类型信息
	 */
	@Transactional
	public void createEmployee(Employee employee) {
		if (!employeeDao.isUnique(employee, "number")) {
			messageSource.thrown("员工号已存在");
		}
		employeeDao.save(employee);
	}

	/**
	 * 获取某个指定的员工信息。
	 * 
	 * @param employeeId
	 *            类型ID
	 * @return 返回某个员工信息。
	 */
	@Transactional(readOnly = true)
	public Employee getEmployee(String employeeId) {
		return employeeDao.get(employeeId);
	}

	/**
	 * 获取某个指定的员工信息。
	 * 
	 * @param number
	 * 
	 * @return 返回某个员工信息。
	 */
	@Transactional(readOnly = true)
	public Employee getEmployeeByNum(String number) {
		return employeeDao.findUnique("number", number);
	}

	/**
	 * 更新某个指定的员工信息。
	 * 
	 * @param employee
	 *            具体员工信息
	 */
	@Transactional
	public void updateEmployee(Employee employee) {
		if (!employeeDao.isUnique(employee, "number")) {
			messageSource.thrown("employee.number.exist");
		}
		Employee origEmployee = employeeDao.get(employee.getId());
		BeanUtils.copyFields(employee, origEmployee);
	}

	/**
	 * 删除员工信息。
	 * 
	 * @param employeeId
	 *            员工ID
	 */
	@Transactional
	public void deleteEmployee(Employee employee) {
		employeeDao.remove(employee);
	}

	/**
	 * 
	 * 方法用途: 员工离职<br>
	 * 实现步骤: <br>
	 * 
	 * @param employeeId
	 */
	@Transactional
	public void stopEmployee(String employeeId) {
		Employee employee = employeeDao.get(employeeId);
		if (employeeDao.countHqlResult(
				"from User u where u.isDeleted=false and u.userNo=?",
				employee.getNumber()) != 0)
			throw new BusinessException("员工离职前必须先删除账号");
		employee.setWorkStatus(EmployeeStatus.OFFWORK);
		employeeDao.update(employee);
	}

	/**
	 * 
	 * 方法用途:获取所有员工 <br>
	 * 实现步骤: <br>
	 * 
	 * @param searchModel
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Employee> searchEmployeel() {
		return employeeDao.getAll("number", true);
	}
}
