/**
 * 
 */
package com.openthinks.libs.utilities.logger;

/**
 * Logger help utilities
 * @author dailey.dai@openthinks.com
 *
 */
public final class Loggers {

  private Loggers() {}

  public static final PLLevel DEFAULT_LEVEL = PLLevel.DEBUG;

  /**
   * fetch log level<BR>
   * <ul>
   * <li>first try to retrieve the value of system property
   * <b>com.openthinks.libs.utilities.logger.PLLevel</b>
   * <li>second try to retrieve the value of system property <b>PLLevel</b>
   * <li>use default level: {@link DEFAULT_LEVEL} when failed to load system property
   * </ul>
   * 
   * @return {@link PLLevel}
   */
  public static PLLevel getLogLevel() {
    String levelString = System.getProperty(PLLevel.class.getName());
    if (levelString == null)
      levelString = System.getProperty(PLLevel.class.getSimpleName(), DEFAULT_LEVEL.name());
    PLLevel level = PLLevel.build(levelString);
    return level == null ? DEFAULT_LEVEL : level;
  }


  /**
   * get instance of {@link PLogger}
   * @param entryName String class name or entry name for group
   * @return {@link PLogger}
   */
  public static PLogger getLogger(String entryName) {
    return ProcessLogger2Factory.getRootLogger().setEntryName(entryName);
  }
}
