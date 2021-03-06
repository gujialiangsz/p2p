package com.qiushui.clearing.operation.service.system;

import org.springframework.stereotype.Service;

import com.qiushui.base.model.Page;
import com.qiushui.base.security.service.AbstractBnLogger;
import com.qiushui.clearing.operation.entity.system.BnLog;
import com.qiushui.clearing.operation.model.system.LogSearchModel;

/**
 * 业务日志组件。
 */
@Service
public class BnLogger extends AbstractBnLogger<BnLog> {
	@Override
	public BnLog newBnLog() {
		return new BnLog();
	}

	public Page<BnLog> searchLogPage(LogSearchModel model) {
		return bnLogDao.findPage(model);
	}
}