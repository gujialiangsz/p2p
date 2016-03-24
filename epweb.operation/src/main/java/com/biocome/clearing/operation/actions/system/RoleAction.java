package com.biocome.clearing.operation.actions.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.biocome.base.exception.BusinessException;
import com.biocome.base.mvc.util.DialogResultUtils;
import com.biocome.base.security.annotations.Auth;
import com.biocome.base.security.constants.AdminIds;
import com.biocome.base.security.permission.PermissionConfig;
import com.biocome.base.xstream.MessageSource;
import com.biocome.clearing.operation.entity.system.Role;
import com.biocome.clearing.operation.entity.system.User;
import com.biocome.clearing.operation.service.system.SecurityService;

/**
 * 角色管理。
 */
@Controller
@RequestMapping("/system")
@Auth({ "JS_A", "JS_M", "JS_V" })
public class RoleAction {
	@Resource
	private SecurityService securityService;
	@Resource
	private PermissionConfig permissionConfig;
	@Resource
	private MessageSource messageSource;

	/**
	 * 查看角色列表。
	 * 
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("role-list")
	@Auth({ "JS_A", "JS_M", "JS_V" })
	public void list(Model model, HttpServletRequest request) {
		List<Role> roles = securityService.getAllRole();
		model.addAttribute("roles", roles);
		if (roles.size() > 0) {
			model.addAttribute("role", roles.get(0));
			model.addAttribute("permissionIds", permissionConfig
					.getPermissionIds(roles.get(0).getPermissions()));
			model.addAttribute("permissionGroups",
					permissionConfig.getPermissionGroups());
		}
		model.addAttribute("sessionid", request.getSession().getId());
	}

	/**
	 * 新增角色。
	 * 
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("role-add")
	@Auth({ "JS_A" })
	public void add(Model model) {
		model.addAttribute(new Role());
		model.addAttribute("permissionIds", new ArrayList<String>());
		model.addAttribute("permissionGroups",
				permissionConfig.getPermissionGroups());
	}

	/**
	 * 保存角色。
	 * 
	 * @param role
	 *            角色
	 * @param permissionIds
	 *            权限ID集合
	 * @return 返回保存角色成功提示。
	 */
	@RequestMapping("role-save")
	@Auth({ "JS_A" })
	public ModelAndView save(Role role, Integer[] permissionIds) {
		if (permissionIds == null || permissionIds.length == 0) {
			messageSource.thrown("角色权限不能为空");
		}
		try {
			role.setPermissions(permissionConfig.getPermissionCode(
					Arrays.asList(permissionIds), 300));
			// 当前登录用户
			User currentUser = securityService.getCurrentUser();
			Date now = new Date();
			role.setCreator(currentUser);
			role.setModifier(currentUser);
			role.setCreateDate(now);
			role.setModifyDate(now);
			securityService.createRole(role);
		} catch (BusinessException e) {
			messageSource.thrown(e.getMessage());
		} catch (Exception e) {
			messageSource.thrown("保存角色失败");
		}
		return DialogResultUtils.closeAndReloadNavTab(
				messageSource.get("role.add.success"), "system_role-list");
	}

	/**
	 * 编辑角色。
	 * 
	 * @param roleId
	 *            角色ID
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("role-edit")
	@Auth({ "JS_A" })
	public void edit(String roleId, Model model) {
		Role role = securityService.getRole(roleId);
		model.addAttribute(role);
		model.addAttribute("relationUsers",
				securityService.getUserNameListStrFromRole(role));
		model.addAttribute("permissionIds",
				permissionConfig.getPermissionIds(role.getPermissions()));
		model.addAttribute("permissionGroups",
				permissionConfig.getPermissionGroups());
	}

	/**
	 * 更新角色。
	 * 
	 * @param role
	 *            角色
	 * @param permissionIds
	 *            权限ID集合
	 * @return 返回提示信息。
	 */
	@RequestMapping("role-update")
	@Auth({ "JS_M" })
	public ModelAndView update(Role role, Integer[] permissionIds) {
		if (permissionIds == null || permissionIds.length == 0) {
			messageSource.thrown("角色权限不能为空");
		}
		try {
			role.setPermissions(permissionConfig.getPermissionCode(
					Arrays.asList(permissionIds), 300));// 要设置bitcode位数，默认300位
			securityService.updateRole(role);
		} catch (Exception e) {
			messageSource.thrown("保存角色失败");
		}
		return DialogResultUtils.reloadNavTab(
				messageSource.get("role.edit.success"), "system_role-list");
	}

	@RequestMapping("role-delete")
	@Auth({ "JS_M" })
	public ModelAndView delete(String roleId) {
		if (AdminIds.ROLE_ID != roleId) {
			securityService.deleteRole(roleId);
		}
		return DialogResultUtils.reloadNavTab(
				messageSource.get("role.delete.success"), "system_role-list");
	}

	/**
	 * 角色导出
	 * 
	 * @return
	 */
	@RequestMapping("role-exp")
	@Auth({ "JS_A", "JS_M", "JS_V" })
	public ModelAndView exp(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// List<Role> roles = securityService.getAllRole();
			// RoleXmls rs = new RoleXmls();
			// rs.entityToXml(permissionConfig, roles);
			// webFileService.downloadXmlByXstream(request.getSession()
			// .getServletContext().getRealPath("tmp"),
			// "role" + DateUtils.format(new Date(), "yyyyMMddHHmmss")
			// + ".xml", rs, response, true);
		} catch (Exception e) {
			messageSource.thrown("导出文件发生异常");
		}
		return null;
	}

}
