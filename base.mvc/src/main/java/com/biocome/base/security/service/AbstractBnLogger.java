package com.biocome.base.security.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.biocome.base.db.dao.Dao;
import com.biocome.base.model.Page;
import com.biocome.base.model.SearchModel;
import com.biocome.base.security.entity.BnLogEntity;
import com.biocome.base.security.entity.UserEntity;

/**
 * 业务日志组件抽象基类。
 * 
 * @param <T>
 *            业务日志类型
 */
public abstract class AbstractBnLogger<T extends BnLogEntity> {
	@Resource
	private AbstractSecurityService<?, ? extends UserEntity<?, ?, ?>, ?, ?, ?> securityService;
	@Resource
	protected Dao<T> bnLogDao;

	/**
	 * 创建业务日志对象。
	 * 
	 * @return 返回业务日志对象。
	 */
	public abstract T newBnLog();

	/**
	 * 记录普通日志。
	 * 
	 * @param message
	 *            日志信息
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void log(String message) {
		log(securityService.getCurrentUser(), message);
	}

	/**
	 * 记录普通日志，登录时用。
	 * 
	 * @param username
	 *            操作人的用户名
	 * @param message
	 *            日志信息
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void log(UserEntity<?, ?, ?> user, String message) {
		T bnLog = newBnLog();
		bnLog.setMessage(message);
		bnLog.setOperatorId(user.getUsername());
		bnLog.setOperatorName(user.getName());
		bnLog.setOperateTime(new Date());
		bnLogDao.save(bnLog);
	}

	/**
	 * 记录高级日志。
	 * 
	 * @param bnLog
	 *            日志对象
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void log(BnLogEntity bnLog) {
		bnLog.setOperateTime(new Date());
		bnLogDao.save((T) bnLog);
	}

	/**
	 * 分页全文搜索日志记录。
	 * 
	 * @param searchModel
	 *            搜索条件
	 * @return 返回符合条件的日志分页对象。
	 */
	@Transactional(readOnly = true)
	public Page<T> searchLog(SearchModel searchModel) {
		searchModel.setOrderBy("createDate");
		searchModel.setSort("DESC");
		return bnLogDao.findPage(searchModel);
	}

	/**
	 * 获取指定ID的日志记录。
	 * 
	 * @param logId
	 *            日志ID
	 * @return 返回指定ID的日志记录。
	 */
	@Transactional(readOnly = true)
	public T getLog(String logId) {
		return bnLogDao.get(logId);
	}

	public void fill(BnLogEntity bnLog) {
		try {
			if (null == bnLog.getOperatorId()) {
				UserEntity<?, ?, ?> user = securityService.getCurrentUser();
				if (user != null) {
					bnLog.setOperatorId(user.getUsername());
					bnLog.setOperatorName(user.getName());
					bnLog.setIp(securityService.getHost());
				}
			}
		} catch (Exception e) {
		}
	}
}
