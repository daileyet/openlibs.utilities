package com.openthinks.libs.utilities.websocket.helper;


import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.openthinks.libs.utilities.Checker;
import com.openthinks.libs.utilities.websocket.message.IMessage;

/**
 * 
 * ClassName: MessageTypes <br>
 * Function: Message types directory. <br>
 * Reason: TODO why you add this class?(Optional). <br>
 * date: Jul 26, 2017 4:21:24 PM <br>
 * 
 * @author dailey.yet@outlook.com
 * @since JDK 1.8
 */
public final class MessageTypes {
  private final static Map<Class<? extends IMessage>, String> directory =
      new ConcurrentHashMap<Class<? extends IMessage>, String>();

  static {
    // register message
  }

  /**
   * @param clazz lookup {@link IMessage} class
   * @return String
   */
  public final static String lookup(Class<? extends IMessage> clazz) {
    Checker.require(clazz).notNull("IMessage Class Type could not be empty!");
    String type = directory.get(clazz);

    return type == null ? clazz.getName() : type;
  }

  @SuppressWarnings("unchecked")
  public final static <T extends IMessage> Class<T> valueOf(String messageTypeName) {
    Class<T> clazz = null;
    for (Entry<Class<? extends IMessage>, String> entry : directory.entrySet()) {
      if (entry.getValue().equals(messageTypeName)) {
        clazz = (Class<T>) entry.getKey();
      }
    }
    return clazz;
  }

  public final static void register(Class<? extends IMessage> clazz, String typeName) {
    Checker.require(clazz).notEmpty("IMessage Class Type could not be empty!");
    Checker.require(typeName).notEmpty("Message Type Name could not be empty!");
    directory.put(clazz, typeName);
  }
}
