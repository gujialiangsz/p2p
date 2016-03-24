package com.biocome.clearing.operation.service.system;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biocome.base.db.dao.Dao;
import com.biocome.base.exception.BusinessException;
import com.biocome.base.model.Page;
import com.biocome.base.model.SearchModel;
import com.biocome.base.security.annotations.Log;
import com.biocome.base.security.annotations.Log.LogType;
import com.biocome.base.security.permission.Permission;
import com.biocome.base.util.JSonUtils;
import com.biocome.base.util.StringUtils;
import com.biocome.base.util.SystemUtil;
import com.biocome.clearing.operation.entity.system.SystemMonitor;
import com.biocome.clearing.operation.entity.system.User;
import com.biocome.clearing.operation.websocket.monitor.MonitorWebSocket;

@Service
public class SystemMonitorService {
	private volatile static boolean start = true;
	private static Logger LOGGER = LoggerFactory
			.getLogger(SystemMonitorService.class);
	@Resource
	SecurityService securityService;
	private static String XTJK_M = "XTJK_M";
	@Value("#{settings['msg.pwd']}")
	private String msgPwd;
	@Value("#{settings['msg.debug']}")
	private boolean debug;
	@Value("#{settings['sms.account.sid']}")
	private String sid;
	@Value("#{settings['sms.account.token']}")
	private String token;
	@Value("#{settings['sms.url']}")
	private String url;
	@Value("#{settings['sms.appid']}")
	private String appid;
	@Value("#{settings['sms.templateid']}")
	private String tid;
	@Resource
	Dao<SystemMonitor> systemMonitorDao;

	@Transactional(readOnly = true)
	public Page<SystemMonitor> getPage(SearchModel model) {
		return systemMonitorDao.findPage(model);
	}

	@Log(code = "更改监控程序的状态", type = LogType.NORMAL)
	public synchronized void setStatus(boolean status) {
		start = status;
	}

	public synchronized boolean getStatus() {
		return start;
	}

	// @Scheduled(cron = "0 */1 * * * ?")
	// @Transactional
	// public void monitor() {
	// if (start) {
	// List<SystemMonitor> monitors = systemMonitorDao.find(Restrictions
	// .eq("enabled", true));
	// for (SystemMonitor m : monitors) {
	// try {
	// boolean ison = heartBeat(m.getTestCommand());
	// if (m.getStatus() != (ison ? 0 : 1)) {
	// m.setStatus(ison ? 0 : 1);
	// systemMonitorDao.save(m);
	// }
	// // 发送短信
	// if (!ison && !debug) {
	// Date time = m.getSendTime();
	// Date now = new Date();
	// if (time == null
	// || DateUtils.rollHour(time, 1).before(now)) {
	// String msg = "服务器应用服务程序<" + m.getName()
	// + ">运行异常，请联系相关负责人员进行恢复。【老虎鱼服务平台】";
	// boolean res = sendMsg(msg, getToSendPhone());
	// if (res)
	// m.setSendTime(now);
	// }
	// }
	// } catch (Exception e) {
	// LOGGER.error("检测心跳时异常：" + e.fillInStackTrace());
	// m.setStatus(2);
	// systemMonitorDao.save(m);
	// }
	// }
	// }
	// }

	@Transactional(readOnly = true)
	public List<SystemMonitor> getHostMonitor(String hostname) {
		return systemMonitorDao.findBy("hostName", hostname);
	}

	@Transactional(readOnly = true)
	public SystemMonitor getMonitor(Long id) {
		return systemMonitorDao.get(id);
	}

	@Log(code = "保存监控配置", type = LogType.NORMAL)
	@Transactional
	public void save(SystemMonitor monitor) {
		if (!systemMonitorDao.isUnique(monitor, "hostName,name")) {
			throw new BusinessException("应用配置重复");
		}
		systemMonitorDao.save(monitor);
		String msg = JSonUtils.toJSon(monitor);
		MonitorWebSocket.push(monitor.getHostName(),
				MonitorWebSocket.TYPE_CLIENT_ADD + msg);
	}

	@Log(code = "更新监控配置", type = LogType.NORMAL)
	@Transactional
	public void update(SystemMonitor monitor) {
		if (!systemMonitorDao.isUnique(monitor, "hostName,name")) {
			throw new BusinessException("应用配置重复");
		}
		systemMonitorDao.save(monitor);
		String msg = JSonUtils.toJSon(monitor);
		MonitorWebSocket.push(monitor.getHostName(),
				MonitorWebSocket.TYPE_CLIENT_ADD + msg);
	}

	/**
	 * 监控记录，不需要日志
	 * 
	 * @param monitor
	 * @description:
	 */
	@Transactional
	public void updateMonitorStatus(SystemMonitor monitor) {
		systemMonitorDao.save(monitor);
	}

