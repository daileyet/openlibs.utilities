package com.openthinks.libs.utilities.logger;

/**
 * 
 * @author dailey.yet@outlook.com
 *
 */
public abstract class ImplManager {

	/**
	 * get the instance of {@link Impl}
	 * 
	 * @return Impl
	 */
	public abstract Impl createImpl();
	
	public PLLevel entryLevel() {
		return PLLevel.INFO;
	}
}