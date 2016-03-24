package com.biocome.clearing.operation.actions.business;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.biocome.base.exception.BusinessException;
import com.biocome.base.model.Page;
import com.biocome.base.mvc.util.DialogResultUtils;
import com.biocome.base.security.annotations.Auth;
import com.biocome.clearing.core.service.system.SystemCodingService;
import com.biocome.clearing.operation.entity.business.WccAgent;
import com.biocome.clearing.operation.entity.business.WccQuestion;
import com.biocome.clearing.operation.model.business.BusinessSearchModel;
import com.biocome.clearing.operation.service.business.BusinessService;

@Controller
@RequestMapping("/business")
@Auth({ "SHGL_A", "SHGL_V", "SHGL_M" })
public class BusinessAction {
	@Resource
	private BusinessService businessService;
	@Resource
	private SystemCodingService systemCodingService;

	@RequestMapping("agent-list")
	@Auth({ "SHGL_A", "SHGL_V", "SHGL_V" })
	public void listAgent(Model model, BusinessSearchModel searchModel) {
		Page<WccAgent> page = businessService.findAgentPage(searchModel);
		model.addAttribute("products",
				systemCodingService.getSystemCoding("PRODUCT"));
		model.addAttribute("types",
				systemCodingService.getSystemCoding("AGENT_TYPE"));
		model.addAttribute("clPage", page);
		model.addAttribute("searchModel", searchModel);
	}

	/**
	 * 新增代理商。
	 * 
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("agent-add")
	@Auth({ "SHGL_A" })
	public void addAgent(Model model) {
		WccAgent WccAgent = new WccAgent();
		model.addAttribute("agent", WccAgent);
		model.addAttribute("products",
				systemCodingService.getSystemCoding("PRODUCT"));
		model.addAttribute("types",
				systemCodingService.getSystemCoding("AGENT_TYPE"));
	}

	/**
	 * 保存代理商。
	 * 
	 * @param WccAgent
	 *            用户
	 * @param actor
	 *            职务
	 * @return 返回提示信息。
	 */
	@RequestMapping("agent-save")
	@Auth({ "SHGL_A" })
	public ModelAndView saveAgent(WccAgent WccAgent) {
		try {
			businessService.createWccAgent(WccAgent);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("保存代理商失败");
		}
		return DialogResultUtils.closeAndReloadNavTab("保存代理商成功",
				"business-agent-list");
	}

	/**
	 * 编辑代理商。
	 * 
	 * @param WccAgentId
	 *            用户ID
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("agent-edit")
	@Auth({ "SHGL_M" })
	public void editAgent(Long id, Model model) {
		model.addAttribute("agent", businessService.findAgent(id));
		model.addAttribute("products",
				systemCodingService.getSystemCoding("PRODUCT"));
		model.addAttribute("types",
				systemCodingService.getSystemCoding("AGENT_TYPE"));
	}

	/**
	 * 
	 * @param WccAgent
	 *            用户
	 * @return 返回提示信息。
	 */
	@RequestMapping("agent-update")
	@Auth({ "SHGL_M" })
	public ModelAndView updateAgent(WccAgent agent) {
		try {
			businessService.updateWccAgent(agent);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("保存代理商失败");
		}
		return DialogResultUtils.closeAndReloadNavTab("保存代理商成功",
				"business-agent-list");
	}

	/**
	 * 删除代理商。
	 * 
	 * @param WccAgentId
	 *            用户ID
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("agent-del")
	@Auth({ "SHGL_M" })
	public ModelAndView delAgent(Long id, Model model) {
		try {
			businessService.remove(id);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除代理商失败");
		}
		return DialogResultUtils.reloadNavTab("删除代理商成功", "business-agent-list");
	}

	@RequestMapping("question-list")
	@Auth({ "WTFK_V", "WTFK_M" })
	public void listQuestion(Model model, BusinessSearchModel searchModel) {
		searchModel.setOrderBy("status,operateDate");
		searchModel.setSort("desc");
		Page<WccQuestion> page = businessService.findQuestionPage(searchModel);
		model.addAttribute("products",
				systemCodingService.getSystemCoding("PRODUCT"));
		model.addAttribute("clPage", page);
		model.addAttribute("searchModel", searchModel);
	}

	/**
	 * 编辑代理商。
	 * 
	 * @param WccAgentId
	 *            用户ID
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("question-edit")
	@Auth({ "WTFK_M" })
	public void editQuestion(Long id, Model model) {
		model.addAttribute("qs", businessService.findQuestion(id));
		model.addAttribute("products",
				systemCodingService.getSystemCoding("PRODUCT"));
	}

	/**
	 * 
	 * @param WccAgent
	 *            用户
	 * @return 返回提示信息。
	 */
	@RequestMapping("question-update")
	@Auth({ "WTFK_M" })
	public ModelAndView updateQuestion(WccQuestion question) {
		try {
			businessService.updateQuestion(question);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("回复问题失败");
		}
		return DialogResultUtils.closeAndReloadNavTab("回复问题成功",
				"business-question-list");
	}

}
