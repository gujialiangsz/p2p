package com.qiushui.base.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.qiushui.base.exception.BusinessException;

public class SystemUtil {
	private static final ExecutorService exc = Executors.newFixedThreadPool(4);
	public static final SystemType system = getSystemName();

	/**
	 * 系统类型
	 * 
	 * @author 谷家良
	 * 
	 */
	public enum SystemType {
		WINDOWS("Windows系统", "windows"), LINUX("linux系统", "linux"), UNKNOWN(
				"未知系统", "unknown");

		private String text;
		private String value;

		SystemType(String text, String value) {
			this.text = text;
			this.value = value;
		}

		public String getText() {
			return text;
		}

		public String getValue() {
			return value;
		}

	}

	public enum InternetStatus {
		OK("正常", "200"), NOT_FOUND("找不到资源", "404"), APP_ERROR("应用错误", "500"), UNCONNECTION(
				"连接异常", "-1");

		private String text;
		private String value;

		InternetStatus(String text, String value) {
			this.text = text;
			this.value = value;
		}

		public String getText() {
			return text;
		}

		public String getValue() {
			return value;
		}

	}

	/**
	 * 获得系统类型
	 * 
	 * @return
	 */
	public static SystemType getSystemName() {
		String name = System.getProperty("os.name").toLowerCase();
		SystemType[] systems = SystemType.values();
		for (SystemType s : systems) {
			if (name.contains(s.value))
				return s;
		}
		return SystemType.UNKNOWN;
	}

