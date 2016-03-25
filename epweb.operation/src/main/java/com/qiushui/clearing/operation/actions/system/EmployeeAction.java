package com.qiushui.clearing.operation.actions.system;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.qiushui.base.db.dao.Dao;
import com.qiushui.base.exception.BusinessException;
import com.qiushui.base.model.Page;
import com.qiushui.base.mvc.util.DialogResultUtils;
import com.qiushui.base.security.annotations.Auth;
import com.qiushui.base.xstream.MessageSource;
import com.qiushui.clearing.core.entity.system.Employee;
import com.qiushui.clearing.core.model.system.EmployeeSearchModel;
import com.qiushui.clearing.core.service.system.EmployeeService;
import com.qiushui.clearing.core.service.system.SystemCodingService;
import com.qiushui.clearing.operation.entity.system.User;
import com.qiushui.clearing.operation.service.system.SecurityService;

/**
 * 
 * 
 * @author 谷家良
 * @date 2015年5月7日 下午7:29:14
 * @Description: TODO
 */
@Controller
@RequestMapping("/system")
@Auth({ "YG_A", "YG_M", "YG_V" })
public class EmployeeAction {
	@Resource
	private Dao<Employee> employeeDao;
	@Resource
	private EmployeeService employeeService;
	@Resource
	private SystemCodingService systemCodingService;
	@Resource
	private SecurityService securityService;
	@Resource
	private MessageSource messageSource;
	public static String number;

	/**
	 * 搜索员工列表。
	 * 
	 * @param model
	 *            数据模型
	 * @param searchModel
	 *            搜索条件
	 */
	@RequestMapping("employee-list")
	@Auth({ "YG_A" })
	public void list(Model model, EmployeeSearchModel searchModel) {
		Page<Employee> employeePage = employeeService
				.searchEmployee(searchModel);
		model.addAttribute("employeePage", employeePage);
		model.addAttribute("searchModel", searchModel);
	}

	/**
	 * 查看具体员工的详细信息
	 * 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * 
	 * @param employeeId
	 * @param model
	 */
	@RequestMapping("employee-view")
	@Auth({ "YG_A", "YG_M", "YG_V" })
	public void showDetail(String employeeId, Model model) {
		model.addAttribute(employeeService.getEmployee(employeeId));
		model.addAttribute("majors",
				systemCodingService.getSystemCoding("edu_level"));
	}

	/**
	 * 新增员工。
	 * 
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("employee-add")
	@Auth({ "YG_A" })
	public void add(Model model) {
		Employee employee = new Employee();
		model.addAttribute(employee);
		model.addAttribute("majors",
				systemCodingService.getSystemCoding("edu_level"));
	}

	/**
	 * 保存员工。
	 * 
	 * @param employee
	 *            员工信息
	 * @return 返回提示信息。
	 */
	@RequestMapping("employee-save")
	@Auth({ "YG_A" })
	public ModelAndView save(Employee employee) {
		// 设置创建者用户信息
		employee.setCreatorId(securityService.getCurrentUser().getId());
		// 设置保存时间
		employee.setCreateDate(new Date());
		employeeService.createEmployee(employee);
		number = null;
		return DialogResultUtils.closeDialogAndReloadNavTab(
				messageSource.get("employee.add.success"), null,
				"employee-list");
	}

	/**
	 * 编辑员工信息。
	 * 
	 * @param employeeId
	 *            员工ID
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("employee-edit")
	@Auth({ "YG_M" })
	public void edit(String employeeId, Model model) {
		Employee employee = employeeService.getEmployee(employeeId);
		model.addAttribute("employee", employee);
		model.addAttribute("majors",
				systemCodingService.getSystemCoding("edu_level"));
	}

	/**
	 * 更新员工信息。
	 * 
	 * @param employee
	 *            类型
	 * @return 返回提示信息。
	 */
	@RequestMapping("employee-update")
	@Auth({ "YG_M" })
	public ModelAndView update(Employee employee) {
		// 保存更改者用户信息
		employee.setModifierId(securityService.getCurrentUser().getId());
		// 保存修改时间
		employee.setModifyDate(new Date());
		employeeService.updateEmployee(employee);
		return DialogResultUtils.closeAndReloadNavTab(
				messageSource.get("employee.edit.success"), "employee-list");
	}

	/**
	 * 删除员工信息。
	 * 
	 * @param employeeId
	 *            员工ID
	 * @return 返回提示信息。
	 */
	@RequestMapping("employee-delete")
	@Auth({ "YG_M" })
	public ModelAndView delete(String employeeId) {
		Employee employee = employeeService.getEmployee(employeeId);
		if (employee != null) {
			// 判断影院用户状态
			User user = securityService.getUserByUserNo(employee.getNumber());
			if (user != null && !user.isDeleted()) {
				// 启用状态，不能离职
				throw new BusinessException("该员工正在使用，不能删除！");
			}
			employeeService.deleteEmployee(employee);
		}
		return DialogResultUtils.reloadNavTab(
				messageSource.get("employee.delete.success"), "employee-list");
	}

	/**
	 * 员工离职设置
	 * 
	 * 方法用途: <br>
	 * 实现步骤: <br>
	 * 
	 * @param employeelId
	 * @return
	 */
	@RequestMapping("employee-stop")
	@Auth({ "YG_M" })
	public ModelAndView stopEmployee(String employeeId) {
		employeeService.stopEmployee(employeeId);
		return DialogResultUtils.reloadNavTab(
				messageSource.get("employee.stop.success"), "employee-list");

	}

}
