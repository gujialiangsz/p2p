package com.biocome.clearing.operation.websocket.monitor;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import net.sf.ehcache.store.chm.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biocome.base.exception.BusinessException;
import com.biocome.base.util.DateUtils;
import com.biocome.base.util.JSonUtils;
import com.biocome.base.util.SpringUtils;
import com.biocome.base.util.StringUtils;
import com.biocome.clearing.operation.entity.system.SystemMonitor;
import com.biocome.clearing.operation.service.system.SystemMonitorService;

/**
 * Servlet implementation class MonitorWebSocket
 */
@ServerEndpoint("/websocket/monitor/{type}/{name}")
public class MonitorWebSocket {
	private static Logger LOGGER = LoggerFactory
			.getLogger(MonitorWebSocket.class);
	private static final Map<String, MonitorWebSocket> connections = new ConcurrentHashMap<String, MonitorWebSocket>();
	private String nickname;
	private Session session;
	public static final String ERROR = "500";
	// 客户端显示
	public static final String TYPE_UI = "11";
	// 客户端监控
	public static final String TYPE_CLIENT_REPORT = "21";
	// 操作码
	public static final String TYPE_CLIENT_INIT = "20";
	public static final String TYPE_CLIENT_START = "22";
	public static final String TYPE_CLIENT_STOP = "23";
	public static final String TYPE_CLIENT_RESTART = "24";
	public static final String TYPE_CLIENT_ADD = "25";
	public static final String TYPE_CLIENT_DELETE = "26";
	// 通讯类型，11监控系统界面，21监控系统客户端上报
	private String type;
	// 监控程序离线发送短信时间
	private static final Map<String, Long> msgSendTime = new ConcurrentHashMap<String, Long>();
	private SystemMonitorService systemMonitorService = SpringUtils
			.getBean("systemMonitorService");

	@OnOpen
	public void connect(Session session, @PathParam("type") String type,
			@PathParam("name") String name) {
		this.nickname = name;
		this.type = type;
		this.session = session;

		LOGGER.info(nickname + "建立连接");
		// 连接时，初始化监控客户端
		if (TYPE_CLIENT_REPORT.equals(type)) {
			List<SystemMonitor> ms = systemMonitorService.getHostMonitor(name);
			if (ms == null || ms.size() == 0) {
				throw new BusinessException("主机不在配置内，拒绝连接");
			}
			connections.put(nickname, this);
			String msg = JSonUtils.toJSon(ms);
			// 推送监控配置
			push(name, TYPE_CLIENT_INIT + msg);
			msgSendTime.remove(name);
		} else {
			connections.put(nickname, this);
		}
	}

	@OnClose
	public void disconnect() {
		LOGGER.info(nickname + "断开连接");
		connections.remove(this.nickname);
		// 更新状态
		if (TYPE_CLIENT_REPORT.equals(type)) {
			List<SystemMonitor> ms = systemMonitorService
					.getHostMonitor(nickname);
			for (SystemMonitor m : ms) {
				m.setStatus(2);
				systemMonitorService.updateMonitorStatus(m);
			}
			// 推送到界面
			String msg = JSonUtils.toJSon(ms);
			broadcastByType(TYPE_UI, msg);
			if (ms != null && ms.size() > 0)
				sendMsg(ms.get(0));
		}
	}

	@OnError
	public void error(Throwable throwable) {
		LOGGER.error(nickname + "通讯异常:" + throwable.getMessage());
		connections.remove(this.nickname);
		// 更新状态
		if (TYPE_CLIENT_REPORT.equals(type)) {
			List<SystemMonitor> ms = systemMonitorService
					.getHostMonitor(nickname);
			for (SystemMonitor m : ms) {
				m.setStatus(2);
				systemMonitorService.updateMonitorStatus(m);
			}
			// 推送到界面
			String msg = JSonUtils.toJSon(ms);
			broadcastByType(TYPE_UI, msg);
			if (ms != null && ms.size() > 0)
				sendMsg(ms.get(0));
		}
	}

