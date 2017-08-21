package com.openthinks.libs.utilities.websocket.message;

/**
 * @author dailey.yet@outlook.com
 *
 */
@FunctionalInterface
public interface IDecoder<T extends IMessage> {
  public T decode(String target);
}
