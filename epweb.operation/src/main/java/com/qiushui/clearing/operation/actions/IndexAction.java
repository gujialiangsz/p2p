package com.qiushui.clearing.operation.actions;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qiushui.base.security.annotations.Auth;
import com.qiushui.base.util.DateUtils;
import com.qiushui.clearing.core.service.system.SystemCodingService;
import com.qiushui.clearing.operation.service.system.SecurityService;

/**
 * 主页。
 */
@Controller
@RequestMapping("/")
@Auth
public class IndexAction {
	@Resource
	private SecurityService securityService;
	@Resource
	private SystemCodingService systemCodingService;

	/**
	 * 查看主页。
	 * 
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("index")
	public void index(Model model) {
		// 当前登录用户信息
		model.addAttribute("currentUser", securityService.getCurrentUser());
		model.addAttribute("actorlist",
				systemCodingService.getSystemCoding("ACTOR"));
		model.addAttribute("dateNow",
				DateUtils.format(new Date(), DateUtils.SECOND));
	}
}
