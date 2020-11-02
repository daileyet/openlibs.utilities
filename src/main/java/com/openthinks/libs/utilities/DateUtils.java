package com.openthinks.libs.utilities;

import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.TimeZone;

/**
 * ClassName: DateUtils <br>
 * date: Aug 7, 2018 9:30:44 AM <br>
 * 
 * @since JDK 1.8
 */
public class DateUtils {

	private static String tz = "Asia/Shanghai";
	private static TimeZone timeZone;

	static {
		timeZone = TimeZone.getTimeZone(tz);
	}

	protected DateUtils() {
	}

	public static final TimeZone getTimeZone() {
		return timeZone;
	}

	public static Date now() {
		return new Date();
	}

	public static Instant instantPlus(Instant target, long amountToAdd, TemporalUnit unit) {
		return target.plus(amountToAdd, unit);
	}

	public static Instant instantMinus(Instant target, long amountToSubtract, TemporalUnit unit) {
		return target.minus(amountToSubtract, unit);
	}

	public static Date offset(Date date, long millseconds) {
		date.setTime(date.getTime() + millseconds);
		return date;
	}

	public static Date offset(long millseconds) {
		return offset(now(), millseconds);
	}

	public final static long systemUpMillseconds() {
		return System.nanoTime() / 1000000;
	}

	public static long currentTimeMillis() {
		return System.currentTimeMillis();
	}

	public static Instant toInstant(long time) {
		return Instant.ofEpochMilli(time);
	}
}
