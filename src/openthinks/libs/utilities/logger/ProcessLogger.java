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
* @version V1.0   
*/
package openthinks.libs.utilities.logger;

/**
 * Simple process logger
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

	public static void log(PLLevel plevel, String... msgs) {
		switch (plevel) {
		case DEBUG:
			_debug(msgs);
			break;
		case INFO:
			_info(msgs);
			break;
		case WARN:
			_warn(msgs);
			break;
		case ERROR:
			_error(msgs);
			break;
		case FATAL:
			_fatal(msgs);
			break;
		default:
			break;
		}
	}

	public static void debug(String... msgs) {
		if (currentLevel().compareTo(PLLevel.DEBUG) >= 0)
			_debug(msgs);
	}

	public static void info(String... msgs) {
		if (currentLevel().compareTo(PLLevel.INFO) >= 0)
			_info(msgs);
	}

	public static void warn(String... msgs) {
		if (currentLevel().compareTo(PLLevel.WARN) >= 0)
			_warn(msgs);
	}

	public static void error(String... msgs) {
		if (currentLevel().compareTo(PLLevel.ERROR) >= 0)
			_error(msgs);
	}

	public static void fatal(String... msgs) {
		if (currentLevel().compareTo(PLLevel.FATAL) >= 0)
			_fatal(msgs);
	}

	////////////////////////////////////////////////////////////////////////////////////

	private static void _debug(String... msgs) {
		getImplManager().createImpl().action(PLLevel.DEBUG, msgs);
	}

	private static void _info(String... msgs) {
		getImplManager().createImpl().action(PLLevel.INFO, msgs);
	}

	private static void _warn(String... msgs) {
		getImplManager().createImpl().action(PLLevel.WARN, msgs);
	}

	private static void _error(String... msgs) {
		getImplManager().createImpl().action(PLLevel.ERROR, msgs);
	}

	private static void _fatal(String... msgs) {
		getImplManager().createImpl().action(PLLevel.FATAL, msgs);
	}

	////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Process Logger level
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
		/**
		 * log action
		 * @param level PLLevel
		 * @param msgs String[]
		 */
		public void action(PLLevel level, String... msgs);
	}

	private static class ConsoleImpl implements Impl {

		@Override
		public void action(PLLevel level, String... msgs) {
			switch (level) {
			case FATAL:
			case ERROR:
				System.err.print(level.name() + "=>");
				for (String msg : msgs) {
					System.err.print(msg);
					System.err.print("  ");
				}
				break;
			case WARN:
			case INFO:
			case DEBUG:
				System.out.print(level.name() + "=>");
				for (String msg : msgs) {
					System.out.print(msg);
					System.out.print("  ");
				}
				break;
			}
			System.err.println();
		}
	}
}