package com.biocome.clearing.operation.actions.system;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.biocome.base.mvc.util.DialogResultUtils;
import com.biocome.clearing.operation.service.system.SecurityService;

@Controller
@RequestMapping("/system")
public class SystemAction {
	@Resource
	private SecurityService securityService;

	/**
	 * 上传处理
	 * 
	 * @param file
	 *            要求file控件的名字为file
	 * @param request
	 *            请求链接：uploader:'<@s.url
	 *            '/system/system-upload'/>;jsessionid=${sessionid}?',后面添加参数
	 *            可选参数：folder（文件保存目录，目前相对当前路径，可以做成配置）默认是应用根目录下tmp目录；forwardurl（
	 *            上传完成后转发的链接，可进行后续处理）， 默认上传完成后直接返回
	 * @return
	 */
	@RequestMapping("system-upload")
	@ResponseBody
	public ModelAndView upload(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request) {
		try {
			String path = request.getSession().getServletContext()
					.getRealPath("upload");
			String fileName = file.getOriginalFilename();
			Object folder = request.getParameter("folder");
			File targetFile = new File(path + "/"
					+ (folder == null ? "tmp" : (String) folder), fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			file.transferTo(targetFile);
			Object forward = request.getParameter("forwardurl");
			if (forward != null) {
				return new ModelAndView("forward:" + forward + "?fileName="
						+ targetFile.getPath());
			} else {
				return DialogResultUtils.reloadNavTab("上传成功",
						request.getParameter("navTabId"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return DialogResultUtils.errorNavTab("上传失败",
					request.getParameter("navTabId"));
		}
	}

	/**
	 * 进入下载页面
	 */
	@RequestMapping("system-download")
	public void download(String testname) {
		System.out.println(testname);
	}

	@RequestMapping(value = "current-permission")
	@ResponseBody
	public String getCurrentUserPermission() {
		return JSONArray
				.fromObject(securityService.getCurrentUserPermissions())
				.toString();
	}
}
