package com.biocome.clearing.operation.service.business;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biocome.base.db.dao.Dao;
import com.biocome.base.model.Page;
import com.biocome.base.security.annotations.Log;
import com.biocome.base.security.annotations.Log.LogType;
import com.biocome.base.util.StringUtils;
import com.biocome.clearing.operation.entity.business.WccAgent;
import com.biocome.clearing.operation.entity.business.WccQuestion;
import com.biocome.clearing.operation.model.business.BusinessSearchModel;

@Service
public class BusinessService {

	@Resource
	Dao<WccAgent> wccAgentDao;

	@Resource
	Dao<WccQuestion> wccQuestionDao;

	@Transactional(readOnly = true)
	public Page<WccAgent> findAgentPage(BusinessSearchModel model) {
		return wccAgentDao.findPage(model);
	}

	@Transactional(readOnly = true)
	public Page<WccQuestion> findQuestionPage(BusinessSearchModel model) {
		return wccQuestionDao.findPage(model);
	}

	@Transactional(readOnly = true)
	public WccAgent findAgent(Long id) {
		return wccAgentDao.get(id);
	}

	@Transactional(readOnly = true)
	public WccQuestion findQuestion(Long id) {
		return wccQuestionDao.get(id);
	}

	@Transactional
	@Log(code = "保存代理商", type = LogType.NORMAL)
	public void createWccAgent(WccAgent wccAgent) {
		wccAgent.setOperateDate(new Date());
		wccAgentDao.save(wccAgent);
	}

	@Transactional
	@Log(code = "更新代理商", type = LogType.NORMAL)
	public void updateWccAgent(WccAgent agent) {
		agent.setOperateDate(new Date());
		wccAgentDao.save(agent);
	}

	@Transactional
	@Log(code = "编辑问题反馈", type = LogType.NORMAL)
	public void updateQuestion(WccQuestion question) {
		question.setOperateDate(new Date());
		if (StringUtils.hasText(question.getAnswer())) {
			question.setStatus(0);
		} else {
			question.setStatus(1);
		}
		wccQuestionDao.save(question);
	}

	@Transactional
	@Log(code = "删除代理商", type = LogType.NORMAL)
	public void remove(Long id) {
		wccAgentDao.remove(id);
	}
}
