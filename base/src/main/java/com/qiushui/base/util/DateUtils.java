package com.qiushui.base.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.qiushui.base.exception.BusinessException;

/**
 * 日期时间工具类。
 */
public class DateUtils {
	public static final String MONTH = "yyyy-MM";
	public static final String DAY = "yyyy-MM-dd";
	public static final String MINUTE = "yyyy-MM-dd HH:mm";
	public static final String SECOND = "yyyy-MM-dd HH:mm:ss";
	public static final String MILLISECOND = "yyyy-MM-dd HH:mm:ss SSSS";

	public static final String MONTH_N = "yyyyMM";
	public static final String DAY_N = "yyyyMMdd";
	public static final String MINUTE_N = "yyyyMMddHHmm";
	public static final String SECOND_N = "yyyyMMddHHmmss";
	public static final String MILLISECOND_N = "yyyyMMddHHmmssSSSS";
	public static Calendar calendar = Calendar.getInstance();
	public static final String YEAR = "year";
	/**
	 * yyyy/MM/dd
	 */
	public static final String DATE_WITH_SLASH = "yyyy/MM/dd";
	/**
	 * yyyyMMdd HH:mm:ss
	 */
	public static final String DATE_TIME_NORMAL = "yyyyMMdd HH:mm:ss";
	/**
	 * yyyy/MM/dd HH:mm:ss
	 */
	public static final String DATE_TIME_WITH_SLASH = "yyyy/MM/dd HH:mm:ss";
	/**
	 * yyyy年MM月dd日
	 */
	public static final String DATE_WITH_CH = "yyyy年MM月dd日";
	/**
	 * yyyy年MM月dd日HH时mm分ss秒
	 */
	public static final String DATE_TIME_WITH_CH = "yyyy年MM月dd日HH时mm分ss秒";
	/**
	 * HH小时mm分钟ss秒
	 */
	public static final String TIME_WITH_CH = "HH时mm分ss秒";
	/**
	 * HH:mm:ss
	 */
	public static final String TIME_NORMAL = "HH:mm:ss";
	public static final String[] FORMATS = new String[] { DAY, MONTH, MINUTE,
			SECOND, MILLISECOND, DAY_N, MONTH_N, MINUTE_N, SECOND_N,
			MILLISECOND_N };

	/**
	 * 将字符串解析成Date对象。<br/>
	 * 该方法尝试用[yyyy-MM/yyyy-MM-dd/ yyyy-MM-dd HH:mm/yyyy-MM-dd
	 * HH:mm:ss/yyyy-MM-dd HH:mm:ss SSSS/ yyyyMM/yyyyMMdd/yyyyMMddHHmm/
	 * yyyyMMddHHmmss/yyyyMMddHHmmssSSSS]格式进行解析，如果无法解析将抛出异常。
	 * 
	 * @param str
	 *            字符串
	 * @return 返回对应的Date对象。
	 */
	public static Date parse(String str) {
		String pattern = getDateFormat(str);
		return parse(str, pattern);
	}