	@Transactional(readOnly = true)
	public SystemMonitor getMonitorByName(String hostname, String name) {
		return systemMonitorDao.findUnique(
				Restrictions.eq("hostName", hostname),
				Restrictions.eq("name", name));
	}

	@Log(code = "删除监控配置", type = LogType.NORMAL)
	@Transactional
	public void delete(Long id) {
		SystemMonitor m = systemMonitorDao.get(id);
		systemMonitorDao.remove(m);
		MonitorWebSocket.push(m.getHostName(),
				MonitorWebSocket.TYPE_CLIENT_DELETE + m.getName());
	}

	public boolean heartBeat(String cmd) {
		String result = SystemUtil.executeByFileWithResult(cmd, "/tmp", 10);
		// 心跳检测
		LOGGER.info(cmd + "检测结果:" + result);
		if (StringUtils.hasText(result)
				&& Pattern.compile("[0-9]+").matcher(result).matches()) {
			return true;
		} else {
			return false;
		}
	}

	@Log(code = "启动监控配置的程序", type = LogType.NORMAL)
	@Transactional
	public void start(Long id) {
		SystemMonitor m = systemMonitorDao.get(id);
		try {
			if (m.getStatus() == 2)
				throw new BusinessException("该主机监控程序已经离线，无法执行命令");
			if (!MonitorWebSocket.push(m.getHostName(),
					MonitorWebSocket.TYPE_CLIENT_START + m.getName())) {
				throw new BusinessException("启动应用指令发送失败");
			}
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("启动应用出现异常：" + e.fillInStackTrace());
			throw new BusinessException("启动应用出现异常");
		}
	}

	@Log(code = "停止监控配置的程序", type = LogType.NORMAL)
	@Transactional
	public void stop(Long id) {
		SystemMonitor m = systemMonitorDao.get(id);
		try {
			if (m.getStatus() == 2)
				throw new BusinessException("该主机监控程序已经离线，无法执行命令");
			if (!MonitorWebSocket.push(m.getHostName(),
					MonitorWebSocket.TYPE_CLIENT_STOP + m.getName())) {
				throw new BusinessException("停止应用指令发送失败");
			}
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			LOGGER.error("停止应用出现异常：" + e.fillInStackTrace());
			throw new BusinessException("停止应用出现异常");
		}
	}

	@Log(code = "更改监控配置启用状态", type = LogType.NORMAL)
	@Transactional
	public void changeStatus(Long id, boolean b) {
		systemMonitorDao.get(id).setEnabled(b);
	}

	/**
	 * 获取需要发送的号码
	 * 
	 * @return
	 * @description:
	 */
	@Transactional(readOnly = true)
	public List<String> getToSendPhone() {
		List<String> phones = new ArrayList<String>();
		List<User> user = securityService.getAllUser();
		for (User u : user) {
			List<Permission> ps = securityService.getCurrentUserPermissions(u);
			for (Permission p : ps) {
				if (XTJK_M.equals(p.getCode())) {
					phones.add(u.getPhone());
					break;
				}
			}
		}
		return phones;
	}

	/**
	 * 发送短信
	 * 
	 * @param msg
	 * @param phones
	 * @description:
	 */
	public boolean sendMsg(String msg, List<String> phones) {
		boolean suc = false;
		if (debug)
			return true;
		for (String phone : phones) {
			try {
				String result = "";
				String body = "accountSid=" + sid + "&appId=" + appid
						+ "&templateId=" + tid + "&to=" + phone + "&param="
						+ msg + createCommonParam();
				OutputStreamWriter out = null;
				BufferedReader in = null;
				URL realUrl = new URL(url);
				URLConnection conn = realUrl.openConnection();

				// 设置连接参数
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setConnectTimeout(5000);
				conn.setReadTimeout(20000);

				// 提交数据
				out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
				out.write(body);
				out.flush();

				// 读取返回数据
				in = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "UTF-8"));
				String line = "";
				boolean firstLine = true; // 读第一行不加换行符
				while ((line = in.readLine()) != null) {
					if (firstLine) {
						firstLine = false;
					} else {
						result += System.lineSeparator();
					}
					result += line;
				}
				LOGGER.info("发送短信结果：" + result);
				String respCode = (String) JSonUtils.get(result, "respCode");

				// 返回发送结果
				suc = suc || (respCode != null && respCode.contains("00000"));
			} catch (Exception e) {
				LOGGER.error("发送短信失败：" + e.fillInStackTrace());
				return false;
			}
		}
		return suc;
	}

	public String createCommonParam() {
		// 时间戳
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(new Date());

		// 签名
		String sig = DigestUtils.md5Hex(sid + token + timestamp);
		System.out.println(sid + token + timestamp);
		System.out.println(sig);
		return "&timestamp=" + timestamp + "&sig=" + sig;
	}

}