	@OnMessage
	public void onMessage(String msg) {
		String realmsg = null;
		if (StringUtils.hasText(msg) && msg.length() >= 2)
			realmsg = msg.substring(2);
		else
			return;
		// 监控客户端上报
		if (TYPE_CLIENT_REPORT.equals(type)) {
			// 监控信息上报
			if (msg.startsWith(TYPE_CLIENT_REPORT)) {
				SystemMonitor[] ms = JSonUtils.readValue(realmsg,
						SystemMonitor[].class);

				if (ms == null || ms.length == 0) {
					try {
						session.getBasicRemote().sendText(ERROR);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					boolean update = false;
					for (SystemMonitor m : ms) {
						SystemMonitor mm = systemMonitorService
								.getMonitorByName(m.getHostName(), m.getName());
						boolean sendmsg = false;
						if (mm.getStatus() != m.getStatus()) {
							mm.setStatus(m.getStatus());
							update = true;
							sendmsg = true;
						}
						// 异常则发送短信
						if (mm.getStatus() > 0) {
							Date time = mm.getSendTime();
							Date now = new Date();
							if (time == null
									|| DateUtils.rollHour(time, 1).before(now)) {

								if (sendMsg(mm)) {
									mm.setSendTime(now);
									sendmsg = true;
								}
							}
						}
						if (sendmsg)
							systemMonitorService.updateMonitorStatus(mm);
					}
					if (update) {
						// 推送到界面
						broadcastByType(TYPE_UI, realmsg);
					}
				}
				// 监控配置初始化
			} else if (msg.startsWith(TYPE_CLIENT_INIT)) {
				List<SystemMonitor> ms = systemMonitorService
						.getHostMonitor(nickname);
				if (ms == null || ms.size() == 0) {
					LOGGER.warn("获取初始化配置为0个，请配置");
					return;
				}
				String msgs = JSonUtils.toJSon(ms);
				// 推送监控配置
				push(nickname, TYPE_CLIENT_INIT + msgs);
			}

		} else if (TYPE_UI.equals(type)) {

		}
	}

	public static boolean push(String name, String msg) {
		MonitorWebSocket client = connections.get(name);
		try {
			if (client != null) {
				client.session.getBasicRemote().sendText(msg);
			} else
				return false;
		} catch (Exception e) {
			LOGGER.error(client.nickname + "推送异常:" + e.getMessage());
			connections.remove(client.nickname);
			return false;
		}
		return true;
	}

	private boolean sendMsg(SystemMonitor mm) {
		String sendmsg = null;
		boolean res = false;
		if (mm.getStatus() == 1) {
			sendmsg = mm.getHostName() + "," + mm.getName();
			res = systemMonitorService.sendMsg(sendmsg,
					systemMonitorService.getToSendPhone());
		} else {
			Long timeCache = msgSendTime.get(mm.getHostName());
			if (timeCache == null
					|| System.currentTimeMillis() - timeCache > 1800000) {
				sendmsg = mm.getHostName() + ",监控程序";
				systemMonitorService.sendMsg(sendmsg,
						systemMonitorService.getToSendPhone());
				msgSendTime.put(mm.getHostName(), System.currentTimeMillis());
			}
		}
		return res;
	}

	public static boolean push(String name, Object obj) {
		MonitorWebSocket client = connections.get(name);
		try {
			if (client != null) {
				client.session.getBasicRemote().sendObject(obj);
			} else
				return false;
		} catch (Exception e) {
			LOGGER.error(client.nickname + "推送异常:" + e.getMessage());
			connections.remove(client.nickname);
			return false;
		}
		return true;
	}

	public static void broadcastByType(String type, String msg) {
		for (MonitorWebSocket client : connections.values()) {
			try {
				if (type != null && type.equals(client.type)) {
					client.session.getBasicRemote().sendText(msg);
				}
			} catch (Exception e) {
				LOGGER.error(client.nickname + "推送异常:" + e.getMessage());
				connections.remove(client.nickname);
			}
		}
	}
}
