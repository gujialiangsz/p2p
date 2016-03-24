package com.biocome.clearing.operation.actions.system;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.biocome.base.model.Page;
import com.biocome.base.mvc.util.DialogResultUtils;
import com.biocome.base.security.annotations.Auth;
import com.biocome.base.xstream.MessageSource;
import com.biocome.clearing.operation.entity.system.AppConfig;
import com.biocome.clearing.operation.entity.system.User;
import com.biocome.clearing.operation.model.system.AppConfigSearchModel;
import com.biocome.clearing.operation.service.system.AppConfigService;
import com.biocome.clearing.operation.service.system.SecurityService;

/**
 * 
 * 
 * @author 谷家良
 * @date 2015年5月8日 上午11:59:32
 * @Description: TODO
 */
@Controller
@RequestMapping("/system")
@Auth({ "YYPZ_A", "YYPZ_M", "YYPZ_V" })
public class AppConfigAction {
	@Resource
	private AppConfigService appConfigService;
	@Resource
	private SecurityService securityService;
	@Resource
	private MessageSource messageSource;

	/**
	 * 搜索类型列表。
	 * 
	 * @param model
	 *            数据模型
	 * @param searchModel
	 *            搜索条件
	 */
	@RequestMapping("appConfig-list")
	@Auth({ "YYPZ_A", "YYPZ_M", "YYPZ_V" })
	public void list(Model model, AppConfigSearchModel searchModel) {
		if (searchModel.isDisable() == null)
			searchModel.setDisable(false);
		model.addAttribute("searchModel", searchModel);
		Page<AppConfig> codings = appConfigService.searchAppConfig(searchModel);
		model.addAttribute("codingPage", codings);
	}

	/**
	 * 新增类型。
	 * 
	 * @param model
	 *            数据模型
	 */
	@Auth({ "YYPZ_A", "YYPZ_M", "YYPZ_V" })
	@RequestMapping("appConfig-add")
	public void add(Model model) {
		model.addAttribute("coding", new AppConfig());
	}

	/**
	 * 保存字典类型。
	 * 
	 * @param baseDictType
	 *            字典类型
	 * @return 返回提示信息。
	 */
	@Auth({ "YYPZ_A" })
	@RequestMapping("appConfig-save")
	public ModelAndView save(AppConfig coding) {
		// 当前登录用户
		User currentUser = securityService.getCurrentUser();
		Date now = new Date();
		coding.setOperateDate(now);
		coding.setOperator(currentUser);
		appConfigService.createAppConfig(coding);
		return DialogResultUtils.closeAndReloadNavTab(
				messageSource.get("add.success"), "system-appConfig-listGroup");
	}

	/**
	 * 编辑字典类型。
	 * 
	 * @param baseDictTypeId
	 *            字典类型ID
	 * @param model
	 *            数据模型
	 */
	@Auth({ "YYPZ_M" })
	@RequestMapping("appConfig-edit")
	public void edit(Long id, Model model) {
		model.addAttribute("coding", appConfigService.getAppConfigById(id));
	}

	/**
	 * 更新字典类型。
	 * 
	 * @param baseDictType
	 *            类型
	 * @return 返回提示信息。
	 */
	@Auth({ "YYPZ_M" })
	@RequestMapping("appConfig-update")
	public ModelAndView update(AppConfig coding) {
		// 保存更改者用户信息
		coding.setOperator(securityService.getCurrentUser());
		// 保存修改时间
		coding.setOperateDate(new Date());
		appConfigService.updateAppConfig(coding);
		return DialogResultUtils
				.closeAndReloadNavTab(messageSource.get("edit.success"),
						"system-appConfig-listGroup");
	}

	/**
	 * 启用字典类型。
	 * 
	 * @param baseDictTypeId
	 *            类型ID
	 * @return 返回提示信息。
	 */
	@Auth({ "YYPZ_M" })
	@RequestMapping("appConfig-enable")
	public ModelAndView enable(Long id) {
		appConfigService.enableAppConfig(id);
		return DialogResultUtils.reloadNavTab(messageSource.get("启用应用配置成功"),
				"system-appConfig-listGroup");
	}

	/**
	 * 禁用字典类型。
	 * 
	 * @param baseDictTypeId
	 *            类型ID
	 * @return 返回提示信息。
	 */
	@Auth({ "YYPZ_M" })
	@RequestMapping("appConfig-disable")
	public ModelAndView disable(Long id) {
		appConfigService.disableAppConfig(id);
		return DialogResultUtils.reloadNavTab(messageSource.get("禁用应用配置成功"),
				"system-appConfig-listGroup");
	}
}
