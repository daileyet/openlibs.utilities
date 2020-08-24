/**
 * 
 */
package com.openthinks.libs.utilities.logger;

import java.text.MessageFormat;
import com.openthinks.libs.utilities.DateFormatUtil;
import com.openthinks.libs.utilities.lookup.Lookups;

/**
 * @author dailey.dai@openthinks.com
 *
 */
public interface StyleFormatter {

  public static final String DEFAULT_TIME_FORMAT_PATTERN = "yy-MM-dd HH:mm:ss.SSS";
  // entry name - time:content
  public final static String DEFAULT_NEW_PATTERN_TEMPLATE = "{0}|{1}|{2}";


  /**
   * get final formatted string by given parameters
   * @param level {@link PLLevel}
   * @param entryName entry name or group
   * @param contentPattern real log content
   * @return final formatted string
   */
  public String format(PLLevel level, String entryName, String contentPattern);

  /**
   * load instance of {@link StyleFormatter}
   * 
   * @return {@link StyleFormatter}
   */
  public static StyleFormatter load() {
    StyleFormatter formatter = null;
    try {
      formatter = Lookups.global().lookupSPI(StyleFormatter.class);
    } catch (Exception e) {
    }
    return formatter == null ? DEFAULT_STYLEFORMATTER : formatter;
  }

  public static final StyleFormatter DEFAULT_STYLEFORMATTER =
      (level, entryName, contentPattern) -> {
        return MessageFormat.format(DEFAULT_NEW_PATTERN_TEMPLATE, entryName,
            DateFormatUtil.formatNow(DEFAULT_TIME_FORMAT_PATTERN), contentPattern);
      };
}