	/**
	 * 将指定格式的字符串解析成Date对象。
	 * 
	 * @param str
	 *            字符串
	 * @param format
	 *            格式
	 * @return 返回对应的Date对象。
	 */
	public static Date parse(String str, String format) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
		return DateTime.parse(str, formatter).toDate();
	}

	/**
	 * 将Date对象解析成yyyy-MM-dd格式的字符串。
	 * 
	 * @param date
	 *            Date对象
	 * @return 返回yyyy-MM-dd格式的字符串。
	 */
	public static String format(Date date) {
		return format(date, DAY);
	}

	/**
	 * 将Date对象解析成指定格式的字符串。
	 * 
	 * @param date
	 *            Date对象
	 * @param pattern
	 *            格式
	 * @return 返回指定格式的字符串。
	 */
	public static String format(Date date, String pattern) {
		return new DateTime(date).toString(pattern);
	}

	/**
	 * 获取字符串的日期格式。如果字符串不在[{@link #MONTH}/{@link #DAY}/ {@link #MINUTE} /
	 * {@link #SECOND}/{@link #MILLISECOND} ]格式范围内将抛出异常。
	 * 
	 * @param str
	 *            字符串
	 * @return 返回字符串的日期格式。
	 */
	public static String getDateFormat(String str) {
		for (String format : FORMATS) {
			if (isDate(str, format)) {
				return format;
			}
		}
		throw new IllegalArgumentException("不支持的日期格式：" + str);
	}

	/**
	 * 判断字符串是否为日期格式的字符串。
	 * 
	 * @param str
	 *            字符串
	 * @return 如果是日期格式的字符串返回true，否则返回false。
	 */
	public static Boolean isDate(String str) {
		for (String format : FORMATS) {
			if (isDate(str, format)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断字符串是否为指定日期格式的字符串。
	 * 
	 * @param str
	 *            字符串
	 * @param format
	 *            日期格式
	 * @return 如果是指定日期格式的字符串返回true，否则返回false。
	 */
	public static Boolean isDate(String str, String format) {
		try {
			parse(str, format);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取当前日期（只取到日期，时间部分都为0）。
	 * 
	 * @return 返回当前日期。
	 */
	public static Date getToday() {
		return DateTime.now().toLocalDate().toDate();
	}

	/**
	 * 获取下一天日期。（只取到日期，时间部分都为0）。
	 * 
	 * @return 返回下一天日期。
	 */
	public static Date getNextDay() {
		return getNextDay(getToday());
	}

	/**
	 * 获取指定日期的下一天日期。
	 * 
	 * @param date
	 *            指定日期
	 * @return 返回指定日期的下一天日期。
	 */
	public static Date getNextDay(Date date) {
		return new DateTime(date).plusDays(1).toLocalDate().toDate();
	}

	/**
	 * 获取最小日期。
	 * 
	 * @return 返回"0000-01-01"的日期。
	 */
	public static Date getMinDate() {
		return parse("0000-01-01");
	}

	/**
	 * 获取最大日期。
	 * 
	 * @return 返回"9999-12-31"的日期。
	 */
	public static Date getMaxDate() {
		return parse("9999-12-31");
	}

	/**
	 * 获取Joda Time的Duration对象。
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 返回Joda Time的Duration对象。
	 */
	public static Duration getDuration(Date beginDate, Date endDate) {
		return new Duration(new DateTime(beginDate), new DateTime(endDate));
	}

	/**
	 * 获取Joda Time的Peroid对象。
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 返回Joda Time的Peroid对象。
	 */
	public static Period getPeriod(Date beginDate, Date endDate) {
		return new Period(new DateTime(beginDate), new DateTime(endDate));
	}

	/**
	 * 获取Joda Time的Interval对象。
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 返回Joda Time的Interval对象。
	 */
	public static Interval getInterval(Date beginDate, Date endDate) {
		return new Interval(new DateTime(beginDate), new DateTime(endDate));
	}

	public static String timestampToString(Timestamp tim) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 定义格式，不显示毫秒
		String str = "";
		try {
			str = df.format(tim);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public static Timestamp dateStringToTimestamp(String tim) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setLenient(false);
		// 要转换字符串 str_test
		Timestamp ts = null;
		try {
			ts = new Timestamp(format.parse(tim + " 00:00:00").getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ts;
	}

	public static Date getFutureDateExact(int day, int hours, int minutes,
			int seconds, boolean flag) {
		Date date0 = null;
		Date date1 = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		calendar.add(Calendar.DATE, day);
		calendar.set(Calendar.HOUR_OF_DAY, hours);
		calendar.set(Calendar.MINUTE, minutes);
		calendar.set(Calendar.SECOND, seconds);
		date0 = calendar.getTime();
		if (flag == true) {
			if (date0.before(date1)) {
				calendar.add(Calendar.DATE, 1);
				date0 = calendar.getTime();
			}
		}
		return date0;
	}

	public static Date getFutureDate(Date date, int year, int month, int day,
			int hours, int minutes, int seconds) {
		Date date0 = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, year);
		calendar.add(Calendar.MONTH, month);
		calendar.add(Calendar.DATE, day);
		calendar.add(Calendar.HOUR_OF_DAY, hours);
		calendar.add(Calendar.MINUTE, minutes);
		calendar.add(Calendar.SECOND, seconds);
		date0 = calendar.getTime();
		return date0;
	}

	public static Date getFutureDateExact(Date date, int day, int hours,
			int minutes, int seconds) {
		Date date0 = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		calendar.set(Calendar.HOUR_OF_DAY, hours);
		calendar.set(Calendar.MINUTE, minutes);
		calendar.set(Calendar.SECOND, seconds);
		date0 = calendar.getTime();
		return date0;
	}

	public static Date getFutureDateExact(Date date, int month, int day,
			int hours, int minutes, int seconds) {
		Date date0 = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		calendar.add(Calendar.DATE, day);
		calendar.set(Calendar.HOUR_OF_DAY, hours);
		calendar.set(Calendar.MINUTE, minutes);
		calendar.set(Calendar.SECOND, seconds);
		date0 = calendar.getTime();
		return date0;
	}

	public static String getFutureDateExact(Date date, int day, String regula) {
		Date date0 = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day);
		date0 = calendar.getTime();
		return GetDateString(date0, regula);
	}

	public static String getFutureDateExact(Integer day, String regula) {
		Date date0 = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, day);
		date0 = calendar.getTime();
		return GetDateString(date0, regula);
	}

	public static Date getFutureDateExact(Date date, int seconds) {
		Date date0 = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.SECOND, seconds);
		date0 = calendar.getTime();
		return date0;
	}

	public static Date GetDate(String time, String regula) throws Exception {
		SimpleDateFormat df2 = new SimpleDateFormat(regula);
		Date date = df2.parse(time);
		return date;
	}

	public static Date GetNextDate(String time, String regula) throws Exception {
		return GetNextDate(GetDate(time, regula));
	}

	public static Date GetNextDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();

	}

	public static String GetNextDate(String time, String inFormat,
			String outFormat) throws Exception {
		return FormatDate(GetNextDate(time, inFormat), outFormat);
	}

	public static String GetNextDateStr(Date date, String outFormat) {
		return FormatDate(GetNextDate(date), outFormat);
	}

	public static Date GetFirstDate(String time, String regula)
			throws Exception {
		return GetFirstDate(GetDate(time, regula));
	}

	public static Date GetFirstDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static String GetFirstDate(String time, String inFormat,
			String outFormat) throws Exception {
		return FormatDate(GetFirstDate(time, inFormat), outFormat);
	}

	public static String GetFirstDateStr(Date date, String regula) {
		return FormatDate(GetFirstDate(date), regula);
	}

	public static Date StrConvertToDate(String time1, String regula) {
		Date date = null;
		SimpleDateFormat df2 = new SimpleDateFormat(regula);
		try {
			date = df2.parse(time1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date ConvertToDate(Object obj, String regula) {
		SimpleDateFormat df2 = new SimpleDateFormat(regula);
		Date date = null;
		try {
			if (obj == null)
				date = new Date();
			else if (obj.getClass() == String.class)
				date = df2.parse((String) obj);
			else if (obj.getClass() == Timestamp.class)
				date = new Date(((Timestamp) obj).getTime());
			else if (obj.getClass() == java.sql.Date.class)
				date = new Date(((java.sql.Date) obj).getTime());
			else if (obj.getClass() == Date.class)
				date = (Date) obj;
			else
				date = new Date();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date ConvertToDate(Object obj) {
		Date date = null;
		try {
			if (obj == null)
				date = new Date();
			else if (obj.getClass() == Timestamp.class)
				date = new Date(((Timestamp) obj).getTime());
			else if (obj.getClass() == java.sql.Date.class)
				date = new Date(((java.sql.Date) obj).getTime());
			else if (obj.getClass() == Date.class)
				date = (Date) obj;
			else
				date = new Date();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String GetDateString(Object date, String regula) {
		if (date == null)
			return "";
		SimpleDateFormat df2 = new SimpleDateFormat(regula);
		return df2.format(date);
	}

	public static boolean compareDate(String time1, String time2) {
		try {
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			if (date1.before(date2) || date1.equals(date2))
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static int compareDates(String time1, String time2, String regula) {
		int i = 0;
		try {
			SimpleDateFormat ft = new SimpleDateFormat(regula);
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			if (date1.before(date2))
				i = -1;
			else if (date1.equals(date2))
				i = 0;
			else if (i == 1)
				i = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	public static boolean IsInRange(String begin, String end, String time) {
		try {
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = ft.parse(begin);
			Date date2 = ft.parse(end);
			Date date3;
			if (time.equals(""))
				date3 = ft.parse(getCurrentDate("yyyy-MM-dd"));
			else
				date3 = ft.parse(time);

			if ((date3.after(date1) && date3.before(date2))
					|| date3.equals(date1) || date3.equals(date2))
				return true;
			else
				return false;
		} catch (Exception e) {

		}
		return false;
	}

	public static boolean IsInRange(Date begin, Date end, Date time) {
		try {
			if ((time.after(begin) && time.before(end))
					|| time.compareTo(begin) == 0 || time.compareTo(end) == 0)
				return true;
			else
				return false;
		} catch (Exception e) {
		}
		return false;
	}

	public static String getCurrentDate(String regula) {
		Date date = new Date();
		SimpleDateFormat df2 = new SimpleDateFormat(regula);
		return df2.format(date);
	}

	public static String getFutureDate(int day, String regula) {
		// date.setDate(date.getDay() + day);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, day);

		SimpleDateFormat df2 = new SimpleDateFormat(regula);
		return df2.format(calendar.getTime());
	}

	public static String getFutureDate(int day, String date, String regula) {
		SimpleDateFormat df2 = new SimpleDateFormat(regula);
		Calendar calendar = Calendar.getInstance();
		try {
			Date date1 = df2.parse(date);
			calendar.setTime(date1);
			calendar.add(Calendar.DATE, day);
			return df2.format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return df2.format(calendar.getTime());
	}

	public static Date getFutureDate(int day) {
		Date date = null;
		Calendar calendar = Calendar.getInstance();
		try {
			Date date1 = new Date();
			calendar.setTime(date1);
			calendar.add(Calendar.DATE, day);
			date = calendar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date getFutureDate(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(date);
			calendar.add(Calendar.DATE, day);
			date = calendar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getFutureMonthDate(int day, String regula) {
		// date.setDate(date.getDay() + day);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MARCH, day);
		SimpleDateFormat df2 = new SimpleDateFormat(regula);
		return df2.format(calendar.getTime());
	}

	public static String FormatDate(Date date, String regula1) {
		String result = "";
		try {
			if (date != null) {
				SimpleDateFormat df1 = new SimpleDateFormat(regula1);
				result = df1.format(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取指定月的第一天
	 * 
	 * @param month
	 * @return
	 * @throws Exception
	 */
	public static Date getNextMonthFirstDay(String month) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(month + "-01"));
		cal.add(Calendar.MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 获取指定月的下一月的第一天
	 * 
	 * @param month
	 * @return
	 * @throws Exception
	 */
	public static Date getCurrentMonthFirstDay(String month) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(month + "-01");
	}

	/**
	 * 获取当前月的第一天
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Date getCurrentMonthFirstDay() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		return getCurrentMonthFirstDay(sdf.format(new Date()));
	}

	/**
	 * 获取当前月的下一个月的第一天
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Date getNextMonthFirstDay() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		return getCurrentMonthFirstDay(sdf.format(new Date()));
	}

	/**
	 * 获取开始时间和结束时间的差值
	 * 
	 * @param endDate
	 *            结束时间
	 * @param beginDate
	 *            开始时间
	 * @return 时间间隔数 毫秒
	 */
	public static long getDateInterval(Date endDate, Date beginDate) {
		return endDate.getTime() - beginDate.getTime();
	}

	/**
	 * 分钟转hh:mm
	 * 
	 * @param minute
	 * @return
	 */
	public static String minuteToTimeStr(int minute) {
		if (minute < 0)
			return null;
		String hour = StringUtils.leftPad(minute / 60 + "", 2, "0");
		String mi = StringUtils.leftPad(minute % 60 + "", 2, "0");
		return hour + ":" + mi;
	}

	/**
	 * HH:mm转分钟
	 * 
	 * @param time
	 * @return
	 */
	public static int timeStrToMinute(String time) {
		String[] ts = time.split(":");
		return Integer.parseInt(ts[0]) * 60 + Integer.parseInt(ts[1]);
	}

	/**
	 * 时间段是否包含指定星期，1表示星期一
	 * 
	 * @param start
	 * @param end
	 * @param weeks
	 * @return
	 */
	public static boolean isWeeksInDate(Date start, Date end, String[] weeks) {
		Set<String> weekset = new HashSet<String>();
		while (!start.after(end)) {
			calendar.setTime(start);
			weekset.add(String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)));
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			start = calendar.getTime();
		}
		if (weeks == null)
			weeks = new String[] { "1", "2", "3", "4", "5", "6", "7" };
		for (String w : weeks) {
			if (weekset.contains(w))
				return true;
		}
		return false;
	}

	public synchronized static int getWeekOrder(Date date) {
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 根据用户输入的格式将字符串转化成日期格式
	 * 
	 * @param date
	 * @param format日期格式
	 * @return
	 * @throws DateException
	 */
	public static Date stringToDate(String date, String format) {
		Date newDate = null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			newDate = simpleDateFormat.parse(date);
		} catch (Exception e) {
			throw new BusinessException("日期格式有误，不能转换:" + e.getMessage());
		}
		return newDate;
	}

	/**
	 * 根据用户输入的格式和日期，得到字符串类型日期
	 * 
	 * @param date
	 * @param format日期格式
	 * @return
	 * @throws DateException
	 */
	public static String dateToString(Date date, String format) {
		String newDate = null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			newDate = simpleDateFormat.format(date);
		} catch (Exception e) {
			throw new BusinessException("日期格式有误，不能转换:" + e.getMessage());
		}
		return newDate;
	}

	/**
	 * 毫秒转时间
	 * 
	 * @param millis
	 * @param format
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String millisToString(long millis, String format) {
		try {
			long secondTime = millis / 1000;
			int second = (int) (secondTime % 60);
			int minute = (int) (secondTime / 60 % 60);
			int hour = (int) (secondTime / 60 / 60 % 24);
			int day = (int) (secondTime / 60 / 60 / 24 % 30);
			int month = (int) (secondTime / 60 / 60 / 24 / 30 % 12);
			int year = (int) (secondTime / 60 / 60 / 24 / 30 / 12);
			if (TIME_WITH_CH.equals(format)) {
				StringBuilder t = new StringBuilder();
				t.append(year == 0 ? "" : year + "年");
				t.append(month == 0 ? "" : month + "个月");
				t.append(day == 0 ? "" : day + "天");
				t.append(hour == 0 ? "" : hour + "小时");
				t.append(minute == 0 ? "" : minute + "分钟");
				t.append(second == 0 ? "" : second + "秒");
				return t.toString();
			} else {
				Time time = new Time(hour, minute, second);
				return time.toString();
			}
		} catch (Exception e) {
			throw new BusinessException("参数有误:" + e.getMessage());
		}
	}

	/**
	 * 获得当前时间若干天前或后的日期
	 * 
	 * @param date
	 * @param days正数表示之后
	 *            ，负数表示之前
	 * @return
	 */
	public static synchronized Date rollByDay(Date date, int days) {
		try {
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_YEAR, days);
		} catch (Exception e) {
			throw new BusinessException("参数有误:" + e.getMessage());
		}
		return calendar.getTime();
	}

	/**
	 * 获得当前时间若干月前或后的日期
	 * 
	 * @param date
	 * @param days正数表示之后
	 *            ，负数表示之前
	 * @return
	 */
	public static synchronized Date rollByMonth(Date date, int months) {
		try {
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, months);
		} catch (Exception e) {
			throw new BusinessException("参数有误:" + e.getMessage());
		}
		return calendar.getTime();
	}

	/**
	 * 获得当前时间若干年前或后的日期
	 * 
	 * @param date
	 * @param days正数表示之后
	 *            ，负数表示之前
	 * @return
	 */
	public static synchronized Date rollYear(Date date, int years) {
		try {
			calendar.setTime(date);
			calendar.add(Calendar.YEAR, years);
		} catch (Exception e) {
			throw new BusinessException("参数有误:" + e.getMessage());
		}
		return calendar.getTime();
	}

	/**
	 * 获得当前时间若干小时前或后的日期
	 * 
	 * @param date
	 * @param days正数表示之后
	 *            ，负数表示之前
	 * @return
	 */
	public static synchronized Date rollHour(Date date, int hours) {
		try {
			calendar.setTime(date);
			calendar.add(Calendar.HOUR, hours);
		} catch (Exception e) {
			throw new BusinessException("参数有误:" + e.getMessage());
		}
		return calendar.getTime();
	}

	/**
	 * 获得当前时间若干分前或后的日期
	 * 
	 * @param date
	 * @param days正数表示之后
	 *            ，负数表示之前
	 * @return
	 */
	public static synchronized Date rollMinute(Date date, int minutes) {
		try {
			calendar.setTime(date);
			calendar.add(Calendar.MINUTE, minutes);
		} catch (Exception e) {
			throw new BusinessException("参数有误:" + e.getMessage());
		}
		return calendar.getTime();
	}

	/**
	 * 获得当前时间若干秒前或后的日期
	 * 
	 * @param date
	 * @param days正数表示之后
	 *            ，负数表示之前
	 * @return
	 */
	public static synchronized Date rollSecond(Date date, int seconds) {
		try {
			calendar.setTime(date);
			calendar.add(Calendar.SECOND, seconds);
		} catch (Exception e) {
			throw new BusinessException("参数有误:" + e.getMessage());
		}
		return calendar.getTime();
	}

	/**
	 * 根据格式从字符串中查找相应格式的日期字符串
	 * 
	 * @param src
	 * @param format
	 * @return 字符串日期
	 */
	public static String stringFindDateString(String src, String format) {
		try {
			Matcher matcher = null;
			if (DAY_N.equalsIgnoreCase(format)) {
				matcher = Pattern.compile("(19|20)\\d{2}[01][0-9][0123][0-9]")
						.matcher(src);
				matcher.find();
			} else if (MONTH_N.equalsIgnoreCase(format)) {
				matcher = Pattern.compile("(19|20)\\d{2}[01][0-9]")
						.matcher(src);
				matcher.find();
			} else if (DATE_WITH_SLASH.equalsIgnoreCase(format)) {
				matcher = Pattern.compile(
						"(19|20)\\d{2}[/][01][0-9][/][0123][0-9]").matcher(src);
				matcher.find();
			} else if (DAY.equalsIgnoreCase(format)) {
				matcher = Pattern.compile(
						"(19|20)\\d{2}[-][01][0-9][-][0123][0-9]").matcher(src);
				matcher.find();
			} else if (DATE_TIME_NORMAL.equalsIgnoreCase(format)) {
				matcher = Pattern
						.compile(
								"(19|20)\\d{2}[01][0-9][0123][0-9][ ][012][0-9][:][0-6][0-9][:][0-6][0-9]")
						.matcher(src);
				matcher.find();
			} else if (DATE_TIME_WITH_SLASH.equalsIgnoreCase(format)) {
				matcher = Pattern
						.compile(
								"(19|20)\\d{2}[/][01][0-9][/][0123][0-9][ ][012][0-9][:][0-6][0-9][:][0-6][0-9]")
						.matcher(src);
				matcher.find();
			} else if (SECOND.equalsIgnoreCase(format)) {
				matcher = Pattern
						.compile(
								"(19|20)\\d{2}[-][01][0-9][-][0123][0-9][ ][012][0-9][:][0-6][0-9][:][0-6][0-9]")
						.matcher(src);
				matcher.find();
			} else if (SECOND_N.equalsIgnoreCase(format)) {
				matcher = Pattern
						.compile(
								"(19|20)\\d{2}[01][0-9][0123][0-9][012][0-9][0-6][0-9][0-6][0-9]")
						.matcher(src);
				matcher.find();
			} else if (DATE_WITH_CH.equalsIgnoreCase(format)) {
				matcher = Pattern.compile(
						"(19|20)\\d{2}年[01][0-9]月[0123][0-9]日").matcher(src);
				matcher.find();
			} else if (DATE_TIME_WITH_CH.equalsIgnoreCase(format)) {
				matcher = Pattern
						.compile(
								"(19|20)\\d{2}年[01][0-9]月[0123][0-9]日[ ]?[012][0-9]时[0-6][0-9]分[0-6][0-9]秒")
						.matcher(src);
				matcher.find();
			}
			return matcher.group();
		} catch (IllegalStateException e) {
			return "";
		} catch (NullPointerException e) {
			return "";
		} catch (Exception e) {
			throw new BusinessException("参数有误" + e.getMessage());
		}
	}

	/**
	 * 字符串中查找日期格式为yyyyMMdd
	 * 
	 * @param src
	 * @throws BusinessException
	 * @return返回日期格式
	 */
	public static Date stringFindDate(String src, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String dateStr = stringFindDateString(src, format);
		Date date = null;
		try {
			date = dateFormat.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("日期格式有误，不能转换:" + e.getMessage());
		}
		return date;
	}

	/**
	 * 获取系统时间的毫秒数
	 * 
	 * @return
	 */
	public static long receiveSysTime() {
		return new Date().getTime();
	}

	/**
	 * 在当前系统时间的基础添加分钟
	 * 
	 * @param minute
	 * @return
	 */
	public static synchronized Date addMuite(int minute) {
		calendar.add(Calendar.MINUTE, 30);
		Date date = new Date(calendar.getTimeInMillis());
		return date;
	}

	/**
	 * 比较时间
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isDateBefore(String date1, String date2) {
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			return df.parse(date1).before(df.parse(date2));
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 时间比较,如果before比end晚,则返回false
	 * 
	 * @param before
	 *            参数1
	 * @param end
	 *            参数2
	 * @return
	 */
	public static boolean compareDate(Date before, Date end) {
		if (before == null || end == null) {
			return false;
		}
		if (before.getTime() > end.getTime()) {
			return false;
		}
		return true;
	}
}