	public static InternetStatus getInternetStatus(String urlString) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(600000);
			conn.setConnectTimeout(600000);
			int code = conn.getResponseCode();
			InternetStatus[] status = InternetStatus.values();
			for (InternetStatus s : status) {
				if (s.value.equals(String.valueOf(code)))
					return s;
			}
		} catch (Exception e) {
			return InternetStatus.UNCONNECTION;
		}
		return InternetStatus.UNCONNECTION;
	}

	/**
	 * 进入指定目录执行本地命令，非批处理命令，格式如net stop mysql，则"net","stop","mysql"
	 * 
	 * @param cmd
	 * @param dir
	 * @param waiteFor
	 * @return
	 */
	public static synchronized int executeDir(final String dir,
			boolean waiteFor, long time, final String... cmd) {
		try {
			ProcessBuilder pb = new ProcessBuilder(cmd);
			if (dir != null) {
				pb.directory(new File(dir));
			}
			Process process = pb.start();
			if (time > 0)
				Thread.sleep(time);
			if (waiteFor) {
				return process.waitFor();
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("执行命令发生异常:" + cmd);
		}
	}

	/**
	 * 直接执行命令，包括批处理
	 * 
	 * @param cmd
	 * @param waiteFor
	 * @return
	 */
	public static synchronized int executeDirect(final String cmd,
			final String dir, boolean waiteFor, long time) {
		try {
			Process p = dir == null ? Runtime.getRuntime().exec(cmd) : Runtime
					.getRuntime().exec(cmd, null, new File(dir));
			if (time > 0)
				Thread.sleep(time);
			if (waiteFor) {
				return p.waitFor();
			}
			return 0;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new BusinessException("执行命令发生异常:" + cmd);
		}
	}

	/**
	 * 
	 * @param cmd
	 * @param waiteFor
	 *            是否等待结果
	 * @param containstr
	 *            返回值中判断是否有该值
	 * @return
	 */
	public static synchronized boolean executeDirectWithResult(
			final String cmd, boolean waiteFor, String containstr) {
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			if (waiteFor) {
				int re = p.waitFor();
				BufferedReader is = new BufferedReader(new InputStreamReader(
						p.getInputStream()));
				String line = is.readLine();
				is.close();
				return String.valueOf(re).contains(containstr)
						|| (line != null && line.contains(containstr));
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("执行命令发生异常:" + cmd);
		}
	}

	/**
	 * 
	 * @param cmd
	 * @param dir
	 * @param readLog
	 *            当返回值不为0则读取日志，false则不判断返回值
	 * @return
	 */
	public static synchronized boolean executeByFile(final String cmd,
			final String dir, boolean readLog, long time, String checkString) {
		boolean isWindows = getSystemName().equals(SystemType.WINDOWS) ? true
				: false;
		String tempbat = dir + (isWindows ? "temp.bat" : "temp.sh");
		File file = new File(tempbat);
		Process process = null;
		byte[] bs = new byte[1024];
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(cmd);
			bw.flush();
			bw.close();
			if (!isWindows) {
				process = Runtime.getRuntime().exec("chmod 777 " + tempbat);
				process.waitFor();
			}
			ProcessBuilder builder = new ProcessBuilder(tempbat);
			builder.directory(new File(dir));
			builder.redirectErrorStream(true);
			process = builder.start();
			if (time > 0)
				Thread.sleep(time);
			if (readLog && checkString != null) {
				InputStream is = process.getInputStream();
				int len = -1;
				while ((len = is.read(bs)) > -1) {
					if (new String(bs, 0, len, "GBK").toUpperCase().contains(
							checkString.toUpperCase()))
						return false;
				}
			}
			return true;
		} catch (UnsupportedEncodingException e) {
			try {
				if (new String(bs, "UTF-8").toUpperCase().contains(
						checkString.toUpperCase()))
					return false;
				return true;
			} catch (Exception e1) {
				System.out.println("读取命令响应流失败");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("执行命令发生异常:" + cmd);
		} finally {
			try {
				file.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param cmd
	 * @param dir
	 * @param readLog
	 *            当返回值不为0则读取日志，false则不判断返回值
	 * @return
	 */
	public static synchronized BufferedReader executeByFile(final String cmd,
			final String dir, long time) {
		boolean isWindows = getSystemName().equals(SystemType.WINDOWS) ? true
				: false;
		String tempbat = dir + (isWindows ? "temp.bat" : "temp.sh");
		File file = new File(tempbat);
		Process process = null;
		BufferedReader br = null;
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), isWindows ? "GBK" : "UTF-8"));
			bw.write(cmd);
			bw.flush();
			bw.close();
			if (!isWindows) {
				process = Runtime.getRuntime().exec("chmod 777 " + tempbat);
				process.waitFor();
			}
			ProcessBuilder builder = new ProcessBuilder(tempbat);
			builder.directory(new File(dir));
			builder.redirectErrorStream(true);
			process = builder.start();
			if (time > 0)
				Thread.sleep(time);
			br = new BufferedReader(new InputStreamReader(
					process.getInputStream(), "GBK"));
			return br;
		} catch (UnsupportedEncodingException e) {
			System.out.println("读取命令响应流失败");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("执行命令发生异常:" + cmd);
		}
	}

	/**
	 * 
	 * @param cmd
	 * @param dir
	 * @param readLog
	 *            当返回值不为0则读取日志，false则不判断返回值
	 * @return
	 */
	public static synchronized String executeByFileWithResult(final String cmd,
			final String dir, long time) {
		boolean isWindows = getSystemName().equals(SystemType.WINDOWS) ? true
				: false;
		String tempbat = dir + (isWindows ? "temp.bat" : "temp.sh");
		File file = new File(tempbat);
		Process process = null;
		BufferedReader br = null;
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), isWindows ? "GBK" : "UTF-8"));
			bw.write(cmd);
			bw.flush();
			bw.close();
			if (!isWindows) {
				process = Runtime.getRuntime().exec("chmod 777 " + tempbat);
				process.waitFor();
			}
			ProcessBuilder builder = new ProcessBuilder(tempbat);
			builder.directory(new File(dir));
			builder.redirectErrorStream(true);
			process = builder.start();
			if (time > 0)
				Thread.sleep(time);
			br = new BufferedReader(new InputStreamReader(
					process.getInputStream(), "GBK"));
			return br.readLine();
		} catch (UnsupportedEncodingException e) {
			System.out.println("读取命令响应流失败");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("执行命令发生异常:" + cmd);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static void executeWithoutResult(Runnable run) {
		exc.execute(run);
	}

}
