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

import java.text.MessageFormat;

/**
 * Simple process logger
 * 
 * @author dailey.yet@outlook.com
 *
 */
public class ProcessLogger {
	public static PLLevel currentLevel = PLLevel.DEBUG;
	public static final PLLevel defaultLevel = PLLevel.INFO;
	private static ImplManager implManager = null;

	public static void setImplManager(ImplManager implManager) {
		ProcessLogger.implManager = implManager;
	}

	public static ImplManager getImplManager() {
		if (implManager == null)
			implManager = new ImplManager() {
				@Override
				public Impl createImpl() {
					return new ConsoleImpl();
				}
			};
		return implManager;
	}

	public static PLLevel currentLevel() {
		return currentLevel == null ? defaultLevel : currentLevel;
	}

	////////////////////////////////////PRIVATE METHOD////////////////////////////////////////////////
	private static void _debug(String pattern, Object... args) {
		getImplManager().createImpl().action(PLLevel.INFO, pattern, args);
	}

	private static void _info(String pattern, Object... args) {
		getImplManager().createImpl().action(PLLevel.INFO, pattern, args);
	}

	private static void _warn(String pattern, Object... args) {
		getImplManager().createImpl().action(PLLevel.WARN, pattern, args);
	}

	private static void _error(String pattern, Object... args) {
		getImplManager().createImpl().action(PLLevel.ERROR, pattern, args);
	}

	private static void _fatal(String pattern, Object... args) {
		getImplManager().createImpl().action(PLLevel.FATAL, pattern, args);
	}

	private static void _debug(Exception... exs) {
		getImplManager().createImpl().action(PLLevel.DEBUG, exs);
	}

	private static void _info(Exception... exs) {
		getImplManager().createImpl().action(PLLevel.INFO, exs);
	}

	private static void _warn(Exception... exs) {
		getImplManager().createImpl().action(PLLevel.WARN, exs);
	}

	private static void _error(Exception... exs) {
		getImplManager().createImpl().action(PLLevel.ERROR, exs);
	}

	private static void _fatal(Exception... exs) {
		getImplManager().createImpl().action(PLLevel.FATAL, exs);
	}

	////////////////////////////////PUBLIC METHOD////////////////////////////////////////////////////

	public static void log(PLLevel plevel, String pattern, Object... args) {
		switch (plevel) {
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
	////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Process Logger level
	 * 
	 * @author dailey.yet@outlook.com
	 *
	 */
	public enum PLLevel {
		FATAL, ERROR, WARN, INFO, DEBUG;

		public static PLLevel build(String level) {
			try {
				return PLLevel.valueOf(level);
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 
	 * @author dailey.yet@outlook.com
	 *
	 */
	public static abstract class ImplManager {

		/**
		 * get the instance of {@link Impl}
		 * 
		 * @return Impl
		 */
		public abstract Impl createImpl();
	}

	/**
	 * 
	 * @author dailey.yet@outlook.com
	 *
	 */
	public static interface Impl {

		public void action(PLLevel level, String pattern, Object... arguments);

		public void action(PLLevel level, Exception... exs);

	}

	private static class ConsoleImpl implements Impl {

		@Override
		public void action(PLLevel level, String pattern, Object... arguments) {
			String msg = "";
			switch (level) {
			case FATAL:
			case ERROR:
				System.err.print(level.name() + "=>");
				try {
					msg = MessageFormat.format(pattern, arguments);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.err.print(msg);
				break;
			case WARN:
			case INFO:
			case DEBUG:
				System.out.print(level.name() + "=>");
				try {
					msg = MessageFormat.format(pattern, arguments);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.print(msg);
			}
			System.out.println();

		}

		@Override
		public void action(PLLevel level, Exception... exs) {
			Exception[] _exs = exs == null ? new Exception[0] : exs;
			switch (level) {
			case FATAL:
			case ERROR:
				System.err.println(level.name() + "=>");
				for (Exception ex : _exs) {
					ex.printStackTrace(System.err);
				}
				break;
			case WARN:
			case INFO:
			case DEBUG:
				System.out.println(level.name() + "=>");
				for (Exception ex : _exs) {
					ex.printStackTrace(System.out);
				}
				break;
			}
			System.out.println();

		}
	}

}
