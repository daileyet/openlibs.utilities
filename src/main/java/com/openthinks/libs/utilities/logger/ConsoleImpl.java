package com.openthinks.libs.utilities.logger;

import java.text.MessageFormat;

class ConsoleImpl implements Impl {

	@Override
	public void action(PLLevel level, String pattern, Object... arguments) {
		String msg = pattern;
		switch (level) {
		case FATAL:
		case ERROR:
			System.err.print(level.name() + "=>");
			if (arguments != null && arguments.length > 0) {//FIX formatter error if arguments is empty
				try {
					msg = MessageFormat.format(pattern, arguments);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.err.print(msg);
			break;
		case WARN:
		case INFO:
		case DEBUG:
		case TRACE:
			System.out.print(level.name() + "=>");
			if (arguments != null && arguments.length > 0) {//FIX formatter error if arguments is empty
				try {
					msg = MessageFormat.format(pattern, arguments);
				} catch (Exception e) {
					e.printStackTrace();
				}
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
		case TRACE:
			System.out.println(level.name() + "=>");
			for (Exception ex : _exs) {
				ex.printStackTrace(System.out);
			}
			break;
		}
		System.out.println();

	}
}