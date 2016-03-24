package com.biocome.base.mvc.util.qiniu;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import com.biocome.base.exception.BusinessException;
import com.biocome.base.util.CryptUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

public class CloudUtil {

	private static String ACCESS_KEY;
	private static String SECRET_KEY;
	private static String SPACE_NAME;
	private static String DOWNLOAD_URL;

	static {
		Properties p = new Properties();
		try {
			p.load(CloudUtil.class.getResourceAsStream("qiniu.properties"));
		} catch (IOException e) {
			throw new BusinessException("上传工具类初始化失败，请检查配置文件");
		}
		ACCESS_KEY = CryptUtils.aesDecrypt(p.getProperty("ACCESS_KEY"),
				"ACCESS_KEY");
		SECRET_KEY = CryptUtils.aesDecrypt(p.getProperty("SECRET_KEY"),
				"SECRET_KEY");
		SPACE_NAME = CryptUtils.aesDecrypt(p.getProperty("SPACE_NAME"),
				"SPACE_NAME");
		DOWNLOAD_URL = CryptUtils.aesDecrypt(p.getProperty("DOWNLOAD_URL"),
				"DOWNLOAD_URL");
	}

	public static StringMap upload(String file) {
		File f = new File(file);
		return upload(f);
	}

	public static StringMap upload(File file) {
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

		UploadManager uploadManager = new UploadManager();
		try {
			Response res = uploadManager.put(file, file.getName(),
					auth.uploadToken(SPACE_NAME, file.getName()));
			StringMap map = res.jsonToMap();
			map.put("url", DOWNLOAD_URL + file.getName());
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("上传文件异常");
		}
	}

	public static StringMap upload(byte[] data, String key) {
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		UploadManager uploadManager = new UploadManager();
		try {
			Response res = uploadManager.put(data, key,
					auth.uploadToken(SPACE_NAME, key));
			if (!res.isOK())
				throw new BusinessException("上传文件失败");
			StringMap map = res.jsonToMap();
			map.put("url", DOWNLOAD_URL + key);
			return map;
		} catch (Exception e) {
			throw new RuntimeException("上传文件异常");
		}
	}

	public static void deleteFile(String key) {
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		BucketManager bucketManager = new BucketManager(auth);
		try {
			bucketManager.delete(SPACE_NAME, key);
		} catch (QiniuException qe) {
			if (StatusCode.FILE_NOT_FOUND != qe.code())
				throw new RuntimeException("删除文件时发生异常");
		} catch (Exception e) {
			throw new RuntimeException("刪除文件失败");
		}
	}

	public static void copyFile(String skey, String targetKey) {
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		BucketManager bucketManager = new BucketManager(auth);
		try {
			bucketManager.copy(SPACE_NAME, skey, SPACE_NAME, targetKey);
		} catch (QiniuException qe) {
			if (StatusCode.FILE_NOT_FOUND != qe.code())
				throw new RuntimeException("复制文件时发生异常");
		} catch (Exception e) {
			throw new RuntimeException("复制文件失败");
		}
	}

	public static void main(String[] args) {
		System.out.println(CryptUtils.aesEncrypt(
				"ZCi7K4Jq_2zKVTPxsVcp-lo634CEeJz7ju-fym8y", "ACCESS_KEY"));
		System.out.println(CryptUtils.aesEncrypt(
				"1mPYKIrewHmr_oYC2fCmVyzqiQDiuZxl36gk7KZO", "SECRET_KEY"));
		System.out.println(CryptUtils.aesEncrypt("bapk", "SPACE_NAME"));
		System.out.println(CryptUtils.aesEncrypt(
				"http://7xkjqy.dl1.z0.glb.clouddn.com/", "DOWNLOAD_URL"));
	}
}
