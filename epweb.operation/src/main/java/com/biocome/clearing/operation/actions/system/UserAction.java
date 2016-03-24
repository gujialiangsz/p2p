package com.biocome.clearing.operation.actions.system;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.biocome.base.exception.BusinessException;
import com.biocome.base.model.Page;
import com.biocome.base.mvc.util.DialogResultUtils;
import com.biocome.base.security.annotations.Auth;
import com.biocome.base.security.permission.AdminPermission;
import com.biocome.base.util.DateUtils;
import com.biocome.base.xstream.MessageSource;
import com.biocome.clearing.core.entity.system.Employee;
import com.biocome.clearing.core.model.system.EmployeeSearchModel;
import com.biocome.clearing.core.service.system.EmployeeService;
import com.biocome.clearing.core.service.system.SystemCodingService;
import com.biocome.clearing.operation.entity.system.Actor;
import com.biocome.clearing.operation.entity.system.User;
import com.biocome.clearing.operation.entity.system.UserSettings;
import com.biocome.clearing.operation.model.system.UserSearchModel;
import com.biocome.clearing.operation.service.system.SecurityService;

/**
 * 用户管理。
 */
@Controller
@RequestMapping("/system")
@Auth({ "YH_A", "YH_M", "YH_V" })
public class UserAction {
	@Resource
	private SecurityService securityService;
	@Resource
	private MessageSource messageSource;
	@Resource
	private SystemCodingService systemCodingService;
	@Resource
	private EmployeeService employeeService;

	@RequestMapping("user-relation")
	public void listEmployee(Model model, EmployeeSearchModel searchModel) {
		Page<Employee> employeePage = employeeService
				.searchEmployeeOnDuty(searchModel);
		model.addAttribute("employeePage", employeePage);
		model.addAttribute("searchModel", searchModel);
	}

	/**
	 * 绑定用户。
	 * 
	 * @param binder
	 *            数据绑定对象
	 */
	@InitBinder("user")
	public void initBinderUser(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("user.");
	}

	/**
	 * 绑定职务。
	 * 
	 * @param binder
	 *            数据绑定对象
	 */
	@InitBinder("actor")
	public void initBinderActor(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("actor.");
	}

	/**
	 * 查看用户列表。
	 * 
	 * @param model
	 *            数据模型
	 * @param searchModel
	 *            搜索条件
	 */
	@RequestMapping("user-list")
	@Auth({ "YH_A", "YH_M", "YH_V" })
	public void list(Model model, UserSearchModel searchModel) {
		Page<User> page = securityService.searchUser(searchModel);
		model.addAttribute("actorlist",
				systemCodingService.getSystemCoding("ACTOR"));
		model.addAttribute("userPage", page);
		model.addAttribute("searchModel", searchModel);
	}

	/**
	 * 新增用户。
	 * 
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("user-add")
	@Auth({ "YH_A" })
	public void add(Model model) {
		User user = new User();
		user.setEffectiveDate(new Date());
		user.setInvalidDate(DateUtils.rollYear(new Date(), 3));
		model.addAttribute("user", user);
		model.addAttribute("actor", new Actor());
		model.addAttribute("actorlist",
				systemCodingService.getSystemCoding("ACTOR"));
		model.addAttribute("rootOrgan", securityService.getCurrentUser()
				.getSettings().getDefaultActor().getOrgan());
		model.addAttribute("roles", securityService.getAllRole());
	}

	/**
	 * 保存用户。
	 * 
	 * @param user
	 *            用户
	 * @param actor
	 *            职务
	 * @return 返回提示信息。
	 */
	@RequestMapping("user-save")
	@Auth({ "YH_A" })
	public ModelAndView save(User user, Actor actor) {
		UserSettings settings = new UserSettings();
		settings.setDefaultActor(actor);
		user.setSettings(settings);
		securityService.createUser(user);
		return DialogResultUtils.closeAndReloadNavTab(
				messageSource.get("user.add.success"), "system_user-list");
	}

	/**
	 * 编辑用户。
	 * 
	 * @param userId
	 *            用户ID
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("user-edit")
	@Auth({ "YH_M" })
	public void edit(String userId, Model model) {
		User user = securityService.getUser(userId);
		model.addAttribute("user", user);
		model.addAttribute("actor", user.getSettings().getDefaultActor());
		model.addAttribute("actorlist",
				systemCodingService.getSystemCoding("ACTOR"));
		model.addAttribute("rootOrgan", securityService.getCurrentUser()
				.getSettings().getDefaultActor().getOrgan());
		model.addAttribute("roles", securityService.getAllRole());
	}

	/**
	 * 更新用户。
	 * 
	 * @param user
	 *            用户
	 * @return 返回提示信息。
	 */
	@RequestMapping("user-update")
	@Auth({ "YH_M" })
	public ModelAndView update(User sysuser) {
		securityService.updateUser(sysuser);
		return DialogResultUtils.closeAndReloadNavTab(
				messageSource.get("user.edit.success"), "system_user-list");
	}

	/**
	 * 禁用用户。
	 * 
	 * @param userId
	 *            用户ID
	 * @return 返回提示信息。
	 */
	@RequestMapping("user-disable")
	@Auth({ "YH_M" })
	public ModelAndView disable(String userId) {
		securityService.disableUser(userId);
		return DialogResultUtils.reloadNavTab(
				messageSource.get("user.disable.success"), "system_user-list");
	}

	/**
	 * 删除用户。
	 * 
	 * @param userId
	 * @return 返回提示信息
	 */
	@RequestMapping("user-delete")
	@Auth({ "YH_M" })
	public ModelAndView delete(String userId) {
		User user = securityService.getUser(userId);
		securityService.deleteUser(user);
		return DialogResultUtils.reloadNavTab(
				messageSource.get("user.delete.success"), "system_user-list");
	}

	/**
	 * 启用用户。
	 * 
	 * @param userId
	 *            用户ID
	 * @return 返回提示信息。
	 */
	@RequestMapping("user-enable")
	@Auth({ "YH_M" })
	public ModelAndView enable(String userId) {
		securityService.enableUser(userId);
		return DialogResultUtils.reloadNavTab(
				messageSource.get("user.enable.success"), "system_user-list");
	}

	/**
	 * 重置用户密码。
	 * 
	 * @param userId
	 *            用户ID
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("user-pwd-reset")
	@Auth({ "YH_A", "YH_M" })
	public void pwdReset(String userId, Model model) {
		model.addAttribute(securityService.getUser(userId));
		model.addAttribute("defaultPassword", AdminPermission.DEFAULT_PASSWORD);
	}

	/**
	 * 保存重置密码。
	 * 
	 * @param managePassword
	 *            管理员密码
	 * @param user
	 *            用户
	 * @return 返回提示信息。
	 */
	@RequestMapping("user-pwd-reset-save")
	@Auth({ "YH_A", "YH_M" })
	public ModelAndView pwdResetSave(String newpassword, String originalpassword) {
		User user = securityService.getCurrentUser();
		if (securityService.checkPassword(originalpassword, user.getPassword())) {
			securityService.updateUserPassword(user.getId(),
					securityService.encryptPassword(newpassword));
		} else
			throw new BusinessException("原密码验证失败");
		return DialogResultUtils.closeAndReloadNavTab(messageSource
				.get("user.pwd.reset.success"));
	}

}
