package com.biocome.clearing.operation.actions.system;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biocome.base.model.Page;
import com.biocome.base.security.annotations.Auth;
import com.biocome.base.util.DateUtils;
import com.biocome.clearing.operation.entity.system.BnLog;
import com.biocome.clearing.operation.model.system.LogSearchModel;
import com.biocome.clearing.operation.service.system.BnLogger;
import com.biocome.clearing.operation.service.system.SecurityService;

/**
 * 日志管理。
 */
@Controller
@RequestMapping("/system")
@Auth({ "RZ_V" })
public class LogAction {

	@Resource
	private SecurityService securityService;
	@Resource
	private BnLogger bnLogger;

	/**
	 * 查看用户操作日志列表
	 * 
	 * @param model
	 * @param searchModel
	 */
	@RequestMapping("log-list")
	@Auth({ "RZ_V" })
	public void operaList(Model model, LogSearchModel searchModel) {
		searchModel.setSort("desc");
		searchModel.setOrderBy("operateTime");
		if (searchModel.getStartTime() == null) {
			searchModel.setStartTime(DateUtils.rollByMonth(new Date(), -1));
		}
		if (searchModel.getEndTime() != null) {
			searchModel.setEndTime(DateUtils.rollByDay(new Date(), 1));
		}
		Page<BnLog> bnLogPage = bnLogger.searchLogPage(searchModel);
		model.addAttribute("pageModel", bnLogPage);
		model.addAttribute("searchModel", searchModel);
	}
}
