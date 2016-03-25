package com.qiushui.base.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;

import com.qiushui.base.exception.BusinessException;
import com.qiushui.base.exception.UncheckedException;

/**
 * 字符串工具类。
 */
public abstract class StringUtils {
	/**
	 * 判断指定字符串是否为空。
	 * 
	 * @param str
	 *            待判断的字符串
	 * @return 返回指定字符串是否为空。
	 */
	public static Boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}

	/**
	 * 判断指定字符串是否不为空。
	 * 
	 * @param str
	 *            待判断的字符串
	 * @return 返回指定字符串是否不为空。
	 */
	public static Boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 判断指定字符串是否为空字符串。
	 * 
	 * @param str
	 *            待判断的字符串
	 * @return 返回指定字符串是否为空字符串。
	 */
	public static Boolean isBlank(String str) {
		if (isEmpty(str)) {
			return true;
		}
		for (char c : str.toCharArray()) {
			if (!Character.isWhitespace(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断指定字符串是否不为空字符串。
	 * 
	 * @param str
	 *            待判断的字符串
	 * @return 返回指定字符串是否不为空字符串。
	 */
	public static Boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * 截取指定分隔符前的字符串内容。
	 * 
	 * @param str
	 *            待截取的字符串
	 * @param separator
	 *            分隔符
	 * @return 返回指定分隔符前的字符串内容。
	 */
	public static String substringBefore(String str, String separator) {
		Assert.notEmpty(str);
		Assert.notEmpty(separator);

		int pos = str.indexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	/**
	 * 截取最后一个分隔符前的字符串内容。
	 * 
	 * @param str
	 *            待截取的字符串
	 * @param separator
	 *            分隔符
	 * @return 返回最后一个分隔符前的字符串内容。
	 */
	public static String substringBeforeLast(String str, String separator) {
		Assert.notEmpty(str);
		Assert.notEmpty(separator);

		int pos = str.lastIndexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	/**
	 * 截取指定分隔符后的字符串内容。
	 * 
	 * @param str
	 *            待截取的字符串
	 * @param separator
	 *            分隔符
	 * @return 返回指定分隔符后的字符串内容。
	 */
	public static String substringAfter(String str, String separator) {
		Assert.notEmpty(str);
		Assert.notEmpty(separator);

		int pos = str.indexOf(separator);
		if (pos == -1) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	/**
	 * 截取最后一个分隔符后的字符串内容。
	 * 
	 * @param str
	 *            待截取的字符串
	 * @param separator
	 *            分隔符
	 * @return 返回最后一个分隔符后的字符串内容。
	 */
	public static String substringAfterLast(String str, String separator) {
		Assert.notEmpty(str);
		Assert.notEmpty(separator);

		int pos = str.lastIndexOf(separator);
		if (pos == -1 || pos == (str.length() - separator.length())) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	/**
	 * 截取两个分隔符之间的字符串。
	 * 
	 * @param str
	 *            待截取的字符串
	 * @param startSeparator
	 *            开始分隔符
	 * @param endSeparator
	 *            结束分隔符
	 * @return 返回两个分隔符之间的字符串。
	 */
	public static String substringBetween(String str, String startSeparator,
			String endSeparator) {
		if (str == null || startSeparator == null || endSeparator == null) {
			return null;
		}
		int start = str.indexOf(startSeparator);
		if (start != -1) {
			int end = str
					.indexOf(endSeparator, start + startSeparator.length());
			if (end != -1) {
				return str.substring(start + startSeparator.length(), end);
			}
		}
		return null;
	}

	/**
	 * 截取指定起始位置和截取长度的字符串。
	 * 
	 * @param str
	 *            待截取的字符串
	 * @param pos
	 *            起始位置
	 * @param len
	 *            截取长度
	 * @return 返回指定起始位置和截取长度的字符串。
	 */
	public static String mid(String str, int pos, int len) {
		Assert.notEmpty(str);
		Assert.isTrue(pos >= 0 && pos <= str.length());
		Assert.isTrue(len >= 0);

		if (str.length() <= (pos + len)) {
			return str.substring(pos);
		}
		return str.substring(pos, pos + len);
	}

	/**
	 * 将一个字符串数组用指定分隔符串联起来。
	 * 
	 * @param strs
	 *            字符串数组
	 * @param separator
	 *            分隔符
	 * @return 返回串联起来的字符串。
	 */
	public static String join(String[] strs, String separator) {
		Assert.notEmpty(strs);
		Assert.notNull(separator);

		StringBuffer buf = new StringBuffer(256);
		for (String str : strs) {
			buf.append(str + separator);
		}

		String result = buf.toString();
		if (!separator.isEmpty()) {
			result = substringBeforeLast(result, separator);
		}
		return result;
	}

	/**
	 * 将一个字符串列表用指定分隔符串联起来。
	 * 
	 * @param strs
	 *            字符串数组
	 * @param separator
	 *            分隔符
	 * @return 返回串联起来的字符串数组。
	 */
	public static String join(List<String> strs, String separator) {
		return join(strs.toArray(new String[] {}), separator);
	}

	/**
	 * 对字符串进行字符集转换。
	 * 
	 * @param str
	 *            字符串
	 * @param origEncoding
	 *            原字符集编码
	 * @param destEncoding
	 *            新字符集编码
	 * @return 返回转换后的字符串。
	 */
	public static String encode(String str, String origEncoding,
			String destEncoding) {
		try {
			return new String(str.getBytes(origEncoding), destEncoding);
		} catch (UnsupportedEncodingException e) {
			throw new UncheckedException("对字符串进行字符集转换时发生异常", e);
		}
	}

	public static boolean hasText(String str) {
		if (str == null || "".equals(str) || str.matches("^[ ]+$")) {
			return false;
		}
		return true;
	}

	/**
	 * 字符串首字母转大写
	 * 
	 * @param str
	 * @return
	 */
	public static String firstUpper(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	/**
	 * 字符串转换为BigDecimal
	 * 
	 * @param str
	 * @return
	 */
	public static BigDecimal stringToDecimal(String str) {
		BigDecimal bd = null;
		try {
			bd = new BigDecimal(str);
		} catch (Exception e) {
			throw new BusinessException("字符串转BigDecimal失败:" + e.getMessage());
		}
		return bd;
	}

	/**
	 * 字符串转为Char
	 * 
	 * @param str
	 * @return
	 */
	public static char stringToChar(String str) {
		return str.charAt(0);
	}

	/**
	 * 字符串转为Long类型
	 * 
	 * @param str
	 * @return
	 */
	public static Long stringToLong(String str) {
		Long l = null;
		try {
			BigDecimal bd = new BigDecimal(str);
			l = bd.longValueExact();
		} catch (Exception e) {
			throw new BusinessException("字符串转Long失败:" + e.getMessage());
		}
		return l;
	}

	/**
	 * 字符串转为整数类型
	 * 
	 * @param str
	 * @return
	 */
	public static Integer stringToInteger(String str) {
		Integer i = null;
		try {
			i = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			throw new BusinessException("字符串转Integer失败:" + e.getMessage());
		}
		return i;
	}

	/**
	 * 字符串转换成Double类型
	 * 
	 * @param str
	 * @return
	 */
	public static Double stringToDouble(String str) {
		Double i = null;
		try {
			i = Double.parseDouble(str);
		} catch (NumberFormatException e) {
			throw new BusinessException("字符串转Integer失败:" + e.getMessage());
		}
		return i;
	}

	/**
	 * 向左补齐
	 * 
	 * @param str
	 * @param size
	 * @param padChar
	 * @return
	 */
	public static String leftPad(String str, int size, char padChar) {
		int i = size - str.length();
		StringBuilder sb = new StringBuilder(str);
		if (i >= 0) {
			for (int j = 0; j < i; j++) {
				sb.insert(0, padChar);
			}
		} else
			throw new BusinessException("字符串长度已经大于指定长度");
		return sb.toString();
	}

	public static String rightPad(String str, int size, char padChar) {
		int i = size - str.length();
		StringBuilder sb = new StringBuilder(str);
		if (i >= 0) {
			for (int j = 0; j < i; j++) {
				sb.append(padChar);
			}
		} else
			throw new BusinessException("字符串长度已经大于指定长度");
		return sb.toString();
	}

	/**
	 * 将字符串重复指定次数
	 * 
	 * @param str
	 * @param repeat
	 * @return
	 */
	public static String repeat(String str, int repeat) {
		StringBuilder sb = new StringBuilder(str);
		for (int j = 0; j < repeat; j++) {
			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * 将数组按照指定分隔符连接成字符串
	 * 
	 * @param ary
	 * @param str
	 * @return
	 */
	public static String john(Object[] ary, String str) {
		StringBuilder sb = new StringBuilder();
		int length = ary.length;
		for (int i = 0; i < length; i++) {
			sb.append(ary[i]);
			if (i < length - 1)
				sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * 去除字符串中指定的多余的字符
	 * 
	 * @param before
	 * @param s
	 * @return
	 */
	public static String trim(String before, char s) {
		StringBuilder sb = new StringBuilder();
		int length = before.length();
		for (int i = 0; i < length; i++) {
			char c = before.charAt(i);
			if (c != s || c == s && before.charAt(i + 1) != s) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 去除字符串中连续重复的字符
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		StringBuilder sb = new StringBuilder();
		int length = str.length();
		char temp = str.charAt(0);
		for (int i = 0; i < length; i++) {
			char c = temp;
			if (i < length - 1) {
				c = str.charAt(i + 1);
				if (c == temp) {
					continue;
				}
				sb.append(temp);
				temp = c;
			} else
				sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * 如果尾部不是该字符则追加
	 * 
	 * @param original
	 * @param append
	 * @return
	 */
	public static String lastAppendUnique(String original, String append) {
		if (original.endsWith(append))
			return original;
		else {
			return original + append;
		}
	}

	/**
	 * 如果首部不是该字符则追加
	 * 
	 * @param original
	 * @param append
	 * @return
	 */
	public static String firstAppendUnique(String original, String append) {
		if (original.startsWith(append))
			return original;
		else {
			return append + original;
		}
	}

	public static String[] split(String str, String seperator, int num) {
		String[] result = new String[num];
		int i = 0;
		for (; i < num - 1; i++) {
			int j = str.indexOf(seperator);
			if (j >= 0) {
				result[i] = str.substring(0, j);
				str = str.substring(j + 1);
			} else {
				break;
			}
		}
		result[i] = str;
		return result;
	}

	/**
	 * 去除指定字符串中包含的字符。
	 * 
	 * @param sourceStr
	 * @param deleteStr
	 * @return
	 */
	public static String trimStr(String sourceStr, String deleteStr) {
		StringBuilder sb = new StringBuilder(sourceStr);
		for (int i = 0; i < sb.length();) {
			if (deleteStr.contains(String.valueOf(sb.charAt(i)))) {
				sb.deleteCharAt(i);
			} else {
				i++;
			}
		}
		return sb.toString();
	}

	/**
	 * 去除指定表达式的字符
	 * 
	 * @param str
	 * @param res
	 * @return
	 */
	public static String trimRes(String str, String res) {
		StringBuilder sb = new StringBuilder(str);
		for (int i = 0; i < sb.length();) {
			if (String.valueOf(sb.charAt(i)).matches(res)) {
				sb.deleteCharAt(i);
			} else {
				i++;
			}
		}
		return sb.toString();
	}

	public static boolean isMatch(String regex, String str) {
		Matcher match = Pattern.compile(regex).matcher(str);
		return match.find();
	}

	public static String[] toArray(String target, String delim) {
		if (target == null)
			return new String[0];
		StringTokenizer st = new StringTokenizer(target, delim);
		String[] result = new String[st.countTokens()];
		int i = 0;

		while (st.hasMoreTokens()) {
			result[i] = st.nextToken();
			i++;
		}
		return result;
	}

	public static String genUid() {
		return UUID.randomUUID().toString();
	}

	public static String arrayToStr(String[] arr) {
		String str = null;
		if (arr == null)
			return "";
		else {
			for (int i = 0; i < arr.length; i++) {
				str += arr[i] + "\r\n";
			}
		}
		return str;
	}

	public static String replaceStr(String str, String oldStr, String newStr) {
		int s = 0;
		int e = 0;
		int ol = oldStr.length();
		StringBuffer result = new StringBuffer();

		while ((e = str.indexOf(oldStr, s)) >= 0) {
			result.append(str.substring(s, e));
			result.append(newStr);
			s = e + ol;
		}
		result.append(str.substring(s));
		return result.toString();
	}

	public static String ConvertToStr(Object obj) {
		String str = "";
		if (!isEmpty(obj)) {
			str = obj.toString().trim();
		}
		return str;
	}

	public static String ConvertDbStrToStr(Object obj) {
		String str = "";
		if (!isEmpty(obj)) {
			str = obj.toString().trim().toLowerCase();
		}
		if (str.contains("_")) {
			String[] tmp = str.split("_");
			int length = tmp.length;
			str = tmp[0];
			for (int i = 1; i < length; i++) {
				String strtmp = tmp[i];
				str += strtmp.substring(0, 1).toUpperCase()
						+ strtmp.substring(1, strtmp.length());
			}
		}
		return str;
	}

	public static String ConvertToStrs(Object obj) {
		String str = " ";
		if (!isEmpty(obj)) {
			str = obj.toString().trim();
		}
		return str;
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param obj
	 *            数据对象
	 * @return true : 对象为空 false : 对象不对空
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		try {
			if (obj == null) {
				return true;
			}
			Class<? extends Object> cls = obj.getClass();
			if (cls.isArray()) {
				return ((Object[]) obj).length == 0;
			}
			if (obj instanceof String) {
				return obj.toString().trim().length() == 0;
			}
			if (obj instanceof Collection) {
				return ((Collection) obj).isEmpty();
			}
			if (obj instanceof Map) {
				return ((Map) obj).isEmpty();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String ConvertDecToStr(Object obj, String code) {
		String str = ConvertToStr(obj);
		try {
			if (!str.equals(""))
				str = URLDecoder.decode(str, code);
		} catch (Exception e) {
			// SaveStackTrace(e);
		}
		return str;
	}

	public static String wrapClauses(String[] strs) {
		if (strs == null || strs.length == 0)
			return "('')";
		String rel = "(";
		for (int i = 0; i < strs.length; i++) {
			rel += "'" + strs[i] + "',";
		}
		rel = removeEnd(rel, ",");
		return rel + ")";
	}

	public static String wrapClause(String[] strs) {
		String rel = "";
		for (int i = 0; i < strs.length; i++) {
			rel += "'" + strs[i] + "',";
		}
		rel = removeEnd(rel, ",");
		return rel;
	}

	public static String wrapClauseList(Map<String, List<String>> map) {
		String rel = "";
		if (!isEmpty(map)) {
			for (Iterator<String> iter = map.keySet().iterator(); iter
					.hasNext();) {
				String fieldName = iter.next();
				List<String> valObj = map.get(fieldName);
				rel += " " + fieldName + " in " + wrapMidList(valObj) + " and";
			}
			rel = removeEnd(rel, "and");
		}
		return rel;
	}

	public static String wrapClauseStringArray(Map<String, String[]> map) {
		String rel = "";
		if (!isEmpty(map)) {
			for (Iterator<String> iter = map.keySet().iterator(); iter
					.hasNext();) {
				String fieldName = iter.next();
				String[] valObj = map.get(fieldName);
				rel += " " + fieldName + " in " + wrapClauses(valObj) + " and";
			}
			rel = removeEnd(rel, "and");
		}
		return rel;
	}

	public static String wrapClauseString(Map<String, String> map) {
		String rel = "";
		if (!isEmpty(map)) {
			for (Iterator<String> iter = map.keySet().iterator(); iter
					.hasNext();) {
				String fieldName = (String) iter.next();
				String valObj = map.get(fieldName);
				rel += " " + fieldName + " = " + valObj + " and";
			}
			rel = removeEnd(rel, "and");
		}
		return rel;
	}

	public static String wrapMidList(List<String> detailMidList) {
		if (detailMidList == null || !(detailMidList.size() > 0))
			return "('')";
		String imidStr = "(";
		for (Iterator<String> iter = detailMidList.iterator(); iter.hasNext();) {
			String str = iter.next();
			if (iter.hasNext()) {
				imidStr += "'" + str + "',";
			} else {
				imidStr += "'" + str + "')";
			}

		}
		return imidStr;
	}

	public static List<String> CoverToList(String data) {
		List<String> list = new ArrayList<String>();
		if (data != null && !data.trim().equals("")) {
			String datas[] = data.split(",");
			int num = datas.length;
			for (int i = 0; i < num; i++)
				list.add(datas[i]);
		}
		return list;
	}

	public static String removeEnd(String str, String remove) {
		if (str.equals("") || remove.equals(""))
			return str;
		if (str.endsWith(remove))
			return str.substring(0, str.length() - remove.length());
		else
			return str;
	}

	public static String convertBig5ToUnicode(Object obj) {
		try {
			return new String(ConvertToStr(obj).getBytes("Big5"), "ISO8859_1");
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String convertToUnicode(Object obj) {
		try {
			return new String(ConvertToStr(obj).getBytes(), "ISO8859_1");
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String convertUnicodeToBig5(Object obj) {
		try {
			return new String(ConvertToStr(obj).getBytes("ISO8859_1"), "Big5");
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String convertUnicodeToGbk(Object obj) {
		try {
			return new String(ConvertToStr(obj).getBytes("utf-8"), "GBK");
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String convertUnicodeToUtf8(Object obj) {
		try {
			return new String(ConvertToStr(obj).getBytes("ISO8859_1"), "utf-8");
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String convertUtf8ToUnicode(Object obj) {
		try {
			return new String(ConvertToStr(obj).getBytes("utf-8"), "ISO8859_1");
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String convertLatin1ToUtf8(Object obj) {
		try {
			return new String(ConvertToStr(obj).getBytes("Latin1"), "utf-8");
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String convertToUtf8(Object obj) {
		try {
			return new String(ConvertToStr(obj).getBytes(), "utf-8");
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String convertBig5ToUtf8(Object obj) {
		try {
			return new String(ConvertToStr(obj).getBytes("Big5"), "utf-8");
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String convertUtfToBig5(Object obj) {
		try {
			return new String(ConvertToStr(obj).getBytes("utf-8"), "Big5");
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String splitList(List<String> list) {
		String result = "";
		if (isEmpty(list)) {
			for (Iterator<String> iter = list.iterator(); iter.hasNext();) {
				String str = iter.next();
				result += str + "##";
			}
			result = removeEnd(result, "##");
		}
		return result;
	}

	/**
	 * 将list转换成,型的字符串
	 * 
	 * @param list
	 *            需要转换的list集合
	 * @return 转换后的字符串
	 * @see [类、类#方法、类#成员]
	 */
	public static String splitListByComma(List<String> list) {
		String result = "";
		if (null != list && list.size() > 0) {
			for (Iterator<String> iter = list.iterator(); iter.hasNext();) {
				String str = iter.next();
				result += str + ",";
			}
			result = removeEnd(result, ",");
		}
		return result;
	}

	public static void main(String args[]) {
		// String s = null;
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		System.out.println(splitListByComma(list));
	}

	/** 1、规避valueOf输出null；2、Number类型的Object转toString的格式 */
	public static String get(Object from) {
		String value = "";
		if (from != null) { // 2、未实现
			value = String.valueOf(from);
		}
		return value;
	}

	public static String replaceText(String text) {

		text = text.replace("\r\n", "<br/>&nbsp;&nbsp;");// 这才是正确的！
		text = text.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		text = text.replace(" ", "&nbsp;");
		return text;

	}

	public static String replaceToBean(String s) {
		int m = 0;
		// String s="meta_area_code";
		StringBuffer sb;
		int t = 0;
		while ((t = s.indexOf("_", m)) > 0) {
			sb = new StringBuffer();
			sb.append(s.substring(0, t));
			sb.append(s.substring(t + 1, t + 2).toUpperCase());
			sb.append(s.substring(t + 2, s.length()));
			m = t + 1;
			s = sb.toString();
		}
		return s;
	}

	public static String utf8Togb2312(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			switch (c) {
			case '+':
				sb.append(' ');
				break;
			case '%':
				try {
					sb.append((char) Integer.parseInt(
							str.substring(i + 1, i + 3), 16));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException();
				}
				i += 2;
				break;
			default:
				sb.append(c);
				break;
			}
		}
		// Undo conversion to external encoding
		String result = sb.toString();
		String res = null;
		try {
			byte[] inputBytes = result.getBytes("8859_1");
			res = new String(inputBytes, "UTF-8");
		} catch (Exception e) {
		}
		return res;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param s
	 *            字符串
	 * @return true : 为空 false:不为空
	 * @see [类、类#方法、类#成员]
	 */
	public static Boolean isStrEmpty(String s) {
		if (s != null && !s.isEmpty() && !s.equalsIgnoreCase(""))
			return false;
		else
			return true;

	}

	public static Boolean isStrEmpty(String[] s) {
		Boolean isEmpty = false;
		for (String str : s) {
			if (isStrEmpty(str))
				return true;
		}
		return isEmpty;
	}

	/**
	 * 数组字符串都不为空
	 * */
	public static Boolean isNotEmptyAll(String[] s) {
		Boolean isEmpty = true;
		for (String str : s) {
			if (isStrEmpty(str))
				return false;
		}
		return isEmpty;
	}

	/**
	 * 数组字符串都为空
	 * */
	public static Boolean isEmptyAll(String[] s) {
		Boolean isEmpty = true;
		for (String str : s) {
			if (!isStrEmpty(str))
				isEmpty = false;
		}
		return isEmpty;
	}

	public static String convertArrToStr(Object o[]) {
		StringBuffer sb = new StringBuffer();
		for (Object obj : o) {
			sb.append(obj + ",");
		}
		return sb.toString();
	}

	public static String convertArrToRawStr(Object o[]) {
		StringBuffer sb = new StringBuffer();
		for (Object obj : o) {
			sb.append(obj);
		}
		return sb.toString();
	}

	public static String fileLast(String name) {
		if (hasText(name))
			return "";
		int f = name.lastIndexOf(".");
		if (f == -1)
			return "";
		String fileName = name.substring(name.lastIndexOf("."));
		return fileName;
	}

	/**
	 * 将中文转为UTF-8
	 * 
	 * @param str
	 *            原中字符串
	 * @return UTF-8的字符串
	 * @see [类、类#方法、类#成员]
	 */
	public static String toUtf8String(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("UTF-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 处理文件或者目录路径，用户windows, linux兼容
	 * 
	 * @param path
	 */
	public static String validPath(String path) {
		String temp = path;
		/*
		 * temp = temp.replace("/", "\\"); temp = temp.replace("\\\\", "\\");
		 */
		temp = temp.replace("\\", "/");
		temp = temp.replace("//", "/");
		return temp;
	}

	/**
	 * @param bytes
	 * @return
	 */
	public static byte[] decode(final byte[] bytes) {
		return Base64.decodeBase64(bytes);
	}

	/**
	 * 二进制数据编码为BASE64字符串
	 * 
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	public static byte[] encode(final byte[] bytes) {
		return Base64.encodeBase64(bytes);
	}

	/**
	 * byte数组转long
	 * 
	 * @param bytes
	 * @return
	 * @description:
	 */
	public static long byteToLong(byte[] bytes) {
		if (bytes == null || bytes.length == 0)
			return -1;
		int ands = 0xFF;
		long num = bytes[0] & ands;
		for (int i = 1; i < bytes.length; i++) {
			ands <<= 8;
			num |= (bytes[i] << 8 * i) & ands;
		}
		return num;
	}

	/**
	 * byte数组转int
	 * 
	 * @param bytes
	 * @return
	 * @description:
	 */
	public static int byteToInt(byte[] bytes) {
		if (bytes == null || bytes.length == 0)
			return -1;
		int ands = 0xFF;
		int num = bytes[0] & ands;
		for (int i = 1; i < bytes.length; i++) {
			ands <<= 8;
			num |= (bytes[i] << 8 * i) & ands;
		}
		return num;
	}

	public static String bcd(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(Integer.toHexString(b & 0xFF));
		}
		return sb.toString();
	}

}
