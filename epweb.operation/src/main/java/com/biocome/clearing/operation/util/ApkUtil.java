package com.biocome.clearing.operation.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.biocome.base.exception.BusinessException;
import com.biocome.base.util.SystemUtil;
import com.biocome.base.util.SystemUtil.SystemType;
import com.biocome.clearing.operation.util.utilmodel.ApkInfo;

/**
 * apk工具类。封装了获取Apk信息的方法。
 * 
 */
public class ApkUtil {
	public static final String VERSION_CODE = "versionCode";
	public static final String VERSION_NAME = "versionName";
	public static final String SDK_VERSION = "sdkVersion";
	public static final String TARGET_SDK_VERSION = "targetSdkVersion";
	public static final String USES_PERMISSION = "uses-permission";
	public static final String APPLICATION_LABEL = "application-label";
	public static final String APPLICATION_ICON = "application-icon";
	public static final String USES_FEATURE = "uses-feature";
	public static final String USES_IMPLIED_FEATURE = "uses-implied-feature";
	public static final String SUPPORTS_SCREENS = "supports-screens";
	public static final String SUPPORTS_ANY_DENSITY = "supports-any-density";
	public static final String DENSITIES = "densities";
	public static final String PACKAGE = "package";
	public static final String APPLICATION = "application:";
	public static final String LAUNCHABLE_ACTIVITY = "launchable-activity";
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private static final String SPLIT_REGEX = "(: )|(=')|(' )|'";
	/**
	 * aapt所在的目录。
	 */
	private String mAaptPath = this.getClass().getClassLoader().getResource("")
			.getPath();

	/**
	 * 返回一个apk程序的信息。
	 * 
	 * @param apkPath
	 *            apk的路径。
	 * @return apkInfo 一个Apk的信息。
	 */
	public ApkInfo getApkInfo(String apkPath) throws Exception {
		String cmd = SystemType.WINDOWS.equals(SystemUtil.getSystemName()) ? "call aapt d badging "
				+ apkPath
				: ("chmod 777 aapt \n " + "./aapt d badging " + apkPath);
		BufferedReader is = SystemUtil.executeByFile(cmd, mAaptPath, 0);
		String tmp = is.readLine();
		try {
			ApkInfo apkInfo = new ApkInfo();
			do {
				if ("".equals(tmp) || tmp == null)
					continue;
				setApkInfoProperty(apkInfo, tmp);
			} while ((tmp = is.readLine()) != null);
			return apkInfo;
		} catch (Exception e) {
			log.error("APP解析错误" + e.fillInStackTrace());
			throw new BusinessException("解析apk失败");
		} finally {
			closeIO(is);
		}
	}

	/**
	 * 设置APK的属性信息。
	 * 
	 * @param apkInfo
	 * @param source
	 */
	private void setApkInfoProperty(ApkInfo apkInfo, String source) {
		if (source.startsWith(PACKAGE)) {
			splitPackageInfo(apkInfo, source);
		} else if (source.startsWith(LAUNCHABLE_ACTIVITY)) {
			apkInfo.setLaunchableActivity(getPropertyInQuote(source));
		} else if (source.startsWith(SDK_VERSION)) {
			apkInfo.setSdkVersion(getPropertyInQuote(source));
		} else if (source.startsWith(TARGET_SDK_VERSION)) {
			apkInfo.setTargetSdkVersion(getPropertyInQuote(source));
		} else if (source.startsWith(USES_PERMISSION)) {
			apkInfo.addToUsesPermissions(getPropertyInQuote(source));
		} else if (source.startsWith(APPLICATION_LABEL)) {
			apkInfo.setApplicationLable(getPropertyInQuote(source));
		} else if (source.startsWith(APPLICATION_ICON)) {
			apkInfo.addToApplicationIcons(getKeyBeforeColon(source),
					getPropertyInQuote(source));
		} else if (source.startsWith(APPLICATION)) {
			String[] rs = source.split("( icon=')|'");
			apkInfo.setApplicationIcon(rs[rs.length - 1]);
		} else if (source.startsWith(USES_FEATURE)) {
			apkInfo.addToFeatures(getPropertyInQuote(source));
		}
	}

	/**
	 * 返回出格式为name: 'value'中的value内容。
	 * 
	 * @param source
	 * @return
	 */
	private String getPropertyInQuote(String source) {
		try {
			source = new String(source.getBytes(), "UTF-8");
			int index = source.indexOf(":") + 1;
			return source.substring(index).replaceAll("\'", "");
		} catch (Exception e) {
			// 乱码问题
			log.error("解析参数乱码");
			throw new BusinessException("解析参数乱码");
		}
	}

	/**
	 * 返回冒号前的属性名称
	 * 
	 * @param source
	 * @return
	 */
	private String getKeyBeforeColon(String source) {
		return source.substring(0, source.indexOf(':'));
	}

	/**
	 * 分离出包名、版本等信息。
	 * 
	 * @param apkInfo
	 * @param packageSource
	 */
	private void splitPackageInfo(ApkInfo apkInfo, String packageSource) {
		String[] packageInfo = packageSource.split(SPLIT_REGEX);
		apkInfo.setPackageName(packageInfo[2]);
		apkInfo.setVersionCode(packageInfo[4]);
		apkInfo.setVersionName(packageInfo[6]);
	}

	/**
	 * 释放资源。
	 * 
	 * @param c
	 *            将关闭的资源
	 */
	private final void closeIO(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		try {
			String demo = "C:/Users/lenovo/Downloads/com.workoutfree.apk";
			ApkInfo apkInfo = new ApkUtil().getApkInfo(demo);
			System.out.println(apkInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getmAaptPath() {
		return mAaptPath;
	}

	public void setmAaptPath(String mAaptPath) {
		this.mAaptPath = mAaptPath;
	}
}
