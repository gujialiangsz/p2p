package com.biocome.clearing.operation.service.system;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biocome.base.constants.Chars;
import com.biocome.base.db.dao.Dao;
import com.biocome.base.exception.BusinessException;
import com.biocome.base.model.Page;
import com.biocome.base.model.SearchModel;
import com.biocome.base.mvc.util.qiniu.CloudUtil;
import com.biocome.base.security.annotations.Log;
import com.biocome.base.security.annotations.Log.LogType;
import com.biocome.base.util.BeanUtils;
import com.biocome.base.util.CryptUtils;
import com.biocome.base.util.FileUtils;
import com.biocome.base.util.StringUtils;
import com.biocome.clearing.operation.entity.system.AppVersion;
import com.qiniu.util.StringMap;

/**
 * 
 * 
 * @author 谷家良
 * @date 2015年8月10日 下午7:52:21
 * @Description: 应用版本管理
 */
@Service
public class AppVersionService {
	// 应用名校验格式
	@Value("#{settings['file.valid']}")
	private String versioncheck;
	// 应用名称示例
	@Value("#{settings['file.case']}")
	private String filecase;
	// 应用名最新版本
	@Value("#{settings['file.last']}")
	private String filelast;
	@Resource
	Dao<AppVersion> appVersionDao;
	private static Logger LOGGER = LoggerFactory
			.getLogger(AppVersionService.class);

	public Page<AppVersion> getPage(SearchModel model) {
		return appVersionDao.findPage(model);
	}

	@Transactional
	@Log(code = "新增应用版本", type = LogType.NORMAL)
	public void save(AppVersion version) {
		if (!appVersionDao.isUnique(version, "appname")) {
			LOGGER.error("版本名称重复：" + version.getAppname());
			throw new BusinessException("版本名称重复");
		}
		appVersionDao.save(version);
	}

	@Transactional
	@Log(code = "更新应用版本", type = LogType.NORMAL)
	public void update(AppVersion version) {
		AppVersion old = appVersionDao.get(version.getId());
		if (!old.getAppname().equals(version.getAppname())) {
			LOGGER.error("文件名称不匹配：" + version.getAppname());
			throw new BusinessException("文件名不匹配");
		}
		BeanUtils.copyFields(version, old,
				"fromversion,imei,firmwareversion,versioninfo", null);
	}

	@Transactional
	@Log(code = "删除应用版本", type = LogType.NORMAL)
	public AppVersion delete(Long id) {
		try {
			AppVersion version = appVersionDao.get(id);
			appVersionDao.remove(version);
			CloudUtil.deleteFile(version.getAppname());
			return version;
		} catch (Exception e) {
			LOGGER.error("删除应用失败：" + e.getMessage());
			throw new BusinessException("删除应用版本失败");
		}
	}

	public List<AppVersion> getAllVersion() {
		return appVersionDao.getAll();
	}

	public AppVersion getVersion(Long id) {
		return appVersionDao.get(id);
	}

	/**
	 * 更新最新版本
	 * 
	 * @description:
	 */
	public void changeLastVersion(String type) {
		try {
			if (type != null && type.equals("APK")) {
				String max = getMaxApkVersionCode();
				if (max != null) {
					AppVersion version = appVersionDao.findFirst("versioncode",
							max, "id", false);
					if (version != null) {
						// CloudUtil.deleteFile(filelast);
						CloudUtil.copyFile(version.getAppname(), filelast);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("更新最新版本失败:" + e.fillInStackTrace());
		}
	}

	public String getMaxApkVersionCode() {
		return appVersionDao.getMax("versioncode",
				Restrictions.eq("type", "APK"));
	}

	public AppVersion analysisFile(byte[] bytes, String originalFilename) {
		String fn = FileUtils.getFullFileName(originalFilename);
		if (fn == null || !StringUtils.isMatch(versioncheck, fn)) {
			LOGGER.error("文件名格式错误，参考格式:" + filecase);
			throw new BusinessException("文件名格式错误，参考格式:" + filecase);
		}
		String[] ss = fn.split(Chars.UNDERLINE);
		AppVersion version = new AppVersion();
		version.setAppname(fn);
		version.setVersioncode(ss.length > 2 ? ss[2].substring(0,
				ss[2].lastIndexOf(".")) : null);
		version.setVersionname(ss[1]);
		StringMap map = CloudUtil.upload(bytes, fn);
		version.setMd5(CryptUtils.getFileMD5(bytes));
		version.setUrl((String) map.get("url"));
		return version;
	}

}