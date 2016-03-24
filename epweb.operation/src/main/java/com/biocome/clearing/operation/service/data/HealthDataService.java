package com.biocome.clearing.operation.service.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biocome.base.db.dao.Dao;
import com.biocome.base.exception.BusinessException;
import com.biocome.base.model.Page;
import com.biocome.base.mvc.util.FileHelper;
import com.biocome.base.security.annotations.Log;
import com.biocome.base.security.annotations.Log.LogType;
import com.biocome.base.util.CompressUtil;
import com.biocome.base.util.DateUtils;
import com.biocome.base.util.FileUtils;
import com.biocome.base.util.FtpUtils;
import com.biocome.base.util.StringUtils;
import com.biocome.clearing.core.entity.account.WccUser;
import com.biocome.clearing.core.entity.account.WccUserInfo;
import com.biocome.clearing.core.entity.health.HealthEcg;
import com.biocome.clearing.core.model.data.HealthSearchModel;

@Service
public class HealthDataService {

	@Resource
	Dao<HealthEcg> healthEcgDao;

	@Resource
	Dao<WccUser> wccUserDao;
	@Resource
	Dao<WccUserInfo> wccUserInfoDao;

	public Page<HealthEcg> findPage(HealthSearchModel search) {
		if (search.getBeginDate() == null)
			search.setBeginDate(DateUtils.rollByMonth(new Date(), -1));
		search.setBeginTime(search.getBeginDate().getTime());
		if (search.getEndDate() != null) {
			search.setEndDate(DateUtils.rollSecond(
					DateUtils.rollByDay(search.getEndDate(), 1), -1));
			search.setEndTime(search.getEndDate().getTime());
		}
		String device = search.getBluetooth();
		if (device != null && !device.isEmpty()) {
			if (device.contains(":") || device.matches(".*[a-zA-Z:].*"))
				search.setDeviceId(Long.parseLong(device.replaceAll(":", ""),
						16));
			else
				search.setDeviceId(-1L);
		}
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
		Page<HealthEcg> ecgs = healthEcgDao.findPage(search);
		search.setUserId(userId);
		return ecgs;
	}

	public HealthEcg getEcg(Long id) {
		return healthEcgDao.get(id);
	}

	public String getEcgData(Long id) {
		HealthEcg ecg = getEcg(id);
		if (ecg == null)
			return null;
		else {
			if (ecg.getData() != null && ecg.getData().startsWith("#")) {
				return dealData(ecg.getData().substring(1), true);
			}
			byte[] data = CompressUtil
					.unZip(Base64.decodeBase64(ecg.getData()));
			return dealData(new String(data), true);
		}
	}

	public String getEcgData(HealthEcg ecg) {
		if (ecg == null)
			return null;
		else {
			if (ecg.getData() != null && ecg.getData().startsWith("#")) {
				return dealData(ecg.getData().substring(1), true);
			}
			byte[] data = CompressUtil
					.unZip(Base64.decodeBase64(ecg.getData()));
			System.out.println(new String(data));
			return dealData(new String(data), true);
		}
	}

	public String dealData(String d, boolean hasRate) {
		if (hasRate && d.length() > 0) {
			if (d.endsWith(","))
				d = d.substring(0, d.length() - 1);
			List<String> newstr = new ArrayList<String>(Arrays.asList(d
					.split(",")));
			for (int i = 0; i < newstr.size();) {
				newstr.remove(i);
				i += 125;
			}
			d = newstr.toString();
			d = d.substring(1, d.length() - 1);
		}
		return d.toString();
	}

	@Transactional
	@Log(code = "删除心电数据", type = LogType.ACCOUNT)
	public void delete(Long id) {
		healthEcgDao.remove(id);
	}

	@Transactional
	@Log(code = "修改备注", type = LogType.ACCOUNT)
	public void update(Long id, String remark) {
		healthEcgDao.get(id).setRemark(remark);
	}

	public void exportFile(HealthSearchModel search,
			HttpServletRequest request, HttpServletResponse response) {
		List<HealthEcg> list = findPage(search).getContents();
		if (list.size() == 0)
			throw new BusinessException("无数据导出");
		Set<String> files = new HashSet<String>();
		String tmppath = request.getSession().getServletContext()
				.getRealPath("/");
		String zipfile = tmppath + "/心电数据"
				+ DateUtils.dateToString(new Date(), DateUtils.SECOND_N)
				+ ".zip";
		File zip = new File(zipfile);
		try {
			for (HealthEcg ecg : list) {
				try {
					String folder = tmppath + "/" + ecg.getUser().getMobile();
					String fn = folder
							+ "/"
							+ ecg.getRemark()
							+ "-"
							+ DateUtils.dateToString(ecg.getBeginDate(),
									DateUtils.SECOND_N) + ".txt";
					FileUtils.writeTextToFile(fn, getEcgData(ecg));
					files.add(folder);
				} catch (Exception e) {
					continue;
				}
			}
			FtpUtils.zipEncryptFiles(zipfile, null,
					files.toArray(new String[files.size()]));
			FileHelper.downloadFile(zip, response);
		} catch (Exception e) {
			throw new BusinessException("导出文件失败");
		} finally {
			try {
				for (String f : files) {
					org.apache.commons.io.FileUtils.deleteQuietly(new File(f));
				}
				zip.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
