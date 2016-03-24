package com.biocome.clearing.core.service.data;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biocome.base.db.dao.Dao;
import com.biocome.base.model.Page;
import com.biocome.base.model.SearchModel;
import com.biocome.clearing.core.entity.account.WccUser;

@Service
public class WccUserService {

	@Resource
	Dao<WccUser> wccUserDao;

	@Transactional(readOnly = true)
	public Page<WccUser> findPage(SearchModel model) {
		return wccUserDao.findPage(model);
	}
}
