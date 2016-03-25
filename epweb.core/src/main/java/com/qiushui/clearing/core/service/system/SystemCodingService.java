package com.qiushui.clearing.core.service.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qiushui.base.db.dao.Dao;
import com.qiushui.base.model.Page;
import com.qiushui.base.util.BeanUtils;
import com.qiushui.base.xstream.MessageSource;
import com.qiushui.clearing.core.entity.system.SystemCoding;
import com.qiushui.clearing.core.model.system.CodingSearchModel;

/**
 * 
 * 
 * @author 谷家良
 * @date 2015年5月7日 上午10:41:12
 * @Description: 字典与参数管理
 */
@Service
@SuppressWarnings("unchecked")
public class SystemCodingService {
	@Resource
	private Dao<SystemCoding> systemCodingDao;
	@Resource
	private MessageSource messageSource;

	/**
	 * 分页搜索元素。
	 * 
	 * @param searchModel
	 *            搜索条件
	 * @return 返回符合条件的分页对象。
	 */
	@Transactional(readOnly = true)
	public Page<SystemCoding> searchSystemCoding(CodingSearchModel searchModel) {
		searchModel.setOrderBy("type,codeName,code,disable");
		searchModel.setSort("desc");
		Page<SystemCoding> dictTypes = systemCodingDao.findPage(searchModel);
		return dictTypes;
	}

	/**
	 * 查找所有类型信息 方法用途: <br>
	 * 实现步骤: <br>
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<SystemCoding> getAllSystemCoding() {
		return systemCodingDao.getAll("codeName", true);
	}

	/**
	 * 查找所有类型信息 方法用途: <br>
	 * 实现步骤: <br>
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<SystemCoding> getAllSystemCoding(boolean disable) {
		return systemCodingDao.findBy("disable", disable, "codeName", true);
	}

	/**
	 * 新增具体类型.dictcode非重复
	 * 
	 * @param SystemCoding
	 *            类型信息
	 */
	@Transactional
	public void createSystemCoding(SystemCoding coding) {
		if (systemCodingDao.find(
				Restrictions.or(Restrictions.eq("code", coding.getCode()),
						Restrictions.eq("codeName", coding.getCode())),
				Restrictions.eq("type", coding.getType())).size() > 0) {
			messageSource.thrown("字典编码或名称重复");
		}
		systemCodingDao.save(coding);
	}

	/**
	 * 获取某个指定的字典的名称。
	 * 
	 * @param type
	 *            字典类型
	 * @param code
	 *            字典编码
	 * @return
	 * @description:
	 */
	@Transactional(readOnly = true)
	public String getSystemCodingName(String type, String code) {
		SystemCoding c = systemCodingDao.findUnique(
				Restrictions.eq("type", type), Restrictions.eq("code", code));
		return c == null ? null : c.getCodeName();
	}

	/**
	 * 获取某个指定的字典的值。
	 * 
	 * @param type
	 *            字典类型
	 * @param code
	 *            字典名称
	 * @return
	 * @description:
	 */
	@Transactional(readOnly = true)
	public String getSystemCodingValue(String type, String name) {
		SystemCoding c = systemCodingDao.findUnique(
				Restrictions.eq("type", type),
				Restrictions.eq("codeName", name));
		return c == null ? null : c.getCode();
	}

	/**
	 * 获取某个指定的字典。
	 * 
	 * @param id
	 * @return
	 * @description:
	 */
	@Transactional(readOnly = true)
	public SystemCoding getSystemCodingById(Long id) {
		return systemCodingDao.get(id);
	}

	/**
	 * 获取某个指定的类型下的字典。
	 * 
	 * @param baseDictTypeId
	 *            类型ID
	 * @return 返回某个类型信息。
	 */
	@Transactional(readOnly = true)
	public List<SystemCoding> getSystemCoding(String type) {
		return systemCodingDao.find(Restrictions.eq("type", type));
	}

	/**
	 * 更新具体类型信息。
	 * 
	 * @param SystemCoding
	 *            具体类型信息
	 */
	@Transactional
	public void updateSystemCoding(SystemCoding coding) {
		if (systemCodingDao.find(Restrictions.eq("code", coding.getCode()),
				Restrictions.eq("type", coding.getType())).size() > 1) {
			messageSource.thrown("字典编码重复");
		}
		SystemCoding origSystemCoding = systemCodingDao.get(coding.getId());
		BeanUtils.copyFields(coding, origSystemCoding);
	}

	/**
	 * 删除字典。
	 * 
	 * @param baseDictTypeId
	 *            类型ID
	 */
	@Transactional
	public void deleteSystemCoding(SystemCoding coding) {
		try {
			systemCodingDao.remove(coding);
		} catch (Exception e) {
			messageSource.thrown("删除字典出现异常");
		}
	}

	/**
	 * 删除字典。
	 * 
	 * @param baseDictTypeId
	 *            类型ID
	 */
	@Transactional
	public void deleteSystemCoding(Long id) {
		try {
			systemCodingDao.remove(id);
		} catch (Exception e) {
			messageSource.thrown("删除字典出现异常");
		}
	}

	/**
	 * 启用字典。
	 * 
	 * @param baseDictTypeId
	 *            类型ID
	 */
	@Transactional
	public void enableSystemCoding(Long id) {
		try {
			systemCodingDao.get(id).setDisable(false);
		} catch (Exception e) {
			messageSource.thrown("启用字典出现异常");
		}
	}

	/**
	 * 禁用字典。
	 * 
	 * @param baseDictTypeId
	 *            类型ID
	 */
	@Transactional
	public void disableSystemCoding(Long id) {
		try {
			systemCodingDao.get(id).setDisable(true);
		} catch (Exception e) {
			messageSource.thrown("禁用字典出现异常");
		}
	}

	/**
	 * 获取所有字典类型
	 * 
	 * @return
	 * @description:
	 */
	@Transactional(readOnly = true)
	public List<String> getAllTypes() {
		return systemCodingDao
				.createCriteria()
				.setProjection(
						Projections.distinct(Projections.property("type")))
				.addOrder(Order.asc("type")).list();
	}

	/**
	 * 将各类型字典分组到map中
	 * 
	 * @return
	 * @description:
	 */
	@Transactional(readOnly = true)
	public Map<String, List<SystemCoding>> getAllSystemCodingMap() {
		List<SystemCoding> all = getAllSystemCoding(false);
		Map<String, List<SystemCoding>> map = new HashMap<String, List<SystemCoding>>();
		for (SystemCoding c : all) {
			if (map.containsKey(c.getType())) {
				map.get(c.getType()).add(c);
			} else {
				List<SystemCoding> l = new ArrayList<SystemCoding>();
				l.add(c);
				map.put(c.getType(), l);
			}
		}
		return map;
	}
}
