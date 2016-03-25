package com.qiushui.clearing.operation.actions.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.qiushui.base.mvc.util.DialogResultUtils;
import com.qiushui.base.security.annotations.Auth;
import com.qiushui.base.xstream.MessageSource;
import com.qiushui.clearing.operation.entity.system.Organ;
import com.qiushui.clearing.operation.entity.system.User;
import com.qiushui.clearing.operation.service.system.SecurityService;

/**
 * 机构管理
 */
@Controller
@RequestMapping("/system")
@Auth({ "BM_A", "BM_M", "BM_V" })
public class OrganAction {
	@Resource
	private SecurityService securityService;
	@Resource
	private MessageSource messageSource;

	/**
	 * 查看机构列表。
	 * 
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("organ-list")
	@Auth({ "BM_A", "BM_M", "BM_V" })
	public void list(Model model) {
		Organ rootOrgan = securityService.getAdminOrgan();
		model.addAttribute("parentOrgans", rootOrgan.getOrganTree());
		model.addAttribute("organ", rootOrgan);
	}

	/**
	 * 新增机构。
	 * 
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("organ-add")
	@Auth({ "BM_A" })
	public void add(Model model, String organId) {
		Organ rootOrgan = securityService.getAdminOrgan();
		Organ organ = new Organ();
		Organ currentOrgan = securityService.getOrgan(organId);
		organ.setOrdinal(securityService.getNextChildMaxOrdinal(organId));
		List<Organ> parentList = new ArrayList<Organ>();
		parentList.add(currentOrgan == null ? rootOrgan : currentOrgan);
		model.addAttribute("parentOrgans", parentList);
		model.addAttribute("organ", organ);
		model.addAttribute("organId", organId);
	}

	/**
	 * 保存机构
	 * 
	 * @param organ
	 *            机构
	 * @return 返回提示信息。
	 */
	@RequestMapping("organ-save")
	@Auth({ "BM_A" })
	public ModelAndView save(Organ organ) {
		if (organ != null) {
			organ.setName(organ.getName().replaceAll("\\s+", ""));
		}
		// 当前登录用户
		User currentUser = securityService.getCurrentUser();
		Date now = new Date();
		organ.setCreator(currentUser);
		organ.setModifier(currentUser);
		organ.setCreateDate(now);
		organ.setModifyDate(now);
		securityService.createOrgan(organ);
		return DialogResultUtils.closeAndReloadNavTab(
				messageSource.get("organ.add.success"), "system_organ-list");
	}

	/**
	 * 编辑机构。
	 * 
	 * @param organId
	 *            机构ID
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("organ-edit")
	@Auth({ "BM_M" })
	public void edit(String organId, Model model) {
		Organ rootOrgan = securityService.getAdminOrgan();
		List<Organ> list = rootOrgan.getOrganTree();
		Organ organ = securityService.getOrgan(organId);
		list.remove(organ);
		// list.add(organ.getParent());
		model.addAttribute("rootOrgan", rootOrgan);
		model.addAttribute("parentOrgans", list);
		model.addAttribute("hasChild", organ.getValidChilds().size() > 0);
		model.addAttribute("hasActor", securityService.findUserByOrgan(organ));
		model.addAttribute("organ", organ);
	}

	/**
	 * 更新机构。
	 * 
	 * @param organ
	 *            机构
	 * @return 返回提示信 息。
	 */
	@RequestMapping("organ-update")
	@Auth({ "BM_M" })
	public ModelAndView update(Organ organ) {
		if (organ != null) {
			organ.setName(organ.getName().replaceAll("\\s+", ""));
		}
		securityService.updateOrgan(organ);
		return DialogResultUtils.reloadNavTab(
				messageSource.get("organ.edit.success"), "system_organ-list");
	}

	/**
	 * 删除机构。
	 * 
	 * @param organId
	 *            机构ID
	 * @return 返回提示信息。
	 */
	@RequestMapping("organ-delete")
	@Auth({ "BM_M" })
	public ModelAndView delete(String organId) {
		securityService.deleteOrgan(organId);
		return DialogResultUtils.reloadNavTab(
				messageSource.get("organ.delete.success"), "system_organ-list");
	}

}
