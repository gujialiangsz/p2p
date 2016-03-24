package com.biocome.clearing.operation.service.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biocome.base.db.dao.Dao;
import com.biocome.base.db.dao.Dao.InsertMode;
import com.biocome.base.exception.BusinessException;
import com.biocome.base.model.Page;
import com.biocome.base.security.annotations.Log;
import com.biocome.base.security.annotations.Log.LogType;
import com.biocome.base.util.StringUtils;
import com.biocome.clearing.core.entity.account.AttendanceDevice;
import com.biocome.clearing.core.entity.account.WccDevice;
import com.biocome.clearing.core.entity.account.WccUser;
import com.biocome.clearing.core.entity.account.WccUserInfo;
import com.biocome.clearing.operation.model.data.BaseDataSearchModel;

@Service
public class BaseDataService {
	@Resource
	Dao<WccUserInfo> wccUserInfoDao;
	@Resource
	Dao<WccDevice> wccDeviceDao;
	@Resource
	Dao<WccUser> wccUserDao;
	@Resource
	Dao<AttendanceDevice> attendanceDeviceDao;

	@Transactional(readOnly = true)
	public WccUser getUser(Long id) {
		return wccUserDao.get(id);
	}

	public Page<WccDevice> findDevicePage(BaseDataSearchModel search) {
		// 按手机号或账号查询
		Long userId = search.getUserId();
		if (!StringUtils.isEmpty(userId)) {
			WccUser user = wccUserDao.findUnique(Restrictions.or(
					Restrictions.eq("mobile", userId + ""),
					Restrictions.eq("email", userId + "")));
			if (user != null)
				search.setUserId(user.getId());
			else
				search.setUserId(-1L);
		}
		String name = search.getNickname();
		if (!StringUtils.isEmpty(name)) {
			WccUserInfo user = wccUserInfoDao.findUnique(Restrictions
					.or(Restrictions.eq("nickname", name)));
			if (user != null)
				search.setUserId(user.getUser_id());
			else
				search.setUserId(-1L);
		}
		Page<WccDevice> devices = wccDeviceDao.findPage(search);
		search.setUserId(userId);
		return devices;
	}

	public WccDevice getDevice(Long id) {
		return wccDeviceDao.get(id);
	}

	@Log(type = LogType.ACCOUNT, code = "添加设备信息")
	@Transactional
	public void saveDevice(WccDevice device) {
		if (!wccDeviceDao.isUnique(device, "bluetooth")) {
			throw new BusinessException("设备已存在");
		}
		if (device.getId() == null || device.getId() == 0) {
			device.setId(Long.parseLong(device.getBluetooth().replace(":", ""),
					16));
		}
		wccDeviceDao.save(device);
	}

	@Log(type = LogType.ACCOUNT, code = "修改设备信息")
	@Transactional
	public void updateDevice(WccDevice device) {
		wccDeviceDao.save(device);
	}

	@Log(type = LogType.ACCOUNT, code = "删除设备")
	@Transactional
	public void delDevice(Long id) {
		wccDeviceDao.remove(id);
	}

	@Log(type = LogType.ACCOUNT, code = "导入设备信息文件", target = 1)
	@Transactional
	public void analysisFile(InputStream is, String originalFilename) {
		try {
			List<String> data = IOUtils.readLines(is);
			List<WccDevice> wds = new ArrayList<WccDevice>();
			for (String d : data) {
				String[] t = d.split("\t");
				if (!StringUtils.hasText(t[0])
						|| !Pattern.compile("[0-9a-zA-Z:]{12,20}")
								.matcher(t[0]).matches())
					continue;
				WccDevice wd = new WccDevice();
				wd.setBluetooth(t[0]);
				wd.setId(Long.parseLong(t[0].replace(":", ""), 16));
				wd.setDeviceSign(t[1]);
				wd.setSecretKey(t[2]);
				wds.add(wd);
			}
			wccDeviceDao.insert(wds, InsertMode.REPLACE);
		} catch (IOException e) {
			throw new BusinessException("读取文件失败");
		}
	}

	@Transactional
	@Log(code = "解绑设备", type = LogType.ACCOUNT)
	public void unbindDevice(Long id) {
		WccDevice device = wccDeviceDao.get(id);
		if (device == null) {
			throw new BusinessException("设备不存在");
		} else {
			device.setUser(null);
			wccDeviceDao.save(device);
		}
	}

	public AttendanceDevice getAttendanceDevice(Long id) {
		return attendanceDeviceDao.get(id);
	}

	@Transactional
	@Log(code = "保存考勤设备", type = LogType.ACCOUNT)
	public void saveAttendanceDevice(AttendanceDevice device) {
		if (!attendanceDeviceDao.isUnique(device, "sn")) {
			throw new BusinessException("设备已存在");
		}
		attendanceDeviceDao.save(device);
	}

	@Transactional
	@Log(code = "更新考勤设备", type = LogType.ACCOUNT)
	public void updateAttendanceDevice(AttendanceDevice device) {
		if (!attendanceDeviceDao.isUnique(device, "sn")) {
			throw new BusinessException("设备已存在");
		}
		attendanceDeviceDao.save(device);
	}

	@Transactional
	@Log(code = "启用考勤设备", type = LogType.ACCOUNT)
	public void enabledAttendanceDevice(Long id) {
		AttendanceDevice device = attendanceDeviceDao.get(id);
		device.setEnabled(true);
	}

	@Transactional
	@Log(code = "停用考勤设备", type = LogType.ACCOUNT)
	public void disabledAttendanceDevice(Long id) {
		AttendanceDevice device = attendanceDeviceDao.get(id);
		device.setEnabled(false);
	}

	public List<AttendanceDevice> getAllAttendanceDevices(boolean enabled) {
		return attendanceDeviceDao.findBy("enabled", enabled);
	}

	public Page<AttendanceDevice> findAttendanceDevicePage(
			BaseDataSearchModel search) {
		return attendanceDeviceDao.findPage(search);
	}
}
