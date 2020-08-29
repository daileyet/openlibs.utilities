/**
 * 
 */
package com.openthinks.libs.utilities.logger;

/**
 * @author dailey.yet@outlook.com
 *
 */
public final class ProcessLogger2Factory {

	public static ProcessLogger2 getLogger(ImplManager implManager) {
		return new ProcessLogger2(implManager);
	}

	public static ProcessLogger2 getRootLogger() {
		return getLogger(RootLoggerManager.getInstance());
	}
}
