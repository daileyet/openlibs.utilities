package com.openthinks.libs.utilities.websocket.message;

/**
 * Interface of all communication message
 * 
 * @author dailey.yet@outlook.com
 *
 */
public interface IMessage {
	/**
	 * get this type instance message content
	 * 
	 * @param <T>
	 *            message content type
	 * @return message content
	 */
	public <T extends Object> T getContent();

	/**
	 * get this instance message type
	 * 
	 * @return message type
	 */
	public String getType();

	/**
	 * 
	 * getGroup:get this message group if it exits. <br>
	 * 
	 * @return group name or empty
	 */
	public String getGroup();

}
