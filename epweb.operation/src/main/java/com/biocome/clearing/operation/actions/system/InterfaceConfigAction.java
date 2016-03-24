package com.biocome.clearing.operation.actions.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.biocome.base.exception.BusinessException;
import com.biocome.base.model.Page;
import com.biocome.base.mvc.util.DialogResultUtils;
import com.biocome.base.security.annotations.Auth;
import com.biocome.base.util.StringUtils;
import com.biocome.base.xstream.MessageSource;
import com.biocome.clearing.core.service.system.SystemCodingService;
import com.biocome.clearing.operation.entity.system.InterfaceConfigList;
import com.biocome.clearing.operation.entity.system.InterfaceConfigMain;
import com.biocome.clearing.operation.entity.system.InterfaceGroup;
import com.biocome.clearing.operation.model.system.InterfaceConfig;
import com.biocome.clearing.operation.model.system.InterfaceConfigSearchModel;
import com.biocome.clearing.operation.model.system.ParamCheck;
import com.biocome.clearing.operation.service.system.AppConfigService;
import com.biocome.clearing.operation.service.system.InterfaceConfigService;

/**
 * 解析参数配置管理。
 */
@Controller
@RequestMapping("/system")
public class InterfaceConfigAction {
	@Resource
	private InterfaceConfigService interfaceConfigService;
	@Resource
	private MessageSource messageSource;
	@Resource
	private SystemCodingService systemCodingService;
	@Resource
	private AppConfigService appConfigService;

	/**
	 * 查看接口组列表。
	 * 
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("interface-listGroup")
	@Auth({ "JKPZ_A", "JKPZ_M", "JKPZ_V" })
	public void listGroup(Model model, Long id,
			InterfaceConfigSearchModel searchModel) {
		List<InterfaceGroup> roles = interfaceConfigService.searchGroup(
				searchModel).getContents();
		// 不为空的时候，表示有选中的组，要加载其信息
		if (id != null) {
			model.addAttribute("selgroup", interfaceConfigService.getGroup(id));
		}
		model.addAttribute("groups", roles);
		model.addAttribute("methods",
				systemCodingService.getSystemCoding("INTERFACE_METHOD"));
		model.addAttribute("auths",
				systemCodingService.getSystemCoding("INTERFACE_AUTH"));
		model.addAttribute("types",
				systemCodingService.getSystemCoding("INTERFACE_GROUPTYPE"));
	}

	/**
	 * 编辑接口组。
	 * 
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("interface-editGroup")
	@Auth({ "JKPZ_A" })
	public void editGroup(Model model, Long id) {
		if (id != null && id != 0)
			model.addAttribute("group", interfaceConfigService.getGroup(id));
		else
			throw new BusinessException("接口组ID不能为空");
		model.addAttribute("methods",
				systemCodingService.getSystemCoding("INTERFACE_METHOD"));
		model.addAttribute("auths",
				systemCodingService.getSystemCoding("INTERFACE_AUTH"));
		model.addAttribute("types",
				systemCodingService.getSystemCoding("INTERFACE_GROUPTYPE"));
		model.addAttribute("typess",
				systemCodingService.getSystemCoding("INTERFACE_TYPE"));
		model.addAttribute("statuss",
				systemCodingService.getSystemCoding("INTERFACE_STATUS"));
	}

	/**
	 * 添加接口组
	 * 
	 * @param model
	 * @description:
	 */
	@RequestMapping("interface-addGroup")
	@Auth({ "JKPZ_A" })
	public void addGroup(Model model) {
		model.addAttribute("group", new InterfaceGroup());
		model.addAttribute("methods",
				systemCodingService.getSystemCoding("INTERFACE_METHOD"));
		model.addAttribute("auths",
				systemCodingService.getSystemCoding("INTERFACE_AUTH"));
		model.addAttribute("types",
				systemCodingService.getSystemCoding("INTERFACE_GROUPTYPE"));
		model.addAttribute("typess",
				systemCodingService.getSystemCoding("INTERFACE_TYPE"));
		model.addAttribute("statuss",
				systemCodingService.getSystemCoding("INTERFACE_STATUS"));
	}

	/**
	 * 保存接口组。
	 * 
	 * @param group
	 *            组
	 * @return 返回保存角色成功提示。
	 */
	@RequestMapping("interface-saveGroup")
	@Auth({ "JKPZ_A" })
	public ModelAndView saveGroup(Model model, InterfaceGroup group) {
		try {
			Date date = new Date();
			group.setCreatetime(date);
			group.setUpdatetime(date);
			interfaceConfigService.addGroup(group);
			model.addAttribute("groupId", group.getId());
		} catch (BusinessException e) {
			messageSource.thrown(e.getMessage());
		} catch (Exception e) {
			messageSource.thrown("保存接口组失败");
		}
		return DialogResultUtils.closeAndReloadNavTab(
				messageSource.get("保存接口组成功"), "system-interface-listGroup");
	}

