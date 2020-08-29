/**
 * 
 */
package com.openthinks.libs.utilities.json.support;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class JSONParseException extends RuntimeException {

  private static final long serialVersionUID = -6319460319362213274L;

  public JSONParseException() {}

  public JSONParseException(String message) {
    super(message);
  }

  public JSONParseException(Throwable cause) {
    super(cause);
  }

  public JSONParseException(String message, Throwable cause) {
    super(message, cause);
  }

  public JSONParseException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
