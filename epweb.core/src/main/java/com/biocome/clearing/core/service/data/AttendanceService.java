package com.biocome.clearing.core.service.data;

import javax.annotation.Resource;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.biocome.base.db.dao.Dao;
import com.biocome.base.model.Page;
import com.biocome.base.util.StringUtils;
import com.biocome.clearing.core.entity.account.AttendanceRecord;
import com.biocome.clearing.core.entity.account.WccUser;
import com.biocome.clearing.core.model.data.AttendanceSearchModel;

@Service
public class AttendanceService {

	@Resource
	Dao<AttendanceRecord> attendanceRecordDao;
	@Resource
	Dao<WccUser> wccUserDao;

	public Page<AttendanceRecord> searchPage(AttendanceSearchModel model) {
		Long userId = model.getUid();
		if (!StringUtils.isEmpty(userId)) {
			WccUser user = wccUserDao.findUnique(Restrictions.eq("mobile",
					model.getUid() + ""));
			if (user != null)
				model.setUid(user.getId());
		}
		Page<AttendanceRecord> page = attendanceRecordDao.findPage(model);
		model.setUid(userId);
		return page;
	}
}