	/**
	 * 更新接口组。
	 * 
	 * @param group
	 *            组
	 * @return 返回保存角色成功提示。
	 */
	@RequestMapping("interface-updateGroup")
	@Auth({ "JKPZ_M" })
	public ModelAndView updateGroup(Model model, InterfaceGroup group) {
		try {
			Date date = new Date();
			group.setUpdatetime(date);
			interfaceConfigService.updateGroup(group);
			model.addAttribute("groupId", group.getId());
		} catch (BusinessException e) {
			messageSource.thrown(e.getMessage());
		} catch (Exception e) {
			messageSource.thrown("更新接口组失败");
		}
		return DialogResultUtils.reloadNavTab(messageSource.get("更新接口组成功"),
				"system-interface-listGroup");
	}

	@RequestMapping("interface-delGroup")
	@Auth({ "JKPZ_M" })
	public ModelAndView removeGroup(long id) {
		try {
			List<InterfaceConfigMain> mains = interfaceConfigService
					.removeGroup(id);
			interfaceConfigService.notify(mains, "remove");
		} catch (Exception e) {
			messageSource.thrown("删除接口组配置失败");
		}
		return DialogResultUtils.reloadNavTab(messageSource.get("接口组删除成功"),
				"system-interface-listGroup");
	}

	/**
	 * 查看参数配置列表。
	 * 
	 * @param model
	 *            数据模型
	 * @param searchModel
	 *            搜索条件
	 */
	@RequestMapping("interface-listConfigLists")
	@Auth({ "JKPZ_A", "JKPZ_M", "JKPZ_V" })
	public void listConfigList(Model model,
			InterfaceConfigSearchModel searchModel) {
		searchModel.setOrderBy("species,order");
		Page<InterfaceConfigList> page = interfaceConfigService
				.searchConfigListPage(searchModel);
		model.addAttribute("types",
				systemCodingService.getSystemCoding("INTERFACE_CLASS"));
		model.addAttribute("species",
				systemCodingService.getSystemCoding("INTERFACE_SPECIES"));
		model.addAttribute("listPage", page);
		model.addAttribute("searchModel", searchModel);
	}

