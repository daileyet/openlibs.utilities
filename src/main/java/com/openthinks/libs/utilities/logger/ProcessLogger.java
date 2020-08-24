/**   
 *  Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
* @Title: ProcessLoger.java 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 11, 2015
* @version V1.2   
*/
package com.openthinks.libs.utilities.logger;

/**
 * Simple process logger<br>
 * please use {@link ProcessLogger2}
 * @author dailey.yet@outlook.com
 *
 */
public class ProcessLogger {
	public static PLLevel currentLevel = PLLevel.DEBUG;
	public static final PLLevel defaultLevel = PLLevel.INFO;
	private static ImplManager implManager = null;
	private static Impl impl = null;

	@Deprecated
	public static void setImplManager(ImplManager implManager) {
		ProcessLogger.implManager = implManager;
	}

	public static ImplManager getImplManager() {
		if (implManager == null) {
			implManager = RootLoggerManager.getInstance();
		}
		return implManager;
	}

	public static PLLevel currentLevel() {
		return currentLevel == null ? defaultLevel : currentLevel;
	}

	//////////////////////////////////// PRIVATE
	//////////////////////////////////// METHOD////////////////////////////////////////////////

	private static Impl getImpl() {
		if (impl == null) {
			impl = getImplManager().createImpl();
		}
		return impl;
	}

	private static void _trace(String pattern, Object... args) {
		getImpl().action(PLLevel.TRACE, pattern, args);
	}

	private static void _debug(String pattern, Object... args) {
		getImpl().action(PLLevel.DEBUG, pattern, args);
	}

	private static void _info(String pattern, Object... args) {
		getImpl().action(PLLevel.INFO, pattern, args);
	}

	private static void _warn(String pattern, Object... args) {
		getImpl().action(PLLevel.WARN, pattern, args);
	}

	private static void _error(String pattern, Object... args) {
		getImpl().action(PLLevel.ERROR, pattern, args);
	}

	private static void _fatal(String pattern, Object... args) {
		getImpl().action(PLLevel.FATAL, pattern, args);
	}

	private static void _trace(Exception... exs) {
		getImpl().action(PLLevel.TRACE, exs);
	}

	private static void _debug(Exception... exs) {
		getImpl().action(PLLevel.DEBUG, exs);
	}

	private static void _info(Exception... exs) {
		getImpl().action(PLLevel.INFO, exs);
	}

	private static void _warn(Exception... exs) {
		getImpl().action(PLLevel.WARN, exs);
	}

	private static void _error(Exception... exs) {
		getImpl().action(PLLevel.ERROR, exs);
	}

	private static void _fatal(Exception... exs) {
		getImpl().action(PLLevel.FATAL, exs);
	}

	//////////////////////////////// PUBLIC
	//////////////////////////////// METHOD////////////////////////////////////////////////////

	public static void log(PLLevel plevel, String pattern, Object... args) {
		switch (plevel) {
		case TRACE:
			trace(pattern, args);
			break;
		case DEBUG:
			debug(pattern, args);
			break;
		case INFO:
			info(pattern, args);
			break;
		case WARN:
			warn(pattern, args);
			break;
		case ERROR:
			error(pattern, args);
			break;
		case FATAL:
			fatal(pattern, args);
			break;
		default:
			break;
		}
	}

	public static void trace(String pattern, Object... args) {
		if (currentLevel().compareTo(PLLevel.TRACE) >= 0)
			_trace(pattern, args);
	}

	public static void debug(String pattern, Object... args) {
		if (currentLevel().compareTo(PLLevel.DEBUG) >= 0)
			_debug(pattern, args);
	}

	public static void info(String pattern, Object... args) {
		if (currentLevel().compareTo(PLLevel.INFO) >= 0)
			_info(pattern, args);
	}

	public static void warn(String pattern, Object... args) {
		if (currentLevel().compareTo(PLLevel.WARN) >= 0)
			_warn(pattern, args);
	}

	public static void error(String pattern, Object... args) {
		if (currentLevel().compareTo(PLLevel.ERROR) >= 0)
			_error(pattern, args);
	}

	public static void fatal(String pattern, Object... args) {
		if (currentLevel().compareTo(PLLevel.FATAL) >= 0)
			_fatal(pattern, args);
	}

	public static void trace(Exception... exs) {
		if (currentLevel().compareTo(PLLevel.TRACE) >= 0)
			_trace(exs);
	}

	public static void debug(Exception... exs) {
		if (currentLevel().compareTo(PLLevel.DEBUG) >= 0)
			_debug(exs);
	}

	public static void info(Exception... exs) {
		if (currentLevel().compareTo(PLLevel.INFO) >= 0)
			_info(exs);
	}

	public static void warn(Exception... exs) {
		if (currentLevel().compareTo(PLLevel.WARN) >= 0)
			_warn(exs);
	}

	public static void error(Exception... exs) {
		if (currentLevel().compareTo(PLLevel.ERROR) >= 0)
			_error(exs);
	}

	public static void fatal(Exception... exs) {
		if (currentLevel().compareTo(PLLevel.FATAL) >= 0)
			_fatal(exs);
	}

}
