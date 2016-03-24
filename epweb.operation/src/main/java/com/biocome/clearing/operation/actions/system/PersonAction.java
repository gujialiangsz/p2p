package com.biocome.clearing.operation.actions.system;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.biocome.base.mvc.util.DialogResultUtils;
import com.biocome.base.security.annotations.Auth;
import com.biocome.base.security.model.PwdChangeModel;
import com.biocome.base.xstream.MessageSource;
import com.biocome.clearing.core.service.system.SystemCodingService;
import com.biocome.clearing.operation.service.system.SecurityService;

/**
 * 个人管理。
 */
@Controller
@RequestMapping("/system")
@Auth({ "YH_A", "YH_M", "YH_V" })
public class PersonAction {
	@Resource
	private SecurityService securityService;
	@Resource
	private MessageSource messageSource;
	@Resource
	private SystemCodingService systemCodingService;

	/**
	 * 修改个人密码。
	 * 
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("person-pwd-change")
	@Auth({ "YH_A", "YH_M" })
	public void pwdChange(Model model) {
		model.addAttribute(new PwdChangeModel());
	}

	/**
	 * 保存个人密码。
	 * 
	 * @param pwdChangeModel
	 *            修改密码表单Model
	 * @return 返回保存个人密码提示信息。
	 */
	@RequestMapping("person-pwd-change-save")
	@Auth({ "YH_A", "YH_M" })
	public ModelAndView pwdChangeSave(PwdChangeModel pwdChangeModel) {
		securityService.changePassword(pwdChangeModel.getOldPwd(),
				pwdChangeModel.getNewPwd());
		return DialogResultUtils.closeAndReloadNavTab(messageSource
				.get("person.pwd.change.success"));
	}

	/**
	 * 切换职务。
	 * 
	 * @param actorId
	 *            职务ID
	 * @return 返回主页。
	 */
	@RequestMapping("person-actor-change")
	@Auth({ "YH_A", "YH_M" })
	public ModelAndView change(String actorId) {
		securityService.changeActor(actorId);
		return new ModelAndView("redirect:/index");
	}

	/**
	 * 显示职务。
	 * 
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("show-actor")
	@Auth({ "YH_A", "YH_M", "YH_V" })
	public void showActor(Model model) {
		model.addAttribute("actorlist",
				systemCodingService.getSystemCoding("ACTOR"));
		model.addAttribute("currentUser", securityService.getCurrentUser());
	}

}
