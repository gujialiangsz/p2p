package com.biocome.clearing.operation.actions.report;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

import com.biocome.base.security.annotations.Auth;
import com.biocome.clearing.core.service.system.SystemCodingService;
import com.biocome.clearing.operation.model.report.ReportSearchModel;

/**
 * 统计类报表。
 */
@Controller
@RequestMapping("/report")
public class StatisticsReportAction {

	@Resource
	private SystemCodingService systemCodingService;

	/**
	 * 消费统计报表
	 * 
	 * @param request
	 * @param response
	 * @param realPath
	 * @param searchModel
	 * @param model
	 * @return
	 * @description:
	 */
	@RequestMapping("statistics-consume-report")
	public ModelAndView getConsumeDetail(HttpServletRequest request,
			HttpServletResponse response, String realPath,
			ReportSearchModel searchModel, Model model) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		model.addAttribute("formats",
				systemCodingService.getSystemCoding("REPORT_FORMAT"));
		model.addAttribute("citys",
				systemCodingService.getSystemCoding("CITY_CODE"));
		model.addAttribute("cardTypes",
				systemCodingService.getSystemCoding("CARD_TYPE"));
		modelMap.put(JasperReportsMultiFormatView.DEFAULT_FORMAT_KEY,
				searchModel.getType());
		modelMap.put("startTime", searchModel.getStartTime());
		modelMap.put("endTime", searchModel.getEndTime());
		// Jasper集成到spring中的实例名称，见报表目录配置文件
		return new ModelAndView("staticConsumeReport", modelMap);
	}

	@Auth({ "CZTJBB_V", "CSHTJBB_V", "TKXKTJBB_V", "TKXKTJBB_V,XFTJBB_V" })
	@RequestMapping("statistics-consume")
	public void getConsumeDetail(Model model) {
		model.addAttribute("formats",
				systemCodingService.getSystemCoding("REPORT_FORMAT"));
		model.addAttribute("citys",
				systemCodingService.getSystemCoding("CITY_CODE"));
		model.addAttribute("cardTypes",
				systemCodingService.getSystemCoding("CARD_TYPE"));
	}
}