	/**
	 * 
	 * @param userId
	 *            关联用户ID
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("interface-addConfigList")
	@Auth({ "JKPZ_A", "JKPZ_M", "JKPZ_V" })
	public void addConfigList(long mainId, Model model) {
		model.addAttribute("types",
				systemCodingService.getSystemCoding("INTERFACE_CLASS"));
		model.addAttribute("species",
				systemCodingService.getSystemCoding("INTERFACE_SPECIES"));
		InterfaceConfigList list = new InterfaceConfigList();
		list.setMainConfig(interfaceConfigService.getConfigMain(mainId));
		model.addAttribute("main", list);
	}

	/**
	 * 
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("interface-editConfigList")
	@Auth({ "JKPZ_A", "JKPZ_M", "JKPZ_V" })
	public void editConfigList(Model model, long id) {
		model.addAttribute("species",
				systemCodingService.getSystemCoding("INTERFACE_SPECIES"));
		model.addAttribute("types",
				systemCodingService.getSystemCoding("INTERFACE_CLASS"));
		model.addAttribute("main", interfaceConfigService.getConfigList(id));
	}

	@RequestMapping("interface-updateConfigList")
	@Auth({ "JKPZ_M" })
	public ModelAndView updateConfigList(InterfaceConfigList list) {
		try {
			String main = interfaceConfigService.updateConfigList(list);
			interfaceConfigService.notify(main, "update");
		} catch (BusinessException be) {
			messageSource.thrown(be.getMessage());
		} catch (Exception e) {
			messageSource.thrown("更新解析主配置失败");
		}
		return DialogResultUtils.closeDialog(messageSource.get("配置更新成功"),
				"system-interface-listConfigLists");
	}

	@RequestMapping("interface-saveConfigList")
	@Auth({ "JKPZ_A" })
	public ModelAndView saveConfigList(InterfaceConfigList list) {
		try {
			String main = interfaceConfigService.addConfigList(list);
			interfaceConfigService.notify(main, "update");
		} catch (BusinessException be) {
			messageSource.thrown(be.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			messageSource.thrown("保存解析配置明细失败");
		}
		return DialogResultUtils.closeDialog(messageSource.get("配置保存成功"),
				"system-interface-listConfigLists");
	}

	@RequestMapping("interface-delConfigList")
	@Auth({ "JKPZ_M" })
	public ModelAndView removeConfigList(long id) {
		try {
			String main = interfaceConfigService.removeConfigList(id);
			interfaceConfigService.notify(main, "update");
		} catch (BusinessException be) {
			messageSource.thrown(be.getMessage());
		} catch (Exception e) {
			messageSource.thrown("删除解析主配置失败");
		}
		return DialogResultUtils.reloadDialog(messageSource.get("配置删除成功"),
				"system-interface-listConfigLists");
	}

	/**
	 * 
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("interface-addMain")
	@Auth({ "JKPZ_A", "JKPZ_M", "JKPZ_V" })
	public void addMain(Model model, InterfaceGroup group) {
		model.addAttribute("methods",
				systemCodingService.getSystemCoding("INTERFACE_METHOD"));
		model.addAttribute("auths",
				systemCodingService.getSystemCoding("INTERFACE_AUTH"));
		model.addAttribute("types",
				systemCodingService.getSystemCoding("INTERFACE_TYPE"));
		model.addAttribute("statuss",
				systemCodingService.getSystemCoding("INTERFACE_STATUS"));
		model.addAttribute("apps", appConfigService.getAllEnableAppConfig());
		InterfaceConfigMain main = new InterfaceConfigMain();
		main.setGroup(interfaceConfigService.getGroup(group.getId()));
		model.addAttribute("main", main);
	}

	/**
	 * 
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("interface-editMain")
	@Auth({ "JKPZ_A", "JKPZ_M", "JKPZ_V" })
	public void editMain(Model model, long id) {
		model.addAttribute("methods",
				systemCodingService.getSystemCoding("INTERFACE_METHOD"));
		model.addAttribute("auths",
				systemCodingService.getSystemCoding("INTERFACE_AUTH"));
		model.addAttribute("types",
				systemCodingService.getSystemCoding("INTERFACE_TYPE"));
		model.addAttribute("statuss",
				systemCodingService.getSystemCoding("INTERFACE_STATUS"));
		model.addAttribute("apps", appConfigService.getAllEnableAppConfig());
		model.addAttribute("main", interfaceConfigService.getConfigMain(id));
	}

	@RequestMapping("interface-saveMain")
	@Auth({ "JKPZ_A" })
	public ModelAndView saveMain(InterfaceConfigMain main) {
		try {
			interfaceConfigService.addConfigMain(main);
			interfaceConfigService.notify(main, "add");
		} catch (BusinessException be) {
			messageSource.thrown(be.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			messageSource.thrown("保存解析主配置失败");
		}
		return DialogResultUtils.closeAndReloadNavTab(
				messageSource.get("配置保存成功"), "system-interface-listGroup");
	}

	@RequestMapping("interface-updateMain")
	@Auth({ "JKPZ_M" })
	public ModelAndView updateMain(InterfaceConfigMain main) {
		try {
			interfaceConfigService.updateConfigMain(main);
			interfaceConfigService.notify(main.getText(), "update");
		} catch (BusinessException be) {
			messageSource.thrown(be.getMessage());
		} catch (Exception e) {
			messageSource.thrown("更新解析主配置失败");
		}
		return DialogResultUtils.closeAndReloadNavTab(
				messageSource.get("配置保存成功"), "system-interface-listGroup");
	}

	@RequestMapping("interface-delMain")
	@Auth({ "JKPZ_M" })
	public ModelAndView removeMain(long id) {
		try {
			String main = interfaceConfigService.removeConfigMain(id);
			interfaceConfigService.notify(main, "remove");
		} catch (Exception e) {
			messageSource.thrown("删除接口配置失败");
		}
		return DialogResultUtils.reloadNavTab(messageSource.get("接口配置删除成功"),
				"system-interface-listGroup");
	}

	/******************** 华丽分割线 ****************************/

	/**
	 * 获取接口配置信息，不需要权限
	 * 
	 * @param name
	 *            接口名
	 * @return
	 * @description:
	 */
	@RequestMapping("interface-getConfig")
	@ResponseBody
	public Object getConfig(String name) {
		Map<String, InterfaceConfig> configs = new HashMap<String, InterfaceConfig>();
		List<InterfaceConfigMain> mains;
		if (!StringUtils.hasText(name)) {
			mains = interfaceConfigService.getAllMainConfig();
		} else {
			mains = interfaceConfigService.getMainConfig(name);
		}
		for (InterfaceConfigMain m : mains) {
			InterfaceConfig c = new InterfaceConfig();
			List<ParamCheck> pcs = new ArrayList<ParamCheck>();
			c.setHttps(m.isHttps());
			c.setFreq_d(m.getFreq_d());
			c.setFreq_m(m.getFreq_m());
			c.setAppOrUser(Integer.valueOf(m.getAccess_authority())
					.shortValue());
			c.setAppID(m.getAppid());
			c.setParam(pcs);
			List<InterfaceConfigList> iclist = m.getConfigLists();
			for (InterfaceConfigList l : iclist) {
				ParamCheck p = new ParamCheck();
				p.setMust(!l.isIsnull());
				p.setPattern(l.getReg());
				p.setPname(l.getName());
				pcs.add(p);
			}
			configs.put(m.getText(), c);
		}
		return configs;
	}
}
