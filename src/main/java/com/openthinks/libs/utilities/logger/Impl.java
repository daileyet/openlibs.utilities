package com.openthinks.libs.utilities.logger;

/**
 * 
 * @author dailey.yet@outlook.com
 *
 */
public interface Impl {

	public void action(PLLevel level, String pattern, Object... arguments);

	public void action(PLLevel level, Exception... exs);

}