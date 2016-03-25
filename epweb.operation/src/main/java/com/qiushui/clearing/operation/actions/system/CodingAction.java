package com.qiushui.clearing.operation.actions.system;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.qiushui.base.model.Page;
import com.qiushui.base.mvc.util.DialogResultUtils;
import com.qiushui.base.security.annotations.Auth;
import com.qiushui.base.xstream.MessageSource;
import com.qiushui.clearing.core.entity.system.SystemCoding;
import com.qiushui.clearing.core.model.system.CodingSearchModel;
import com.qiushui.clearing.core.service.system.SystemCodingService;
import com.qiushui.clearing.operation.entity.system.User;
import com.qiushui.clearing.operation.service.system.SecurityService;

/**
 * 
 * 
 * @author 谷家良
 * @date 2015年5月8日 上午11:59:32
 * @Description: 字典参数配置，包括常量和系统配置
 */
@Controller
@RequestMapping("/system")
@Auth({ "SJCS_A", "SJCS_M", "SJCS_V" })
public class CodingAction {
	@Resource
	private SystemCodingService systemCodingService;
	@Resource
	private SecurityService securityService;
	@Resource
	private MessageSource messageSource;

	/**
	 * 搜索字典列表。
	 * 
	 * @param model
	 *            数据模型
	 * @param searchModel
	 *            搜索条件
	 */
	@RequestMapping("coding-list")
	@Auth({ "SJCS_A", "SJCS_M", "SJCS_V" })
	public void list(Model model, CodingSearchModel searchModel) {
		Page<SystemCoding> codings = systemCodingService
				.searchSystemCoding(searchModel);
		model.addAttribute("codingPage", codings);
		model.addAttribute("searchModel", searchModel);
	}

	/**
	 * 新增字典。
	 * 
	 * @param model
	 *            数据模型
	 */
	@Auth({ "SJCS_A", "SJCS_M", "SJCS_V" })
	@RequestMapping("coding-add")
	public void add(Model model) {
		model.addAttribute("coding", new SystemCoding());
		model.addAttribute("codingTypes", systemCodingService.getAllTypes());
	}

	/**
	 * 保存字典类型。
	 * 
	 * @param baseDictType
	 *            字典类型
	 * @return 返回提示信息。
	 */
	@Auth({ "SJCS_A" })
	@RequestMapping("coding-save")
	public ModelAndView save(SystemCoding coding) {
		// 当前登录用户
		User currentUser = securityService.getCurrentUser();
		Date now = new Date();
		coding.setCreatorId(currentUser.getId());
		coding.setModifierId(currentUser.getId());
		coding.setCreateDate(now);
		coding.setModifyDate(now);
		systemCodingService.createSystemCoding(coding);
		return DialogResultUtils.closeAndReloadNavTab(
				messageSource.get("add.success"), "coding-list");
	}

	/**
	 * 编辑字典类型。
	 * 
	 * @param baseDictTypeId
	 *            字典类型ID
	 * @param model
	 *            数据模型
	 */
	@Auth({ "SJCS_M" })
	@RequestMapping("coding-edit")
	public void edit(Long codingId, Model model) {
		model.addAttribute("coding",
				systemCodingService.getSystemCodingById(codingId));
		model.addAttribute("codingTypes", systemCodingService.getAllTypes());
	}

	/**
	 * 更新字典类型。
	 * 
	 * @param baseDictType
	 *            类型
	 * @return 返回提示信息。
	 */
	@Auth({ "SJCS_M" })
	@RequestMapping("coding-update")
	public ModelAndView update(SystemCoding coding) {
		// 保存更改者用户信息
		coding.setModifierId(securityService.getCurrentUser().getId());
		// 保存修改时间
		coding.setModifyDate(new Date());
		systemCodingService.updateSystemCoding(coding);
		return DialogResultUtils.closeAndReloadNavTab(
				messageSource.get("edit.success"), "coding-list");
	}

	/**
	 * 删除字典类型。
	 * 
	 * @param baseDictTypeId
	 *            类型ID
	 * @return 返回提示信息。
	 */
	@Auth({ "SJCS_M" })
	@RequestMapping("coding-delete")
	public ModelAndView delete(Long codingId) {
		systemCodingService.deleteSystemCoding(codingId);
		return DialogResultUtils.reloadNavTab(
				messageSource.get("delete.success"), "coding-list");
	}

	/**
	 * 启用字典类型。
	 * 
	 * @param baseDictTypeId
	 *            类型ID
	 * @return 返回提示信息。
	 */
	@Auth({ "SJCS_M" })
	@RequestMapping("coding-enable")
	public ModelAndView enable(Long codingId) {
		systemCodingService.enableSystemCoding(codingId);
		return DialogResultUtils.reloadNavTab(messageSource.get("启用字典成功"),
				"coding-list");
	}

	/**
	 * 禁用字典类型。
	 * 
	 * @param baseDictTypeId
	 *            类型ID
	 * @return 返回提示信息。
	 */
	@Auth({ "SJCS_M" })
	@RequestMapping("coding-disable")
	public ModelAndView disable(Long codingId) {
		systemCodingService.disableSystemCoding(codingId);
		return DialogResultUtils.reloadNavTab(messageSource.get("禁用字典成功"),
				"coding-list");
	}
}
