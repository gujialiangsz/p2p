package com.biocome.clearing.operation.service.system;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biocome.base.db.dao.Dao;
import com.biocome.base.exception.BusinessException;
import com.biocome.base.model.Page;
import com.biocome.base.security.annotations.Log;
import com.biocome.base.security.annotations.Log.LogType;
import com.biocome.base.util.BeanUtils;
import com.biocome.base.util.JSonUtils;
import com.biocome.base.util.NetUtils;
import com.biocome.clearing.operation.entity.system.AppConfig;
import com.biocome.clearing.operation.entity.system.InterfaceConfigList;
import com.biocome.clearing.operation.entity.system.InterfaceConfigMain;
import com.biocome.clearing.operation.entity.system.InterfaceGroup;
import com.biocome.clearing.operation.model.system.InterfaceConfigSearchModel;

@Service
public class InterfaceConfigService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(InterfaceConfigService.class);
	@Resource
	Dao<InterfaceGroup> interfaceGroupDao;

	@Resource
	Dao<InterfaceConfigMain> interfaceConfigMainDao;

	@Resource
	Dao<InterfaceConfigList> interfaceConfigListDao;

	@Resource
	AppConfigService appConfigService;

	@Resource
	SecurityService securityService;
	@Value("#{settings['interface.configUrl']}")
	private String ifConfigUrl;
	@Value("#{settings['interface.tokenUrl']}")
	private String tokenConfigUrl;
	@Value("#{settings['oss.Url']}")
	private String ossUrl;
	private String token;

	public Page<InterfaceGroup> searchGroup(InterfaceConfigSearchModel model) {
		return interfaceGroupDao.findPage(model);
	}

	public Page<InterfaceConfigMain> searchMainConfig(
			InterfaceConfigSearchModel model) {
		return interfaceConfigMainDao.findPage(model);
	}

	public Page<InterfaceConfigList> searchConfigListPage(
			InterfaceConfigSearchModel model) {
		return interfaceConfigListDao.findPage(model);
	}

	public List<InterfaceGroup> getAllGroup() {
		return interfaceGroupDao.getAll();
	}

	/**
	 * 获取指定名称接口
	 * 
	 * @param groupId
	 * @return
	 * @description:
	 */
	public List<InterfaceConfigMain> getMainConfig(String name) {
		return interfaceConfigMainDao.find(Restrictions.eq("text", name));
	}

	/**
	 * 获取所有接口
	 * 
	 * @param groupId
	 * @return
	 * @description:
	 */
	public List<InterfaceConfigMain> getAllMainConfig() {
		return interfaceConfigMainDao.getAll();
	}

	/**
	 * 获取组下所有接口
	 * 
	 * @param groupId
	 * @return
	 * @description:
	 */
	public List<InterfaceConfigMain> getAllMainConfig(long groupId) {
		return interfaceConfigMainDao
				.find(Restrictions.eq("group.id", groupId));
	}

	/**
	 * 获取接口参数明细列表
	 * 
	 * @param mainId
	 * @return
	 * @description:
	 */
	public List<InterfaceConfigList> getConfigLists(long interfaceId) {
		return interfaceConfigListDao.find(Restrictions.eq("mainConfig.id",
				interfaceId));
	}

	@Transactional
	@Log(code = "添加接口组", type = LogType.NORMAL)
	public void addGroup(InterfaceGroup group) {
		if (!interfaceGroupDao.isUnique(group, "name"))
			throw new BusinessException("接口组已存在，无法添加");
		interfaceGroupDao.save(group);
	}

	@Log(code = "添加接口主配置", type = LogType.NORMAL)
	@Transactional
	public void addConfigMain(InterfaceConfigMain main) {
		if (!interfaceConfigMainDao.isUnique(main, "name"))
			throw new BusinessException("接口主配置已存在，无法添加");
		Date date = new Date();
		main.setCreatetime(date);
		main.setUpdatetime(date);
		main.setWriters(securityService.getCurrentUser());
		// main.getGroup().setUpdatetime(date);
		interfaceConfigMainDao.save(main);
	}

	@Log(code = "添加接口参数配置明细", type = LogType.NORMAL)
	@Transactional
	public String addConfigList(InterfaceConfigList list) {
		if (list.getReg() != null) {
			try {
				Pattern.compile(list.getReg());
			} catch (Exception e) {
				throw new BusinessException("参数正则表达式错误，无法添加");
			}
		}
		if (!interfaceConfigListDao
				.isUnique(list, "mainConfig.id,name,species"))
			throw new BusinessException("参数配置已存在，无法添加");
		InterfaceConfigMain main = interfaceConfigMainDao.get(list
				.getMainConfig().getId());
		Date date = new Date();
		list.setCreatetime(date);
		list.setUpdatetime(date);
		main.setUpdatetime(date);
		main.setVersion(main.getVersion() + 1);
		main.setWriters(securityService.getCurrentUser());
		// list.getMainConfig().getGroup().setUpdatetime(date);
		String name = main.getText();
		interfaceConfigListDao.save(list);
		return name;
	}

	@Transactional
	@Log(code = "批量添加接口参数配置明细", type = LogType.NORMAL)
	public void addConfigLists(List<InterfaceConfigList> lists) {
		for (InterfaceConfigList list : lists) {
			addConfigList(list);
		}
	}

	@Transactional
	@Log(code = "更新接口组配置", type = LogType.NORMAL)
	public void updateGroup(InterfaceGroup main) {
		Date date = new Date();
		main.setUpdatetime(date);
		InterfaceGroup oldmain = interfaceGroupDao.get(main.getId());
		if (oldmain == null)
			throw new BusinessException("接口组配置不存在，无法更新");
		if (!interfaceGroupDao.isUnique(main, "name"))
			throw new BusinessException("接口组名称重复，更新失败");
		BeanUtils.copyFields(main, oldmain);
	}

	@Log(code = "更新接口主配置", type = LogType.NORMAL)
	@Transactional
	public void updateConfigMain(InterfaceConfigMain main) {
		Date date = new Date();
		main.setUpdatetime(date);
		main.setWriters(securityService.getCurrentUser());
		main.setVersion(main.getVersion() + 1);
		// main.getGroup().setUpdatetime(date);
		InterfaceConfigMain oldmain = interfaceConfigMainDao.get(main.getId());
		if (oldmain == null)
			throw new BusinessException("接口主配置不存在，无法更新");
		BeanUtils.copyFields(main, oldmain);
	}

	@Log(code = "更新接口参数配置明细", type = LogType.NORMAL)
	@Transactional
	public String updateConfigList(InterfaceConfigList list) {
		InterfaceConfigList oldlist = interfaceConfigListDao.get(list.getId());
		if (list.getReg() != null) {
			try {
				Pattern.compile(list.getReg());
			} catch (Exception e) {
				throw new BusinessException("参数正则表达式错误，无法更新");
			}
		}
		if (oldlist == null)
			throw new BusinessException("接口参数明细不存在，无法更新");
		if (!interfaceConfigListDao
				.isUnique(list, "mainConfig.id,name,species"))
			throw new BusinessException("参数配置已存在，无法添加");
		InterfaceConfigMain main = interfaceConfigMainDao.get(list
				.getMainConfig().getId());
		Date date = new Date();
		list.setCreatetime(date);
		list.setUpdatetime(date);
		main.setUpdatetime(date);
		main.setVersion(main.getVersion() + 1);
		main.setWriters(securityService.getCurrentUser());
		String name = main.getText();
		System.out.println(name);
		BeanUtils.copyFields(list, oldlist);
		return name;
	}

	@Log(code = "删除接口组配置", type = LogType.NORMAL)
	@Transactional
	public List<InterfaceConfigMain> removeGroup(long id) {
		InterfaceGroup group = interfaceGroupDao.get(id);
		List<InterfaceConfigMain> list = group.getMainList();
		interfaceGroupDao.remove(group);
		return list;
	}

	@Log(code = "删除接口主配置", type = LogType.NORMAL)
	@Transactional
	public String removeConfigMain(long id) {
		InterfaceConfigMain main = interfaceConfigMainDao.get(id);
		String name = main.getText();
		interfaceConfigMainDao.remove(main);
		return name;
	}

	@Log(code = "删除对账解析配置明细", type = LogType.NORMAL)
	@Transactional
	public String removeConfigList(long id) {
		InterfaceConfigList list = interfaceConfigListDao.get(id);
		InterfaceConfigMain main = interfaceConfigMainDao.get(list
				.getMainConfig().getId());
		Date date = new Date();
		main.setUpdatetime(date);
		main.setVersion(main.getVersion() + 1);
		main.setWriters(securityService.getCurrentUser());
		String name = main.getText();
		interfaceConfigListDao.remove(list);
		return name;
	}

	public InterfaceGroup getGroup(long id) {
		return interfaceGroupDao.get(id);
	}

	public InterfaceConfigMain getConfigMain(long id) {
		return interfaceConfigMainDao.get(id);
	}

	public InterfaceConfigList getConfigList(long id) {
		return interfaceConfigListDao.get(id);
	}

	/**************** 分割线 *******************/
	public void notify(InterfaceConfigMain main, String type) {
		pushUpdateInfo(main.getText(), type, 1);
	}

	public void notify(String name, String type) {
		pushUpdateInfo(name, type, 1);
	}

	public void notify(List<InterfaceConfigMain> mains, String type) {
		for (InterfaceConfigMain main : mains) {
			pushUpdateInfo(main.getText(), type, 1);
		}
	}

	/**
	 * 接口变更信息推送
	 * 
	 * @param name
	 *            接口名称，text
	 * @param type
	 *            变更类型，add，update，remove
	 * @param retryTimes
	 * @description:
	 */
	public void pushUpdateInfo(String name, String type, int retryTimes) {
		try {
			if (retryTimes > 0) {
				retryTimes--;
				int code = (int) JSonUtils.get(
						NetUtils.sendPost(ifConfigUrl, "token="
								+ getToken(false) + "&name=" + name + "&type="
								+ type + "&url=" + ossUrl), "code");
				if (code == 3) {
					LOGGER.error("token错误");
					getToken(true);
					pushUpdateInfo(name, type, retryTimes);
				} else if (code == 0) {
					LOGGER.info("接口变更信息推送成功");
				} else {
					LOGGER.error("接口变更信息推送失败，错误码：" + code);
					pushUpdateInfo(name, type, retryTimes);
				}
			} else {
				LOGGER.error("推送尝试次数达到上限");
			}
		} catch (Exception e) {
			LOGGER.error("接口变更信息推送失败，错误信息：" + e.getMessage());
		}

	}

	/**
	 * 获取通知token
	 * 
	 * @param force
	 * @return
	 * @description:
	 */
	public String getToken(boolean force) {
		try {
			if (token == null || force) {
				AppConfig app = appConfigService.getAppConfigByName("woss");
				long appID = app.getId();
				String secret = app.getSecret();
				token = (String) JSonUtils.get(
						NetUtils.sendPost(tokenConfigUrl, "appID=" + appID
								+ "&secret=" + secret), "token");
			}
		} catch (Exception e) {
			LOGGER.error("获取token失败，错误信息：" + e.getMessage());
		}
		return token;
	}

}
