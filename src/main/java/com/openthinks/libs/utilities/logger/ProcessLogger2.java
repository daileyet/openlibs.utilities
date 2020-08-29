/**
 * 
 */
package com.openthinks.libs.utilities.logger;

/**
 * single instance of Logger interface
 * 
 * @author dailey.yet@outlook.com
 *
 */
public final class ProcessLogger2 implements PLogger {
  private final Impl impl;
  private final ImplManager implManager;
  private final StyleFormatter styleFormatter;
  private String entryName;

  public ProcessLogger2(ImplManager implManager) {
    super();
    this.implManager = implManager;
    this.impl = implManager.createImpl();
    this.styleFormatter = StyleFormatter.load();
  }

  public PLLevel getCurrentLevel() {
    return currentLevel();
  }

  @Override
  public PLLevel currentLevel() {
    return implManager.entryLevel();
  }

  ProcessLogger2 setEntryName(String entryName) {
    this.entryName = entryName;
    return this;
  }

  public String getEntryName() {
    return entryName;
  }

  //////////////////////////////////// PRIVATE
  //////////////////////////////////// METHOD////////////////////////////////////////////////
  private String newPattern(final String pattern, final PLLevel level) {
    if (pattern == null)
      return null;
    if (entryName == null)
      return pattern;
    return styleFormatter.format(level, entryName, pattern);
  }

  private Impl getImpl() {
    return impl;
  }

  private void _trace(String pattern, Object... args) {
    getImpl().action(PLLevel.TRACE, newPattern(pattern, PLLevel.TRACE), args);
  }

  private void _debug(String pattern, Object... args) {
    getImpl().action(PLLevel.DEBUG, newPattern(pattern, PLLevel.DEBUG), args);
  }

  private void _info(String pattern, Object... args) {
    getImpl().action(PLLevel.INFO, newPattern(pattern, PLLevel.INFO), args);
  }

  private void _warn(String pattern, Object... args) {
    getImpl().action(PLLevel.WARN, newPattern(pattern, PLLevel.WARN), args);
  }

  private void _error(String pattern, Object... args) {
    getImpl().action(PLLevel.ERROR, newPattern(pattern, PLLevel.ERROR), args);
  }

  private void _fatal(String pattern, Object... args) {
    getImpl().action(PLLevel.FATAL, newPattern(pattern, PLLevel.FATAL), args);
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.openthinks.libs.utilities.logger.PLogger#log(com.openthinks.libs.utilities.logger.PLLevel,
   * java.lang.String, java.lang.Object)
   */
  @Override
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

  /*
   * (non-Javadoc)
   * 
   * @see com.openthinks.libs.utilities.logger.PLogger#trace(java.lang.String, java.lang.Object)
   */
  @Override
  public void trace(String pattern, Object... args) {
    if (currentLevel().compareTo(PLLevel.TRACE) >= 0)
      _trace(pattern, args);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.openthinks.libs.utilities.logger.PLogger#debug(java.lang.String, java.lang.Object)
   */
  @Override
  public void debug(String pattern, Object... args) {
    if (currentLevel().compareTo(PLLevel.DEBUG) >= 0)
      _debug(pattern, args);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.openthinks.libs.utilities.logger.PLogger#info(java.lang.String, java.lang.Object)
   */
  @Override
  public void info(String pattern, Object... args) {
    if (currentLevel().compareTo(PLLevel.INFO) >= 0)
      _info(pattern, args);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.openthinks.libs.utilities.logger.PLogger#warn(java.lang.String, java.lang.Object)
   */
  @Override
  public void warn(String pattern, Object... args) {
    if (currentLevel().compareTo(PLLevel.WARN) >= 0)
      _warn(pattern, args);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.openthinks.libs.utilities.logger.PLogger#error(java.lang.String, java.lang.Object)
   */
  @Override
  public void error(String pattern, Object... args) {
    if (currentLevel().compareTo(PLLevel.ERROR) >= 0)
      _error(pattern, args);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.openthinks.libs.utilities.logger.PLogger#fatal(java.lang.String, java.lang.Object)
   */
  @Override
  public void fatal(String pattern, Object... args) {
    if (currentLevel().compareTo(PLLevel.FATAL) >= 0)
      _fatal(pattern, args);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.openthinks.libs.utilities.logger.PLogger#trace(java.lang.Exception)
   */
  @Override
  public void trace(Exception... exs) {
    if (currentLevel().compareTo(PLLevel.TRACE) >= 0)
      _trace(exs);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.openthinks.libs.utilities.logger.PLogger#debug(java.lang.Exception)
   */
  @Override
  public void debug(Exception... exs) {
    if (currentLevel().compareTo(PLLevel.DEBUG) >= 0)
      _debug(exs);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.openthinks.libs.utilities.logger.PLogger#info(java.lang.Exception)
   */
  @Override
  public void info(Exception... exs) {
    if (currentLevel().compareTo(PLLevel.INFO) >= 0)
      _info(exs);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.openthinks.libs.utilities.logger.PLogger#warn(java.lang.Exception)
   */
  @Override
  public void warn(Exception... exs) {
    if (currentLevel().compareTo(PLLevel.WARN) >= 0)
      _warn(exs);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.openthinks.libs.utilities.logger.PLogger#error(java.lang.Exception)
   */
  @Override
  public void error(Exception... exs) {
    if (currentLevel().compareTo(PLLevel.ERROR) >= 0)
      _error(exs);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.openthinks.libs.utilities.logger.PLogger#fatal(java.lang.Exception)
   */
  @Override
  public void fatal(Exception... exs) {
    if (currentLevel().compareTo(PLLevel.FATAL) >= 0)
      _fatal(exs);
  }
}
