package com.biocome.clearing.operation.actions;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biocome.base.security.actions.AbstractLoginAction;
import com.biocome.base.security.model.LoginModel;
import com.biocome.base.xstream.MessageSource;
import com.biocome.clearing.operation.service.system.SecurityService;

/**
 * 登录。
 */
@Controller
@RequestMapping("/")
public class LoginAction extends AbstractLoginAction {
	@Resource
	protected MessageSource messageSource;
	@Resource
	private SecurityService securityService;

	/**
	 * 查看登录页面。
	 * 
	 * @param model
	 *            数据模型
	 */
	@Override
	public void login(Model model) {
		model.addAttribute(new LoginModel());
	}

}
