package com.biocome.clearing.operation.actions.health;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.biocome.base.exception.BusinessException;
import com.biocome.base.mvc.util.DialogResultUtils;
import com.biocome.base.security.annotations.Auth;
import com.biocome.clearing.core.entity.health.HealthEcg;
import com.biocome.clearing.core.model.data.HealthSearchModel;
import com.biocome.clearing.operation.service.data.HealthDataService;

/**
 * 健康数据明细。
 */
@Controller
@RequestMapping("/health")
public class HealthDataAction {
	@Resource
	HealthDataService healthDataService;

	@Auth("XDSJ_V")
	@RequestMapping("detail-ecg")
	public void getEcgDetail(Model model, HealthSearchModel search) {
		if (search == null) {
			search = new HealthSearchModel();
		}
		model.addAttribute("searchModel", search);
		search.setSort("desc");
		search.setOrderBy("beginTime");
		model.addAttribute("clPage", healthDataService.findPage(search));
	}

	@Auth("XDSJ_V")
	@RequestMapping("detail-export")
	public void exportEcgDetail(Model model, HealthSearchModel search,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			if (search == null) {
				search = new HealthSearchModel();
			}
			model.addAttribute("searchModel", search);
			search.setSort("desc");
			search.setOrderBy("beginTime");
			healthDataService.exportFile(search, request, response);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.fillInStackTrace());
			e.printStackTrace();
			throw new BusinessException("导出文件异常");
		}
	}

	@Auth("XDSJ_V")
	@RequestMapping("detail-ecgData")
	public void getEcgData(Model model, Long id) {
		model.addAttribute("ecgData", healthDataService.getEcgData(id));
	}

	@Auth("XDSJ_V")
	@RequestMapping("ecg-del")
	public ModelAndView deleteEcg(Model model, Long id) {
		try {
			healthDataService.delete(id);
		} catch (Exception e) {
			throw new BusinessException("删除心电数据失败");
		}
		return DialogResultUtils.reloadNavTab("删除心电数据成功", "health-detail-ecg");
	}

	@Auth("XDSJ_V")
	@RequestMapping("detail-ecgGraphic")
	public void getEcgGraphic(Model model, Long id) {
		try {
			HealthEcg ecg = healthDataService.getEcg(id);
			model.addAttribute("ecgData", healthDataService.getEcgData(ecg));
			model.addAttribute("beginTime", ecg.getBeginDate());
			model.addAttribute("endTime", ecg.getEndDate());
			model.addAttribute("times",
					(ecg.getEndTime() - ecg.getBeginTime()) / 1000);
		} catch (Exception e) {
			throw new BusinessException("读取心电数据失败");
		}
	}

	@Auth("XDSJ_V")
	@RequestMapping("ecg-remark")
	public void editRemark(Model model, Long id) {
		try {
			HealthEcg ecg = healthDataService.getEcg(id);
			model.addAttribute("id", ecg.getId());
			model.addAttribute("remark", ecg.getRemark());
		} catch (Exception e) {
			throw new BusinessException("读取备注失败");
		}
	}

	@Auth("XDSJ_V")
	@RequestMapping("ecg-update")
	public ModelAndView update(Model model, Long id, String remark) {
		try {
			healthDataService.update(id, remark);
		} catch (Exception e) {
			throw new BusinessException("修改备注失败");
		}
		return DialogResultUtils.closeAndReloadNavTab("修改备注成功",
				"health-detail-ecg");
	}
}
