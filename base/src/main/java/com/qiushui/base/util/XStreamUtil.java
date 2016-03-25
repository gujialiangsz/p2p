package com.qiushui.base.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import com.qiushui.base.exception.BusinessException;
import com.thoughtworks.xstream.XStream;

@SuppressWarnings("unchecked")
public class XStreamUtil {
	private static final XStream xs = new XStream();

	/**
	 * 使用xstream生成xml文件
	 * 
	 * @param object
	 *            实体类 ，需要用注解指定xml格式
	 * @param code
	 *            编码
	 * @param out
	 *            文件输出流
	 */
	public static synchronized void objectToXmlFile(Object object, String code,
			File file) {
		try {
			xs.setMode(XStream.NO_REFERENCES);
			xs.processAnnotations(object.getClass());
			Writer writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), code));
			xs.toXML(object, writer);
		} catch (Exception e) {
			throw new BusinessException("write file error", e);
		}
	}

	/**
	 * 将对象转换成xml字符串
	 * 
	 * @param object
	 * @description:
	 */
	public static synchronized String objectToXml(Object object) {
		try {
			xs.setMode(XStream.NO_REFERENCES);
			xs.processAnnotations(object.getClass());
			return xs.toXML(object);
		} catch (Exception e) {
			throw new BusinessException("convert object error", e);
		}
	}

	/**
	 * 将字符串转换成对象
	 * 
	 * @param object
	 * @description:
	 */
	public static synchronized <X> X xmlToObject(String xml, Class<?> clazz) {
		try {
			xs.setMode(XStream.NO_REFERENCES);
			xs.processAnnotations(clazz);
			return (X) xs.fromXML(xml);
		} catch (Exception e) {
			throw new BusinessException("convert object error", e);
		}
	}

	/**
	 * 使用xstream读取xml到实体类
	 * 
	 * @param <T>
	 * 
	 * @param clazz
	 *            实体类名 ，需要用注解指定xml格式
	 * @param in
	 *            文件输入流
	 * @return
	 */
	public static Object xmlFileToObject(String code, File file,
			Class<?>... clazz) {
		try {
			Reader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), code));
			XStream xs = new XStream();
			xs.setMode(XStream.NO_REFERENCES);
			xs.processAnnotations(clazz);
			return xs.fromXML(in);
		} catch (Exception e) {
			throw new BusinessException("文件解析失败");
		}
	}
}
