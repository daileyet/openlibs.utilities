/**
 * 
 */
package com.openthinks.libs.utilities.json.support;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class UnMatcherTypeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7137655941939685673L;

	/**
	 * 
	 */
	public UnMatcherTypeException() {
	}

	/**
	 * @param message
	 */
	public UnMatcherTypeException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public UnMatcherTypeException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UnMatcherTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public UnMatcherTypeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
