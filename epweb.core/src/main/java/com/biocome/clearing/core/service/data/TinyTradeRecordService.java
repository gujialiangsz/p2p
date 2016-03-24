package com.biocome.clearing.core.service.data;

import javax.annotation.Resource;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.biocome.base.db.dao.Dao;
import com.biocome.base.model.Page;
import com.biocome.base.util.StringUtils;
import com.biocome.clearing.core.entity.account.TinyTradeRecordList;
import com.biocome.clearing.core.entity.account.WccUser;
import com.biocome.clearing.core.model.data.TradeSearchModel;

/**
 * 
 * 
 * @author 谷家良
 * @date 2015年8月10日 下午7:56:26
 * @Description: 交易明细记录管理
 */
@Service
public class TinyTradeRecordService {
	@Resource
	private Dao<TinyTradeRecordList> tinyTradeRecordListDao;
	@Resource
	private Dao<WccUser> wccUserDao;

	public Page<TinyTradeRecordList> searchConsumePage(TradeSearchModel model) {
		String userId = model.getUserId();
		if (!StringUtils.isEmpty(userId)) {
			WccUser user = wccUserDao.findUnique(Restrictions.or(
					Restrictions.eq("mobile", model.getUserId()),
					Restrictions.eq("email", model.getUserId())));
			if (user != null)
				model.setUserId(user.getId() + "");
		}
		Page<TinyTradeRecordList> result = tinyTradeRecordListDao
				.findPage(model);
		model.setUserId(userId);
		return result;
	}

	public Page<TinyTradeRecordList> searchRechargePage(TradeSearchModel model) {
		String userId = model.getUserId();
		if (!StringUtils.isEmpty(userId)) {
			WccUser user = wccUserDao.findUnique(Restrictions.or(
					Restrictions.eq("mobile", model.getUserId()),
					Restrictions.eq("email", model.getUserId())));
			if (user != null)
				model.setUserId(user.getId() + "");
		}
		Page<TinyTradeRecordList> result = tinyTradeRecordListDao
				.findPage(model);
		model.setUserId(userId);
		return result;
	}
}
