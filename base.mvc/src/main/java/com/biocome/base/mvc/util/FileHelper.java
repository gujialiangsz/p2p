package com.biocome.base.mvc.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletResponse;

import com.biocome.base.exception.BusinessException;

public class FileHelper {
	/**
	 * 文件下载
	 * 
	 * @param in
	 *            待下载文件流
	 * @param response
	 *            HTTP响应
	 */
	public static void downloadFile(File file, HttpServletResponse response) {
		try {
			response.setContentType("application/octet-stream;");
			response.setHeader("Content-disposition", "attachment;filename="
					+ new String(file.getName().getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(file.length()));
			BufferedOutputStream bos = new BufferedOutputStream(
					response.getOutputStream());
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			byte[] bytes = new byte[1024];
			int len = -1;
			while ((len = bis.read(bytes)) != -1) {
				bos.write(bytes, 0, len);
			}
			bis.close();
			bos.flush();
			bos.close();
		} catch (Exception e) {
			throw new BusinessException("down file error", e);
		}
	}
}
