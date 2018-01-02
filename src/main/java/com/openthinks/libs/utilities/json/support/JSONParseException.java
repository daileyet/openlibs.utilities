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

	/**
	 * 
	 */
	public JSONParseException() {
	}

	/**
	 * @param message
	 */
	public JSONParseException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public JSONParseException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public JSONParseException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public JSONParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
