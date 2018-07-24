package com.openthinks.libs.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName: DateFormatUtil <br>
 * Function: format date to string or parse string to date. <br>
 * Reason: {@link DateFormat} has thread issue in multi-thread situation, use
 * {@link ThreadLocal} to make sure each thread use one instance of
 * {@link DateFormat}. <br>
 * 
 * @author dailey.yet@outlook.com
 */
public final class DateFormatUtil {

	private static final Map<String, ThreadLocal<DateFormat>> formatCache = new ConcurrentHashMap<>();

	/**
	 * 
	 * getDateFormat:get or create {@link DateFormat} which bind to current thread.
	 * <br>
	 * 
	 * @param pattern
	 *            format pattern
	 * @return {@link DateFormat}
	 * @throws NullPointerException
	 *             - if the given pattern is null
	 * @throws IllegalArgumentException
	 *             - if the given pattern is invalid
	 */
	public static final DateFormat getDateFormat(String pattern) {
		ThreadLocal<DateFormat> threadLocal = formatCache.get(pattern);
		if (threadLocal == null) {
			synchronized (DateFormatUtil.class) {
				threadLocal = formatCache.get(pattern);
				if (threadLocal == null) {
					threadLocal = new ThreadLocal<DateFormat>() {
						@Override
						protected DateFormat initialValue() {
							return new SimpleDateFormat(pattern);
						}
					};
					formatCache.put(pattern, threadLocal);
				}
			}
		}
		return threadLocal.get();
	}

	public static final String format(String pattern, Date date) {
		return getDateFormat(pattern).format(date);
	}

	public static final Date parse(String pattern, String dateString) throws ParseException {
		return getDateFormat(pattern).parse(dateString);
	}
}
