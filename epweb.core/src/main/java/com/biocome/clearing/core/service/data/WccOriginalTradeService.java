package com.biocome.clearing.core.service.data;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biocome.base.db.dao.Dao;
import com.biocome.base.model.Page;
import com.biocome.clearing.core.entity.account.WccOriginalTradeRecord;
import com.biocome.clearing.core.model.data.TradeSearchModel;

@Service
public class WccOriginalTradeService {
	@Resource
	Dao<WccOriginalTradeRecord> wccOriginalTradeRecordDao;

	public Page<WccOriginalTradeRecord> searchOriginalDataPage(
			TradeSearchModel model) {
		return wccOriginalTradeRecordDao.findPage(model);
	}

	@Transactional
	public void batchUpdateOriginalRecord(String sql, List<Object[]> objs) {
		wccOriginalTradeRecordDao.executeBatch(sql, objs);
	}
}
