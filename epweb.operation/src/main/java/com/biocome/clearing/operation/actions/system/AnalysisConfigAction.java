package com.biocome.clearing.operation.actions.system;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.biocome.base.exception.BusinessException;
import com.biocome.base.model.Page;
import com.biocome.base.mvc.util.DialogResultUtils;
import com.biocome.base.security.annotations.Auth;
import com.biocome.base.xstream.MessageSource;
import com.biocome.clearing.core.entity.account.WccFileAnalysisConfigList;
import com.biocome.clearing.core.entity.account.WccFileAnalysisConfigListId;
import com.biocome.clearing.core.entity.account.WccFileAnalysisConfigMain;
import com.biocome.clearing.core.entity.account.WccFileAnalysisConfigMainId;
import com.biocome.clearing.core.service.system.SystemCodingService;
import com.biocome.clearing.operation.model.system.AnalysisConfigSearchModel;
import com.biocome.clearing.operation.service.account.WccFileAnalysisConfigService;

/**
 * 解析参数配置管理。主配置与配置明细1：N
 */
@Controller
@RequestMapping("/system")
@Auth({ "JXPZ_A", "JXPZ_M", "JXPZ_V" })
public class AnalysisConfigAction {
	@Resource
	private WccFileAnalysisConfigService wccFileAnalysisConfigService;
	@Resource
	private MessageSource messageSource;
	@Resource
	private SystemCodingService systemCodingService;

	/**
	 * 查看配置明细列表。
	 * 
	 * @param model
	 *            数据模型
	 * @param searchModel
	 *            搜索条件
	 */
	@RequestMapping("analysis-listConfigLists")
	@Auth({ "JXPZ_A", "JXPZ_M", "JXPZ_V" })
	public void listConfigList(Model model,
			AnalysisConfigSearchModel searchModel) {
		searchModel.setOrderBy("columnNo");
		Page<WccFileAnalysisConfigList> page = wccFileAnalysisConfigService
				.searchConfigListPage(searchModel);
		model.addAttribute("citys",
				systemCodingService.getSystemCoding("CITY_CODE"));
		model.addAttribute("sourceTypes",
				systemCodingService.getSystemCoding("SOURCE_TYPE"));
		model.addAttribute("dataTypes",
				systemCodingService.getSystemCoding("DATA_TYPE"));
		model.addAttribute("listPage", page);
		model.addAttribute("searchModel", searchModel);
	}

	/**
	 * 添加配置明细
	 * 
	 * @param mainId
	 * @param model
	 * @description:
	 */
	@RequestMapping("analysis-addConfigList")
	@Auth({ "JXPZ_A", "JXPZ_M", "JXPZ_V" })
	public void addConfigList(WccFileAnalysisConfigMainId mainId, Model model) {
		model.addAttribute("dataTypes",
				systemCodingService.getSystemCoding("DATA_TYPE"));
		model.addAttribute("citys",
				systemCodingService.getSystemCoding("CITY_CODE"));
		model.addAttribute("sourceTypes",
				systemCodingService.getSystemCoding("SOURCE_TYPE"));
		model.addAttribute("main", new WccFileAnalysisConfigList());
	}

	/**
	 * 编辑配置明细
	 * 
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("analysis-editConfigList")
	@Auth({ "JXPZ_A", "JXPZ_M", "JXPZ_V" })
	public void editConfigList(Model model, WccFileAnalysisConfigListId id) {
		model.addAttribute("citys",
				systemCodingService.getSystemCoding("CITY_CODE"));
		model.addAttribute("sourceTypes",
				systemCodingService.getSystemCoding("SOURCE_TYPE"));
		model.addAttribute("dataTypes",
				systemCodingService.getSystemCoding("DATA_TYPE"));
		model.addAttribute("main",
				wccFileAnalysisConfigService.getConfigList(id));
	}

	/**
	 * 更新配置明细
	 * 
	 * @param list
	 * @return
	 * @description:
	 */
	@RequestMapping("analysis-updateConfigList")
	@Auth({ "JXPZ_M" })
	public ModelAndView updateConfigList(WccFileAnalysisConfigList list) {
		try {
			wccFileAnalysisConfigService.updateConfigList(list);
		} catch (BusinessException be) {
			messageSource.thrown(be.getMessage());
		} catch (Exception e) {
			messageSource.thrown("更新解析主配置失败");
		}
		return DialogResultUtils.closeDialog(messageSource.get("配置更新成功"),
				"system-analysis-listConfigLists");
	}

