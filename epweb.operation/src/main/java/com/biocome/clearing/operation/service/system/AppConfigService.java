package com.biocome.clearing.operation.service.system;

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
import com.biocome.cache.RedisCache;
import com.biocome.clearing.operation.entity.system.AppConfig;
import com.biocome.clearing.operation.model.system.AppConfigSearchModel;

/**
 * 
 * 
 * @author 谷家良
 * @date 2015年8月10日 下午7:51:34
 * @Description: 应用配置管理
 */
@Service
public class AppConfigService {

	@Resource
	Dao<AppConfig> appConfigDao;
	private static final String APP_TOKEN_CACHE = "APP_TOKEN_CACHE";

	public Page<AppConfig> searchAppConfig(AppConfigSearchModel searchModel) {
		return appConfigDao.findPage(searchModel);
	}

	public List<AppConfig> getAllEnableAppConfig() {
		return appConfigDao.find(Restrictions.eq("disable", false));
	}

	@Transactional
	@Log(code = "添加应用配置", type = LogType.NORMAL)
	public void createAppConfig(AppConfig coding) {
		if (!appConfigDao.isUnique(coding, "name"))
			throw new BusinessException("应用名重复");
		if (!appConfigDao.isUnique(coding, "secret"))
			throw new BusinessException("密钥重复");
		appConfigDao.save(coding);
	}

	public AppConfig getAppConfigById(Long id) {
		return appConfigDao.get(id);
	}

	public AppConfig getAppConfigByName(String name) {
		return appConfigDao.findUnique(Restrictions.eq("name", name));
	}

	@Transactional
	@Log(code = "禁用应用配置", type = LogType.NORMAL)
	public void disableAppConfig(Long id) {
		AppConfig app = appConfigDao.get(id);
		app.setDisable(true);
		RedisCache.getInstance().removeSetCache(APP_TOKEN_CACHE + app.getId());
	}

	@Transactional
	@Log(code = "启用应用配置", type = LogType.NORMAL)
	public void enableAppConfig(Long id) {
		AppConfig app = appConfigDao.get(id);
		app.setDisable(false);
	}

	@Transactional
	@Log(code = "更新应用配置", type = LogType.NORMAL)
	public void updateAppConfig(AppConfig coding) {
		AppConfig old = appConfigDao.get(coding.getId());
		if (!appConfigDao.isUnique(coding, "name"))
			throw new BusinessException("应用名重复");
		if (!appConfigDao.isUnique(coding, "secret"))
			throw new BusinessException("密钥重复");
		if (!old.getSecret().equals(coding.getSecret())) {
			RedisCache.getInstance().removeSetCache(
					APP_TOKEN_CACHE + old.getId());
		}
		BeanUtils.copyFields(coding, old);
	}

}
