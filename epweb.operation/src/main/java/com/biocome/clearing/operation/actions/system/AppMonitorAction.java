package com.biocome.clearing.operation.actions.system;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.biocome.base.exception.BusinessException;
import com.biocome.base.model.SearchModel;
import com.biocome.base.mvc.util.DialogResultUtils;
import com.biocome.base.security.annotations.Auth;
import com.biocome.clearing.core.service.system.SystemCodingService;
import com.biocome.clearing.operation.entity.system.SystemMonitor;
import com.biocome.clearing.operation.service.system.SystemMonitorService;

@Controller
@RequestMapping("/system")
@Auth({ "XTJK_V", "XTJK_M" })
public class AppMonitorAction {

	@Resource
	SystemMonitorService systemMonitorService;
	@Resource
	SystemCodingService systemCodingService;

	@Value("#{settings['monitor.websocket.url']}")
	private String socketUrl;

	@RequestMapping("status-list")
	@Auth({ "XTJK_V" })
	public void list(Model model, SearchModel searchModel) {
		model.addAttribute("monitorStatus", systemMonitorService.getStatus());
		model.addAttribute("searchModel", searchModel);
		model.addAttribute("url", socketUrl);
		model.addAttribute("pages", systemMonitorService.getPage(searchModel));
		model.addAttribute("appTypes",
				systemCodingService.getSystemCoding("APP_MONITOR_TYPE"));
		model.addAttribute("appStatus",
				systemCodingService.getSystemCoding("APP_STATUS"));
	}

	@Auth("XTJK_M")
	@RequestMapping("status-add")
	public void add(Model model, Long id) {
		if (id != null) {
			model.addAttribute("monitor", systemMonitorService.getMonitor(id));
		} else {
			model.addAttribute("monitor", new SystemMonitor());
		}
		model.addAttribute("appTypes",
				systemCodingService.getSystemCoding("APP_MONITOR_TYPE"));
		model.addAttribute("appStatus",
				systemCodingService.getSystemCoding("APP_STATUS"));
	}

	@Auth("XTJK_M")
	@RequestMapping("status-edit")
	public void edit(Model model, Long id) {
		model.addAttribute("monitor", systemMonitorService.getMonitor(id));
		model.addAttribute("appTypes",
				systemCodingService.getSystemCoding("APP_MONITOR_TYPE"));
		model.addAttribute("appStatus",
				systemCodingService.getSystemCoding("APP_STATUS"));
	}

	@Auth("XTJK_M")
	@RequestMapping("status-startMonitor")
	public ModelAndView startMonitor(Model model, Long id) {
		systemMonitorService.setStatus(true);
		model.addAttribute("monitorStatus", systemMonitorService.getStatus());
		return DialogResultUtils.reloadNavTab("启动成功", "system-status-list");
	}

	@Auth("XTJK_M")
	@RequestMapping("status-stopMonitor")
	public ModelAndView stopMonitor(Model model, Long id) {
		systemMonitorService.setStatus(false);
		model.addAttribute("monitorStatus", systemMonitorService.getStatus());
		return DialogResultUtils.reloadNavTab("停止成功", "system-status-list");
	}

	/**
	 * 启动应用
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @description:
	 */
	@Auth("XTJK_M")
	@RequestMapping("status-start")
	public ModelAndView start(Model model, Long id) {
		try {
			systemMonitorService.start(id);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("启动应用出现异常");
		}
		model.addAttribute("monitorStatus", systemMonitorService.getStatus());
		return DialogResultUtils.reloadNavTab("启动成功", "system-status-list");
	}

	/**
	 * 停止应用
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @description:
	 */
	@Auth("XTJK_M")
	@RequestMapping("status-stop")
	public ModelAndView stop(Model model, Long id) {
		try {
			systemMonitorService.stop(id);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("启动应用出现异常");
		}
		model.addAttribute("monitorStatus", systemMonitorService.getStatus());
		return DialogResultUtils.reloadNavTab("停止成功", "system-status-list");
	}

	/**
	 * 启用监控配置
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @description:
	 */
	@Auth("XTJK_M")
	@RequestMapping("status-enable")
	public ModelAndView enable(Model model, Long id) {
		systemMonitorService.changeStatus(id, true);
		return DialogResultUtils.reloadNavTab("启用监控配置成功", "system-status-list");
	}

	/**
	 * 禁用监控配置
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @description:
	 */
	@Auth("XTJK_M")
	@RequestMapping("status-disable")
	public ModelAndView disable(Model model, Long id) {
		systemMonitorService.changeStatus(id, false);
		return DialogResultUtils.reloadNavTab("禁用监控配置成功", "system-status-list");
	}

	/**
	 * 保存监控配置
	 * 
	 * @param model
	 * @param monitor
	 * @return
	 * @description:
	 */
	@Auth("XTJK_M")
	@RequestMapping("status-save")
	public ModelAndView save(Model model, SystemMonitor monitor) {
		try {
			if (monitor.getId() != 0)
				systemMonitorService.save(monitor);
			else
				systemMonitorService.update(monitor);
			return DialogResultUtils.closeAndReloadNavTab("保存监控配置成功",
					"system-status-list");
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("保存监控配置失败");
		}
	}

	/**
	 * 删除监控配置
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @description:
	 */
	@Auth("XTJK_M")
	@RequestMapping("status-del")
	public ModelAndView delete(Model model, Long id) {
		try {
			systemMonitorService.delete(id);
			return DialogResultUtils.reloadNavTab("删除监控配置成功",
					"system-status-list");
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除监控配置失败");
		}
	}
}
