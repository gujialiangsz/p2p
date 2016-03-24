package com.biocome.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.biocome.base.exception.BusinessException;

public class NetUtils {
	public static String sendPost(String url, String param) {
		if (url != null) {
			if (url.startsWith("https")) {
				return sendHttps(url, param);
			} else {
				return sendHttp(url, param);
			}
		}
		return null;
	}

	public static String sendHttp(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.write(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			String code = conn.getContentType();
			if (code == null) {
				in = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "UTF-8"));
			} else if (code.toUpperCase().contains("ISO-8859-1")) {
				in = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "ISO-8859-1"));
			} else if (code.toUpperCase().contains("GBK")) {
				in = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "GBK"));
			} else if (code.toUpperCase().contains("GB2312")) {
				in = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "GB2312"));
			} else {
				in = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "UTF-8"));
			}
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (ConnectException ce) {
			throw new BusinessException("网络连接异常");
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
			throw new BusinessException("通讯异常");
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		try {
			return URLDecoder.decode(result, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * https
	 * 
	 */
	public static String sendHttps(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			// 创建TrustManager
			X509TrustManager xtm = new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			SSLContext ctx = SSLContext.getInstance("TLS");
			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			ctx.init(null, new TrustManager[] { xtm }, null);
			// 创建SSLSocketFactory
			SSLSocketFactory socketFactory = ctx.getSocketFactory();

			// 从上述SSLContext对象中得到SSLSocketFactory对象
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpsURLConnection conn = (HttpsURLConnection) realUrl
					.openConnection();
			conn.setSSLSocketFactory(socketFactory);
			conn.setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String paramString,
						SSLSession paramSSLSession) {
					return true;
				}
			});
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.write(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			String code = conn.getContentType();
			if (code == null) {
				in = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "UTF-8"));
			} else if (code.toUpperCase().contains("ISO-8859-1")) {
				in = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "ISO-8859-1"));
			} else if (code.toUpperCase().contains("GBK")) {
				in = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "GBK"));
			} else if (code.toUpperCase().contains("GB2312")) {
				in = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "GB2312"));
			} else {
				in = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "UTF-8"));
			}
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (ConnectException ce) {
			throw new BusinessException("网络连接异常");
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
			throw new BusinessException("通讯异常");
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		try {
			return URLDecoder.decode(result, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

}