	/**
	 * 保存配置明细
	 * 
	 * @param list
	 * @return
	 * @description:
	 */
	@RequestMapping("analysis-saveConfigList")
	@Auth({ "JXPZ_A" })
	public ModelAndView saveConfigList(WccFileAnalysisConfigList list) {
		try {
			wccFileAnalysisConfigService.addConfigList(list);
		} catch (BusinessException be) {
			messageSource.thrown(be.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			messageSource.thrown("保存解析配置明细失败");
		}
		return DialogResultUtils.closeDialog(messageSource.get("配置保存成功"),
				"system-analysis-listConfigLists");
	}

	/**
	 * 删除配置明细
	 * 
	 * @param id
	 * @return
	 * @description:
	 */
	@RequestMapping("analysis-delConfigList")
	@Auth({ "JXPZ_M" })
	public ModelAndView removeConfigList(WccFileAnalysisConfigListId id) {
		try {
			wccFileAnalysisConfigService.removeConfigList(id);
		} catch (BusinessException be) {
			messageSource.thrown(be.getMessage());
		} catch (Exception e) {
			messageSource.thrown("删除解析主配置失败");
		}
		return DialogResultUtils.reloadDialog(messageSource.get("配置删除成功"),
				"system-analysis-listConfigLists");
	}

	/**
	 * 查看主配置列表。
	 * 
	 * @param model
	 *            数据模型
	 * @param searchModel
	 *            搜索条件
	 */
	@RequestMapping("analysis-listMain")
	@Auth({ "JXPZ_A", "JXPZ_M", "JXPZ_V" })
	public void listMain(Model model, AnalysisConfigSearchModel searchModel) {
		Page<WccFileAnalysisConfigMain> page = wccFileAnalysisConfigService
				.searchMainConfig(searchModel);
		model.addAttribute("citys",
				systemCodingService.getSystemCoding("CITY_CODE"));
		model.addAttribute("sourceTypes",
				systemCodingService.getSystemCoding("SOURCE_TYPE"));
		model.addAttribute("mainPage", page);
		model.addAttribute("searchModel", searchModel);
	}

	/**
	 * 添加主配置
	 * 
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("analysis-addMain")
	@Auth({ "JXPZ_A", "JXPZ_M", "JXPZ_V" })
	public void addMain(Model model) {
		model.addAttribute("citys",
				systemCodingService.getSystemCoding("CITY_CODE"));
		model.addAttribute("sourceTypes",
				systemCodingService.getSystemCoding("SOURCE_TYPE"));
		model.addAttribute(new WccFileAnalysisConfigMain());
	}

	/**
	 * 编辑主配置
	 * 
	 * @param model
	 * @param id
	 * @description:
	 */
	@RequestMapping("analysis-editMain")
	@Auth({ "JXPZ_A", "JXPZ_M", "JXPZ_V" })
	public void editMain(Model model, WccFileAnalysisConfigMainId id) {
		model.addAttribute("citys",
				systemCodingService.getSystemCoding("CITY_CODE"));
		model.addAttribute("sourceTypes",
				systemCodingService.getSystemCoding("SOURCE_TYPE"));
		model.addAttribute("main",
				wccFileAnalysisConfigService.getConfigMain(id));
	}

	/**
	 * 保存主配置
	 * 
	 * @param main
	 * @return
	 * @description:
	 */
	@RequestMapping("analysis-saveMain")
	@Auth({ "JXPZ_A" })
	public ModelAndView saveMain(WccFileAnalysisConfigMain main) {
		try {
			wccFileAnalysisConfigService.addConfigMain(main);
		} catch (BusinessException be) {
			messageSource.thrown(be.getMessage());
		} catch (Exception e) {
			messageSource.thrown("保存解析主配置失败");
		}
		return DialogResultUtils.closeAndReloadNavTab(
				messageSource.get("配置保存成功"), "system-analysis-listMain");
	}

	/**
	 * 更新主配置
	 * 
	 * @param main
	 * @return
	 * @description:
	 */
	@RequestMapping("analysis-updateMain")
	@Auth({ "JXPZ_M" })
	public ModelAndView updateMain(WccFileAnalysisConfigMain main) {
		try {
			wccFileAnalysisConfigService.updateConfigMain(main);
		} catch (BusinessException be) {
			messageSource.thrown(be.getMessage());
		} catch (Exception e) {
			messageSource.thrown("更新解析主配置失败");
		}
		return DialogResultUtils.closeAndReloadNavTab(
				messageSource.get("配置保存成功"), "system-analysis-listMain");
	}

	/**
	 * 删除主配置
	 * 
	 * @param id
	 * @return
	 * @description:
	 */
	@RequestMapping("analysis-delMain")
	@Auth({ "JXPZ_M" })
	public ModelAndView removeMain(WccFileAnalysisConfigMainId id) {
		try {
			wccFileAnalysisConfigService.removeConfigMain(id);
		} catch (Exception e) {
			messageSource.thrown("删除解析主配置失败");
		}
		return DialogResultUtils.reloadNavTab(messageSource.get("配置删除成功"),
				"system-analysis-listMain");
	}

}
