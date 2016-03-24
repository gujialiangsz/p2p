package com.biocome.clearing.operation.service.account;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biocome.base.db.dao.Dao;
import com.biocome.base.exception.BusinessException;
import com.biocome.base.model.Page;
import com.biocome.base.security.annotations.Log;
import com.biocome.base.security.annotations.Log.LogType;
import com.biocome.base.util.BeanUtils;
import com.biocome.clearing.core.entity.account.WccFileAnalysisConfigList;
import com.biocome.clearing.core.entity.account.WccFileAnalysisConfigListId;
import com.biocome.clearing.core.entity.account.WccFileAnalysisConfigMain;
import com.biocome.clearing.core.entity.account.WccFileAnalysisConfigMainId;
import com.biocome.clearing.operation.model.system.AnalysisConfigSearchModel;

/**
 * 
 * 
 * @author 谷家良
 * @date 2015年8月10日 下午7:50:50
 * @Description: 解析配置
 */
@Service
public class WccFileAnalysisConfigService {

	@Resource
	Dao<WccFileAnalysisConfigMain> wccFileAnalysisConfigMainDao;
	@Resource
	Dao<WccFileAnalysisConfigList> wccFileAnalysisConfigListDao;

	public Page<WccFileAnalysisConfigMain> searchMainConfig(
			AnalysisConfigSearchModel model) {
		return wccFileAnalysisConfigMainDao.findPage(model);
	}

	public Page<WccFileAnalysisConfigList> searchConfigListPage(
			AnalysisConfigSearchModel model) {
		return wccFileAnalysisConfigListDao.findPage(model);
	}

	public List<WccFileAnalysisConfigMain> getAllMainConfig() {
		return wccFileAnalysisConfigMainDao.getAll();
	}

	/**
	 * 获取配置明细列表
	 * 
	 * @param mainId
	 * @return
	 * @description:
	 */
	public List<WccFileAnalysisConfigList> getConfigLists(
			WccFileAnalysisConfigMainId mainId) {
		return wccFileAnalysisConfigListDao.find(
				Restrictions.eq("sourceType", mainId.getSourceType()),
				Restrictions.eq("cityCode", mainId.getCityCode()));
	}

	@Transactional
	@Log(code = "添加对账解析主配置", type = LogType.ACCOUNT)
	public void addConfigMain(WccFileAnalysisConfigMain main) {
		if (wccFileAnalysisConfigMainDao.get(main.getId()) != null)
			throw new BusinessException("主配置已存在，无法添加");
		wccFileAnalysisConfigMainDao.save(main);
	}

	@Transactional
	@Log(code = "更新对账解析主配置", type = LogType.ACCOUNT)
	public void updateConfigMain(WccFileAnalysisConfigMain main) {
		WccFileAnalysisConfigMain oldmain = wccFileAnalysisConfigMainDao
				.get(main.getId());
		if (oldmain == null)
			throw new BusinessException("主配置不存在，无法更新");
		BeanUtils.copyFields(main, oldmain);
	}

	@Transactional
	@Log(code = "删除对账解析主配置", type = LogType.ACCOUNT)
	public void removeConfigMain(WccFileAnalysisConfigMainId id) {
		wccFileAnalysisConfigMainDao.remove(id);
	}

	@Transactional
	@Log(code = "更新对账解析配置明细", type = LogType.ACCOUNT)
	public void updateConfigList(WccFileAnalysisConfigList list) {
		WccFileAnalysisConfigList oldlist = wccFileAnalysisConfigListDao
				.get(list.getId());
		if (oldlist == null)
			throw new BusinessException("配置明细不存在，无法更新");
		BeanUtils.copyFields(list, oldlist);
	}

	@Transactional
	@Log(code = "添加对账解析配置明细", type = LogType.ACCOUNT)
	public void addConfigList(WccFileAnalysisConfigList list) {
		if (wccFileAnalysisConfigListDao.get(list.getId()) != null)
			throw new BusinessException("配置明细已存在，无法添加");
		wccFileAnalysisConfigListDao.save(list);
	}

	@Transactional
	@Log(code = "添加对账解析配置明细", type = LogType.ACCOUNT)
	public void addConfigLists(List<WccFileAnalysisConfigList> lists) {
		for (WccFileAnalysisConfigList list : lists) {
			wccFileAnalysisConfigListDao.save(list);
		}
	}

	@Transactional
	@Log(code = "删除对账解析配置明细", type = LogType.ACCOUNT)
	public void removeConfigList(WccFileAnalysisConfigListId id) {
		wccFileAnalysisConfigListDao.remove(id);
	}

	public WccFileAnalysisConfigMain getConfigMain(
			WccFileAnalysisConfigMainId id) {
		return wccFileAnalysisConfigMainDao.get(id);
	}

	public WccFileAnalysisConfigList getConfigList(
			WccFileAnalysisConfigListId id) {
		return wccFileAnalysisConfigListDao.get(id);
	}
}
