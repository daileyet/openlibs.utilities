package com.openthinks.libs.utilities.logger;

/**
 * Process Logger level
 * 
 * @author dailey.yet@outlook.com
 *
 */
public enum PLLevel {
	FATAL, ERROR, WARN, INFO, DEBUG,TRACE;

	public static PLLevel build(String level) {
		try {
			return PLLevel.valueOf(level);
		} catch (Exception e) {
			return null;
		}
	}
}