package com.qiushui.clearing.operation.service.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qiushui.base.db.dao.Dao;
import com.qiushui.base.model.BitCode;
import com.qiushui.base.model.Page;
import com.qiushui.base.security.annotations.Log;
import com.qiushui.base.security.constants.AdminIds;
import com.qiushui.base.security.permission.AdminPermission;
import com.qiushui.base.security.permission.Permission;
import com.qiushui.base.security.service.AbstractSecurityService;
import com.qiushui.base.util.BeanUtils;
import com.qiushui.base.util.StringUtils;
import com.qiushui.clearing.operation.entity.system.Actor;
import com.qiushui.clearing.operation.entity.system.Organ;
import com.qiushui.clearing.operation.entity.system.Role;
import com.qiushui.clearing.operation.entity.system.User;
import com.qiushui.clearing.operation.entity.system.UserSettings;
import com.qiushui.clearing.operation.model.system.UserSearchModel;

/**
 * 安全服务。
 */
@Service
@SuppressWarnings("unchecked")
public class SecurityService extends
		AbstractSecurityService<Organ, User, Role, Actor, UserSettings> {

	@Resource
	private SecurityService securityService;

	@Resource
	private Dao<User> userDao;
	@Resource
	private Dao<UserSettings> userSettingsDao;

	public static Map<String, Object> systemUserLoginInfo = new HashMap<String, Object>();

	/**
	 * 检查密码是否正确。
	 * 
	 * @param password
	 *            原密码
	 * @param ciphertext
	 *            加密后的密码
	 * @return 如果密码正确返回true，否则返回false。
	 */
	public boolean checkPassword(String password, String ciphertext) {
		return loginRealm.checkPassword(password, ciphertext);
	}

	/**
	 * 加密
	 * 
	 * @param password
	 * @return
	 * @description:
	 */
	public String encryptPassword(String password) {
		return loginRealm.encryptPassword(password);
	}

	@Transactional(readOnly = true)
	public List<User> getAllUser() {
		return userDao.findBy("isDeleted", false);
	}

	/**
	 * 获取指定员工号的用户。
	 * 
	 * @param userCode
	 *            用户编码
	 * @return 返回指定用户编码的用户。
	 */
	public User getUserByUserNo(String userNo) {
		List<User> list = userDao.createCriteria(
				Restrictions.eq("userNo", userNo),
				Restrictions.eq("isDeleted", false)).list();
		if (list == null || list.size() == 0)
			return null;
		else if (list.size() > 1)
			messageSource.thrown("关联用户不唯一");
		return list.get(0);
	}

	/**
	 * 获取指定用户名的用户。
	 * 
	 * @param username
	 *            用户名
	 * @return 返回指定用户名的用户。
	 */
	public User getUserByUsername(String username) {
		List<User> list = userDao.createCriteria(
				Restrictions.eq("username", username),
				Restrictions.eq("isDeleted", false)).list();
		if (list == null || list.size() == 0)
			return null;
		else if (list.size() > 1)
			messageSource.thrown("关联用户不唯一");
		return list.get(0);
	}

	/**
	 * 启用用户
	 * 
	 * @param userNo
	 *            员工号
	 */
	@Transactional
	public void enableUser(String userId) {
		User user = getUser(userId);
		user.setEnabled(true);
	}

	/**
	 * 禁用用户
	 */
	@Transactional
	public void disableUser(String userId) {
		User user = getUser(userId);
		user.setEnabled(false);
	}

	@Override
	@Transactional
	@Log(code = "创建部门")
	public void createOrgan(Organ organ) {
		if (!organDao.isUnique(organ, "name")) {
			messageSource.thrown("organ.exist", organ.getName());
		}
		if (organ.getParent() == null) {
			messageSource.thrown("organ.add.no.parent");
		}
		organDao.save(organ);
	}

	/**
	 * 搜索用户。
	 * 
	 * @param searchModel
	 *            搜索条件
	 * @return 返回搜索用户分页对象。
	 */
	@Transactional(readOnly = true)
	public Page<User> searchUser(UserSearchModel searchModel) {
		StringBuilder hql = new StringBuilder(
				"from User u where isDeleted = false and id != '"
						+ AdminIds.USER_ID + "'");
		if (StringUtils.isNotBlank(searchModel.getUsername())) {
			hql.append(" and u.username like '%"
					+ searchModel.getUsername().trim() + "%'");
		}
		if (StringUtils.isNotBlank(searchModel.getName())) {
			hql.append(" and u.name like '%" + searchModel.getName().trim()
					+ "%'");
		}
		if (searchModel.getEnabled() == null || !searchModel.getEnabled()) {
			hql.append(" and enabled = true");
		}
		if (null != searchModel.getNumber()) {
			hql.append(" and u.userNo like '%" + searchModel.getNumber().trim()
					+ "%'");
		}
		hql.append(" order by u.createDate desc");
		return userDao.findPage(hql.toString(), searchModel.getPageNo(),
				searchModel.getPageSize());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> getAllRole() {
		Criteria criteria = roleDao.createCriteria();
		criteria.add(Restrictions.ne("id", AdminIds.ROLE_ID));
		criteria.addOrder(Order.asc("createDate"));
		return criteria.list();
	}

	@Override
	@Transactional
	public void updateOrgan(Organ organ) {
		if (!organDao.isUnique(organ, "name")) {
			messageSource.thrown("organ.exist", organ.getName());
		}
		Organ origOrgan = getOrgan(organ.getId());
		BeanUtils.copyFields(organ, origOrgan, "ordinal", null);
	}

	@Override
	@Transactional
	public void updateRole(Role role) {
		super.updateRole(role);
	}

	/**
	 * 更新用户
	 * 
	 * @param user
	 * @param actor
	 */
	@Transactional
	@Log
	public void updateUser(User user) {
		if (!userDao.isUnique(user, "username,isDeleted")) {
			messageSource.thrown("username.exist", user.getUsername());
		}
		User origUser = getUser(user.getId());
		if (!StringUtils.isEmpty(user.getPassword())) {
			user.setPassword(encryptPassword(user.getPassword()));
		}
		origUser.autoFillIn();
		BeanUtils.copyFields(user, origUser);

	}

	/**
	 * 更新职位
	 */
	@Override
	@Transactional
	public void updateActor(Actor actor) {
		Actor origActor = getActor(actor.getId());
		origActor.setModifier(getCurrentUser());
		origActor.setModifyDate(new Date());
		origActor.setName(actor.getName() == null ? origActor.getName() : actor
				.getName());
		origActor.setOrgan(actor.getOrgan() == null ? origActor.getOrgan()
				: actor.getOrgan());
		origActor.setUser(actor.getUser() == null ? origActor.getUser() : actor
				.getUser());
		origActor.setRole(actor.getRole() == null ? origActor.getRole() : actor
				.getRole());
	}

	/**
	 * 删除角色
	 * 
	 * @param Id
	 */
	@Transactional
	public void deleteRole(String id) {
		Role role = roleDao.get(id);
		if (!StringUtils.isEmpty(getUserNameListStrFromRole(role))) {
			messageSource.thrown("role.delete.failed.user");
		}
		roleDao.remove(role);
	}

	/**
	 * 获取拥有该角色的所有用户
	 * 
	 * @param role
	 * @return
	 */
	public String getUserNameListStrFromRole(Role role) {
		StringBuilder sb = new StringBuilder();
		try {
			List<Actor> actors = role.getActors();
			for (Actor a : actors) {
				try {
					User u = a.getUser();
					if (!u.isDeleted()) {
						sb.append(a.getUser().getUsername());
						sb.append(";");
					}
				} catch (ObjectNotFoundException e) {
					continue;
				}
			}
		} catch (ObjectNotFoundException e) {
			return sb.toString();
		}
		return sb.toString();
	}

	/**
	 * 新建用户
	 */
	@Override
	@Transactional
	public void createUser(User user) {
		if (!userDao.isUnique(user, "userNo,isDeleted")) {
			messageSource.thrown("userno.exist", user.getUserNo());
		}
		if (!userDao.isUnique(user, "username,isDeleted")) {
			messageSource.thrown("username.exist", user.getUsername());
		}
		String password = user.getPassword();
		user.setPassword(StringUtils.hasText(password) ? encryptPassword(password)
				: encryptPassword(AdminPermission.DEFAULT_PASSWORD));
		// 当前登录用户
		Date now = new Date();
		User currentUser = getCurrentUser();
		user.setCreator(currentUser);
		user.setModifier(currentUser);
		user.setCreateDate(now);
		user.setModifyDate(now);
		userDao.save(user);
		UserSettings settings = user.getSettings();
		Actor defaultActor = settings.getDefaultActor();
		defaultActor.setUser(user);
		defaultActor.autoFillIn();
		actorDao.save(defaultActor);

		settings.setId(user.getId());
		userDao.merge(user);
		try {
			userSettingsDao.save(settings);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void createActor(Actor actor) {
		actor.autoFillIn();
		actorDao.save(actor);
	}

	/**
	 * 逻辑删除用户
	 * 
	 * @param user
	 */
	@Transactional
	public void deleteUser(User user) {
		user.setDeleted(true);
		userDao.save(user);
	}

	/**
	 * 删除部门
	 */
	@Transactional
	public void deleteOrgan(String organId) {
		if (AdminIds.ORGAN_ID == organId)
			messageSource.thrown("organ.delete.failed.root");
		Organ organ = getOrgan(organId);
		if (organ.getValidChilds().size() > 0)
			messageSource.thrown("organ.delete.failed.child");
		if (findUserByOrgan(organ))
			messageSource.thrown("organ.delete.success");
		organ.setDeleted(true);
	}

	/**
	 * 查找与机构关联的用户
	 * 
	 * @param organ
	 * @return
	 */
	public boolean findUserByOrgan(Organ organ) {
		try {
			List<Actor> acts = organ.getActors();
			for (Actor a : acts) {
				try {
					if (!a.getUser().isDeleted())
						return true;
				} catch (ObjectNotFoundException e) {// 职务获取关联用户失败表示该职务无关联用户
					continue;
				}
			}
		} catch (ObjectNotFoundException e) {// 获取职务失败表示无关联用户
			return false;
		}
		return false;
	}

	@Transactional(readOnly = true)
	public Organ getAdminOrgan() {
		return organDao.get(AdminIds.ORGAN_ID);
	}

	/**
	 * 获得次级部门中下次递增序号
	 * 
	 * @param organId
	 * @return
	 */
	@Transactional
	public int getNextChildMaxOrdinal(String organId) {
		Object o = organDao.getMax("ordinal",
				Restrictions.eq("parent.id", organId),
				Restrictions.eq("isDeleted", false));

		return o == null ? 0 : (Integer.parseInt(o.toString()) + 1);
	}

	/**
	 * 批量保存角色
	 * 
	 * @param roles
	 */
	@Transactional
	public void saveRoles(List<Role> roles) {
		for (Role entity : roles) {
			entity.autoFillIn();
			createRole(entity);
		}
	}

	@Transactional
	public void createRole(Role role) {
		if (!roleDao.isUnique(role, "name")) {
			messageSource.thrown("role.name.exist", role.getName());
		}
		roleDao.save(role);
	}

	/**
	 * 根据权限查询用户
	 */
	@Transactional(readOnly = true)
	@Override
	public List<User> findUserByPermissions(String[] includePermissionCodes,
			String[] excludePermissionCodes) {
		List<Integer> trueBits = permissionConfig
				.getPermissionIds(includePermissionCodes);
		List<Integer> falseBits = permissionConfig
				.getPermissionIds(excludePermissionCodes);
		BitCode permissionCode = new BitCode(3000).getQueryBitCode(
				trueBits.toArray(new Integer[] {}),
				falseBits.toArray(new Integer[] {}));
		Criteria criteria = userDao.createCriteria();
		criteria.createAlias("actors.role", "role");
		criteria.add(Restrictions.like("role.permissions",
				permissionCode.toString()));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	/**
	 * 根据帐号，员工号查找用户
	 * 
	 * @param username
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<User> findUser(String username) {
		return userDao.createCriteria(
				Restrictions.eq("isDeleted", false),
				Restrictions.or(Restrictions.eq("username", username),
						Restrictions.eq("userNo", username))).list();
	}

	/**
	 * 获取当前用户权限
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Permission> getCurrentUserPermissions() {
		User user = getCurrentUser();
		return permissionConfig.getPermissions(user.getSettings()
				.getDefaultActor().getRole().getPermissions());
	}

	/**
	 * 获取指定用户权限
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Permission> getCurrentUserPermissions(User user) {
		return permissionConfig.getPermissions(user.getSettings()
				.getDefaultActor().getRole().getPermissions());
	}

	public void updateUserPassword(String id, String password) {
		// 修改密码
		Query query = userDao.createQuery("update User s set password = '"
				+ password + "' where id = '" + id + "'");
		query.executeUpdate();
	}
}