package com.openthinks.libs.utilities.logger;

public interface PLogger {

  void log(PLLevel plevel, String pattern, Object... args);

  void trace(String pattern, Object... args);

  void debug(String pattern, Object... args);

  void info(String pattern, Object... args);

  void warn(String pattern, Object... args);

  void error(String pattern, Object... args);

  void fatal(String pattern, Object... args);

  void trace(Exception... exs);

  void debug(Exception... exs);

  void info(Exception... exs);

  void warn(Exception... exs);

  void error(Exception... exs);

  void fatal(Exception... exs);

  PLLevel currentLevel();

}
