package com.biocome.base.security.actions;

import java.io.OutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biocome.base.exception.BusinessException;
import com.biocome.base.security.component.AuthCounter;
import com.biocome.base.security.component.Captcha;
import com.biocome.base.security.model.LoginModel;
import com.biocome.base.security.service.AbstractSecurityService;

/**
 * 登录基类。
 */
public abstract class AbstractLoginAction {
	@Resource
	protected AbstractSecurityService<?, ?, ?, ?, ?> securityService;
	@Resource
	protected Captcha captcha;
	@Resource
	protected AuthCounter authCounter;

	/**
	 * 查看登录页面。
	 * 
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("login")
	public void login(Model model) {
		model.addAttribute(new LoginModel());
		model.addAttribute(authCounter);
	}

	/**
	 * 验证登录。
	 * 
	 * @param loginModel
	 *            登录数据模型
	 * @param errors
	 *            错误信息
	 * @param model
	 *            数据模型
	 * 
	 * @return 登录成功返回系统首页，失败返回登录页面。
	 */
	@RequestMapping("login-auth")
	public String auth(LoginModel loginModel, BindingResult errors,
			Model model, HttpServletResponse response) {
		// if (authCounter.isOver()) {
		// if (!captcha.validate(loginModel.getCode())) {
		// errors.reject("security.code.wrong");
		// model.addAttribute(authCounter);
		// return "login";
		// }
		// }
		try {
			securityService.signIn(loginModel.getUsername(),
					loginModel.getPassword());
			authCounter.clean();
			response.sendRedirect("index");
			return null;
		} catch (BusinessException e) {
			errors.reject("none", e.getMessage());
			authCounter.add();
			model.addAttribute(authCounter);
			return "login";
		} catch (Exception e) {
			errors.reject("none", e.getMessage());
			authCounter.add();
			model.addAttribute(authCounter);
			return "login";
		}
	}

	/**
	 * 输出验证码图片。
	 * 
	 * @param out
	 *            页面输出流
	 * 
	 * @throws Exception
	 *             图片输出失败时抛出异常。
	 */
	@RequestMapping("captcha-code-image")
	public void captchaCode(OutputStream out) throws Exception {
		ImageIO.write(captcha.generateImage(), "JPEG", out);
	}

	/**
	 * 退出登录。
	 * 
	 * @return 返回登录页面。
	 */
	@RequestMapping("logout")
	public String logout() {
		securityService.signOut();
		return "redirect:/login";
	}
}
