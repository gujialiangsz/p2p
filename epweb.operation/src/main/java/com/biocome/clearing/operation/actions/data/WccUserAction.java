package com.biocome.clearing.operation.actions.data;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biocome.base.model.Page;
import com.biocome.base.security.annotations.Auth;
import com.biocome.base.xstream.MessageSource;
import com.biocome.clearing.core.entity.account.WccUser;
import com.biocome.clearing.core.model.data.WccUserSearchModel;
import com.biocome.clearing.core.service.data.WccUserService;
import com.biocome.clearing.core.service.system.SystemCodingService;

/**
 * 用户管理。
 */
@Controller
@RequestMapping("/data")
@Auth({ "ZCYH_V" })
public class WccUserAction {
	@Resource
	private MessageSource messageSource;
	@Resource
	private SystemCodingService systemCodingService;
	@Resource
	private WccUserService wccUserService;

	/**
	 * 查看用户列表。
	 * 
	 * @param model
	 *            数据模型
	 * @param searchModel
	 *            搜索条件
	 */
	@RequestMapping("wccuser-list")
	public void list(Model model, WccUserSearchModel searchModel) {
		Page<WccUser> page = wccUserService.findPage(searchModel);
		model.addAttribute("userPage", page);
		model.addAttribute("searchModel", searchModel);
	}
}
