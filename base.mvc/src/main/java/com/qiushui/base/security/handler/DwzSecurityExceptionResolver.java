package com.qiushui.base.security.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import com.qiushui.base.mvc.handler.DwzExceptionResolver;
import com.qiushui.base.mvc.model.DwzResult;
import com.qiushui.base.util.CollectionUtils;

/**
 * 基于安全模块的DWZ异常处理。
 */
public class DwzSecurityExceptionResolver extends DwzExceptionResolver {
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception) {
		// 如果是系统首页抛出未登录异常则直接跳转到登录页面。
		if (exception instanceof UnauthenticatedException
				&& handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			RequestMapping requestMapping = handlerMethod
					.getMethodAnnotation(RequestMapping.class);
			if (CollectionUtils.contains(requestMapping.value(), "index")) {
				return new ModelAndView("redirect:/login");
			}
		}
		return super.doResolveException(request, response, handler, exception);
	}

	@Override
	protected void logException(Exception ex, HttpServletRequest request) {
		if (ex instanceof UnauthenticatedException) {
			return;
		}
		super.logException(ex, request);
	}

	@Override
	protected void processCustomExceptions(ModelAndView mav, Exception exception) {
		super.processCustomExceptions(mav, exception);
		if (exception instanceof UnauthenticatedException) {
			mav.addObject("ajaxResult", DwzResult.timeout());
		}
		if (exception instanceof UnauthorizedException) {
			mav.addObject("ajaxResult", DwzResult.denied());
		}
	}
}
