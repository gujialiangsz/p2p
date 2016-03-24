package com.biocome.clearing.operation.actions.data;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.biocome.clearing.core.entity.account.AttendanceDevice;
import com.biocome.clearing.core.entity.account.WccDevice;
import com.biocome.clearing.core.service.system.SystemCodingService;
import com.biocome.clearing.operation.model.data.BaseDataSearchModel;
import com.biocome.clearing.operation.service.data.BaseDataService;

/**
 * 数据明细。
 */
@Controller
@RequestMapping("/data")
public class BaseDataDetailAction {
	@Resource
	BaseDataService baseDataService;
	@Resource
	SystemCodingService systemCodingService;

	@RequestMapping("basedata-getUser")
	public void getUser(Model model, Long id) {
		model.addAttribute("userTypes",
				systemCodingService.getSystemCoding("USER_TYPE"));
		model.addAttribute("userStatus",
				systemCodingService.getSystemCoding("USER_STATUS"));
		model.addAttribute("coding", baseDataService.getUser(id));
	}

	@Auth("SBXX_V")
	@RequestMapping("detail-device")
	public void getDevice(Model model, BaseDataSearchModel search) {
		model.addAttribute("userTypes",
				systemCodingService.getSystemCoding("USER_TYPE"));
		model.addAttribute("searchModel", search);
		model.addAttribute("clPage", baseDataService.findDevicePage(search));
	}

	@Auth("SBXX_M")
	@RequestMapping("device-add")
	public void addDevice(Model model) {
		model.addAttribute("device", new WccDevice());
	}

	@Auth("SBXX_M")
	@RequestMapping("device-edit")
	public void editDevice(Model model, Long id) {
		model.addAttribute("device", baseDataService.getDevice(id));
	}

	@Auth("SBXX_M")
	@RequestMapping("device-save")
	public ModelAndView saveDevice(Model model, WccDevice device) {
		try {
			if (device.getId() == null)
				baseDataService.saveDevice(device);
			else
				baseDataService.updateDevice(device);
			return DialogResultUtils.closeAndReloadNavTab("保存设备成功",
					"data-account-device");
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("保存设备失败");
		}
	}

	@Auth("SBXX_M")
	@RequestMapping("device-del")
	public ModelAndView deleteDevice(Model model, Long id) {
		try {
			baseDataService.delDevice(id);
			return DialogResultUtils.reloadNavTab("删除设备成功",
					"data-account-device");
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除设备失败");
		}
	}

	@Auth("SBXX_M")
	@RequestMapping("device-unbind")
	public ModelAndView unbindDevice(Model model, Long id) {
		try {
			baseDataService.unbindDevice(id);
			return DialogResultUtils.reloadNavTab("解绑设备成功",
					"data-account-device");
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除设备失败");
		}
	}

	@Auth("SBXX_M")
	@RequestMapping("device-import")
	public void importDevice(Model model, BaseDataSearchModel search) {
		model.addAttribute("model", model);
	}

	/**
	 * 上传设备信息文件
	 * 
	 * @param file
	 * @param request
	 * @return
	 * @description:
	 */
	@RequestMapping("/device-upload")
	@ResponseBody
	public ModelAndView upload(@RequestParam("file") MultipartFile file,
			HttpServletRequest request) {
		if (!file.isEmpty()) {
			try {
				baseDataService.analysisFile(file.getInputStream(),
						file.getOriginalFilename());
				return DialogResultUtils.closeAndReloadNavTab("导入设备成功",
						"data-account-device");
			} catch (BusinessException e) {
				return DialogResultUtils.error("格式错误，导入失败");
			} catch (Exception e) {
				e.printStackTrace();
				return DialogResultUtils.error("导入发生异常");
			}
		} else {
			return null;
		}
	}

	/*---------------------------------考勤设备-----------------------------------*/
	@Auth("KQSB_V")
	@RequestMapping("detail-attendanceDevice")
	public void getAttendanceDevice(Model model, BaseDataSearchModel search) {
		model.addAttribute("searchModel", search);
		model.addAttribute("clPage",
				baseDataService.findAttendanceDevicePage(search));
	}

	@Auth("KQSB_M")
	@RequestMapping("attendanceDevice-add")
	public void addAttendanceDevice(Model model) {
		model.addAttribute("device", new AttendanceDevice());
	}

	@Auth("KQSB_M")
	@RequestMapping("attendanceDevice-edit")
	public void editAttendanceDevice(Model model, Long id) {
		model.addAttribute("device", baseDataService.getAttendanceDevice(id));
	}

	@Auth("KQSB_M")
	@RequestMapping("attendanceDevice-disabled")
	public ModelAndView disable(Model model, Long id) {
		try {
			baseDataService.disabledAttendanceDevice(id);
			return DialogResultUtils.reloadNavTab("禁用设备成功",
					"data-account-attendanceDevice");
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("禁用设备失败");
		}
	}

	@Auth("KQSB_M")
	@RequestMapping("attendanceDevice-enabled")
	public ModelAndView enable(Model model, Long id) {
		try {
			baseDataService.enabledAttendanceDevice(id);
			return DialogResultUtils.reloadNavTab("启用设备成功",
					"data-account-attendanceDevice");
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("禁用设备失败");
		}
	}

	@Auth("KQSB_M")
	@RequestMapping("attendanceDevice-save")
	public ModelAndView saveAttendanceDevice(Model model,
			AttendanceDevice device) {
		try {
			if (device.getId() == 0)
				baseDataService.saveAttendanceDevice(device);
			else
				baseDataService.updateAttendanceDevice(device);
			return DialogResultUtils.closeAndReloadNavTab("保存设备成功",
					"data-account-attendanceDevice");
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("保存设备失败");
		}
	}

}
