/**
 * 
 */
package com.openthinks.libs.utilities.json.support;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class UnMatcherTypeException extends RuntimeException {

  private static final long serialVersionUID = 7137655941939685673L;

  public UnMatcherTypeException() {}

  public UnMatcherTypeException(String message) {
    super(message);
  }

  public UnMatcherTypeException(Throwable cause) {
    super(cause);
  }

  public UnMatcherTypeException(String message, Throwable cause) {
    super(message, cause);
  }

  public UnMatcherTypeException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
