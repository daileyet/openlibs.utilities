/**
 * 
 */
package com.openthinks.libs.utilities.logger;

/**
 * single instance of Logger interface
 * @author dailey.yet@outlook.com
 *
 */
public class ProcessLogger2 {
	private final Impl impl;
	private final ImplManager implManager;

	public ProcessLogger2(ImplManager implManager) {
		super();
		this.implManager = implManager;
		this.impl = implManager.createImpl();
	}

	public PLLevel getCurrentLevel() {
		return currentLevel();
	}

	public PLLevel currentLevel() {
		return implManager.entryLevel();
	}

	//////////////////////////////////// PRIVATE
	//////////////////////////////////// METHOD////////////////////////////////////////////////
	private Impl getImpl() {
		return impl;
	}

	private void _trace(String pattern, Object... args) {
		getImpl().action(PLLevel.TRACE, pattern, args);
	}

	private void _debug(String pattern, Object... args) {
		getImpl().action(PLLevel.DEBUG, pattern, args);
	}

	private void _info(String pattern, Object... args) {
		getImpl().action(PLLevel.INFO, pattern, args);
	}

	private void _warn(String pattern, Object... args) {
		getImpl().action(PLLevel.WARN, pattern, args);
	}

	private void _error(String pattern, Object... args) {
		getImpl().action(PLLevel.ERROR, pattern, args);
	}

	private void _fatal(String pattern, Object... args) {
		getImpl().action(PLLevel.FATAL, pattern, args);
	}

	private void _trace(Exception... exs) {
		getImpl().action(PLLevel.TRACE, exs);
	}

	private void _debug(Exception... exs) {
		getImpl().action(PLLevel.DEBUG, exs);
	}

	private void _info(Exception... exs) {
		getImpl().action(PLLevel.INFO, exs);
	}

	private void _warn(Exception... exs) {
		getImpl().action(PLLevel.WARN, exs);
	}

	private void _error(Exception... exs) {
		getImpl().action(PLLevel.ERROR, exs);
	}

	private void _fatal(Exception... exs) {
		getImpl().action(PLLevel.FATAL, exs);
	}

	//////////////////////////////// PUBLIC
	//////////////////////////////// METHOD////////////////////////////////////////////////////

	public void log(PLLevel plevel, String pattern, Object... args) {
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

	public void trace(String pattern, Object... args) {
		if (currentLevel().compareTo(PLLevel.TRACE) >= 0)
			_trace(pattern, args);
	}

	public void debug(String pattern, Object... args) {
		if (currentLevel().compareTo(PLLevel.DEBUG) >= 0)
			_debug(pattern, args);
	}

	public void info(String pattern, Object... args) {
		if (currentLevel().compareTo(PLLevel.INFO) >= 0)
			_info(pattern, args);
	}

	public void warn(String pattern, Object... args) {
		if (currentLevel().compareTo(PLLevel.WARN) >= 0)
			_warn(pattern, args);
	}

	public void error(String pattern, Object... args) {
		if (currentLevel().compareTo(PLLevel.ERROR) >= 0)
			_error(pattern, args);
	}

	public void fatal(String pattern, Object... args) {
		if (currentLevel().compareTo(PLLevel.FATAL) >= 0)
			_fatal(pattern, args);
	}

	public void trace(Exception... exs) {
		if (currentLevel().compareTo(PLLevel.TRACE) >= 0)
			_trace(exs);
	}

	public void debug(Exception... exs) {
		if (currentLevel().compareTo(PLLevel.DEBUG) >= 0)
			_debug(exs);
	}

	public void info(Exception... exs) {
		if (currentLevel().compareTo(PLLevel.INFO) >= 0)
			_info(exs);
	}

	public void warn(Exception... exs) {
		if (currentLevel().compareTo(PLLevel.WARN) >= 0)
			_warn(exs);
	}

	public void error(Exception... exs) {
		if (currentLevel().compareTo(PLLevel.ERROR) >= 0)
			_error(exs);
	}

	public void fatal(Exception... exs) {
		if (currentLevel().compareTo(PLLevel.FATAL) >= 0)
			_fatal(exs);
	}
}
