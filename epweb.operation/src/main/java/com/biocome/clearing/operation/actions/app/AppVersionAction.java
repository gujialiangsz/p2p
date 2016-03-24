package com.biocome.clearing.operation.actions.app;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.biocome.base.exception.BusinessException;
import com.biocome.base.mvc.util.DialogResultUtils;
import com.biocome.base.security.annotations.Auth;
import com.biocome.base.xstream.MessageSource;
import com.biocome.clearing.core.service.system.SystemCodingService;
import com.biocome.clearing.operation.entity.system.AppVersion;
import com.biocome.clearing.operation.model.system.AppSearchModel;
import com.biocome.clearing.operation.service.system.AppVersionService;

/**
 * 
 * 
 * @author 谷家良
 * @date 2015年8月10日 下午5:44:32
 * @Description: 软件版本管理信息，上传并提供下载地址
 */
@Controller
@RequestMapping("/app")
@Auth({ "YYBB_A", "YYBB_M", "YYBB_V" })
public class AppVersionAction {
	// app名称格式校验表达式
	@Value("#{settings['file.valid']}")
	private String versioncheck;
	@Value("#{settings['file.case']}")
	private String versioncase;
	@Resource
	private SystemCodingService systemCodingService;
	@Resource
	private MessageSource messageSource;
	@Resource
	private AppVersionService appVersionService;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping("/appVersion-list")
	public void list(AppSearchModel search, Model model) {
		search.setOrderBy("versioncode");
		search.setSort("DESC");
		model.addAttribute("mainPage", appVersionService.getPage(search));
		model.addAttribute("searchModel", search);
		model.addAttribute("apptypes",
				systemCodingService.getSystemCoding("APP_TYPE"));
		model.addAttribute("versions", appVersionService.getAllVersion());
	}

	/**
	 * 添加版本
	 * 
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("appVersion-add")
	@Auth({ "YYBB_A", "YYBB_M", "YYBB_V" })
	public void addAppVersion(Model model) {
		model.addAttribute("versions", appVersionService.getAllVersion());
		model.addAttribute("main", new AppVersion());
		model.addAttribute("apptypes",
				systemCodingService.getSystemCoding("APP_TYPE"));
		model.addAttribute("versioncheck", versioncheck);
		model.addAttribute("versioncase", versioncase);
	}

	/**
	 * 编辑版本
	 * 
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("appVersion-edit")
	@Auth({ "YYBB_A", "YYBB_M", "YYBB_V" })
	public void editVersion(Model model, Long id) {
		model.addAttribute("versions", appVersionService.getAllVersion());
		model.addAttribute("main", appVersionService.getVersion(id));
		model.addAttribute("apptypes",
				systemCodingService.getSystemCoding("APP_TYPE"));
		model.addAttribute("versioncheck", versioncheck);
		model.addAttribute("versioncase", versioncase);
	}

	/**
	 * 更新版本
	 * 
	 * @param main
	 * @return
	 * @description:
	 */
	@RequestMapping("appVersion-update")
	@Auth({ "YYBB_M" })
	public ModelAndView updateVersion(AppVersion main) {
		try {
			appVersionService.update(main);
			appVersionService.changeLastVersion(main.getType());
		} catch (BusinessException be) {
			messageSource.thrown(be.getMessage());
		} catch (Exception e) {
			messageSource.thrown("更新应用版本信息失败");
		}
		return DialogResultUtils.closeAndReloadNavTab(
				messageSource.get("应用版本信息更新成功"), "app-appVersion-list");
	}

	@RequestMapping("appVersion-save")
	@Auth({ "YYBB_A" })
	public ModelAndView saveVersion(AppVersion main) {
		try {
			appVersionService.save(main);
			appVersionService.changeLastVersion(main.getType());
		} catch (BusinessException be) {
			messageSource.thrown(be.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			messageSource.thrown("保存应用版本信息失败");
		}
		return DialogResultUtils.closeAndReloadNavTab(
				messageSource.get("应用版本信息保存成功"), "app-appVersion-list");
	}

	/**
	 * 删除版本
	 * 
	 * @param id
	 * @return
	 * @description:
	 */
	@RequestMapping("appVersion-del")
	@Auth({ "YYBB_M" })
	public ModelAndView removeVersion(Long id) {
		try {
			AppVersion version = appVersionService.delete(id);
			appVersionService.changeLastVersion(version.getType());
		} catch (BusinessException be) {
			messageSource.thrown(be.getMessage());
		} catch (Exception e) {
			messageSource.thrown("删除应用版本信息失败");
		}
		return DialogResultUtils.reloadNavTab(messageSource.get("应用版本信息删除成功"),
				"app-appVersion-list");
	}

	/**
	 * 上传应用，通过名称解析出版本信息，并且上传到云端
	 * 
	 * @param file
	 * @param request
	 * @return
	 * @description:
	 */
	@RequestMapping("/appVersion-upload")
	@ResponseBody
	public Object upload(@RequestParam("file") MultipartFile file,
			HttpServletRequest request) {
		if (!file.isEmpty()) {
			try {
				return appVersionService.analysisFile(file.getBytes(),
						file.getOriginalFilename());
			} catch (BusinessException e) {
				e.printStackTrace();
				return "format_error";
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		} else {
			logger.error("上传失败，上传文件信息：" + file.getOriginalFilename() + ","
					+ file.getSize());
			return null;
		}
	}
}
