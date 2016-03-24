package com.biocome.clearing.operation.actions.system;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.biocome.base.mvc.util.DialogResultUtils;
import com.biocome.base.xstream.MessageSource;
import com.biocome.clearing.core.service.system.SystemCodingService;
import com.biocome.clearing.operation.entity.system.Actor;
import com.biocome.clearing.operation.service.system.SecurityService;

/**
 * 职务管理。
 */
@Controller
@RequestMapping("/system")
public class ActorAction {
	@Resource
	private SecurityService securityService;
	@Resource
	private MessageSource messageSource;
	@Resource
	private SystemCodingService systemCodingService;

	/**
	 * 新增职务。
	 * 
	 * @param userId
	 *            关联用户ID
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("actor-add")
	public void add(String userId, Model model) {
		Actor actor = new Actor();
		actor.setUser(securityService.getUser(userId));
		model.addAttribute(actor);
		model.addAttribute("actorlist",
				systemCodingService.getSystemCoding("ACTOR"));
		model.addAttribute("rootOrgan", securityService.getCurrentUser()
				.getSettings().getDefaultActor().getOrgan());
		model.addAttribute("roles", securityService.getAllRole());
	}

	/**
	 * 保存职务。
	 * 
	 * @param actor
	 *            职务
	 * @return 返回提示信息。
	 */
	@RequestMapping("actor-save")
	public ModelAndView save(Actor actor) {
		securityService.createActor(actor);
		return DialogResultUtils.closeAndReloadDialog(
				messageSource.get("actor.add.success"), "system_user_edit");
	}

	/**
	 * 编辑职务。
	 * 
	 * @param actorId
	 *            职务ID
	 * @param model
	 *            数据模型
	 */
	@RequestMapping("actor-edit")
	public void edit(String actorId, Model model) {
		model.addAttribute(securityService.getActor(actorId));
		model.addAttribute("actorlist",
				systemCodingService.getSystemCoding("ACTOR"));
		model.addAttribute("rootOrgan", securityService.getCurrentUser()
				.getSettings().getDefaultActor().getOrgan());
		model.addAttribute("roles", securityService.getAllRole());
	}

	/**
	 * 更新职务。
	 * 
	 * @param actor
	 *            职务
	 * @return 返回提示信息。
	 */
	@RequestMapping("actor-update")
	public ModelAndView update(Actor actor) {
		securityService.updateActor(actor);
		return DialogResultUtils.closeAndReloadDialog(
				messageSource.get("actor.update.success"), "system_user_edit");
	}

	/**
	 * 删除职务。
	 * 
	 * @param actorId
	 *            职务ID
	 * @return 返回提示信息。
	 */
	@RequestMapping("actor-delete")
	public ModelAndView delete(String actorId) {
		securityService.deleteActor(actorId);
		return DialogResultUtils.reloadNavTab(
				messageSource.get("actor.delete.success"), "system_user_edit");
	}
}
