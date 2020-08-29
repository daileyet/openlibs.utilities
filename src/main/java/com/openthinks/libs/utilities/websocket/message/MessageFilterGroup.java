package com.openthinks.libs.utilities.websocket.message;


import java.util.HashMap;
import java.util.Map;

import com.openthinks.libs.utilities.websocket.support.IEndPointSession;

/**
 * ClassName: MessageFilterGroup <br>
 * Function: TODO FUNCTION description of this class. <br>
 * Reason: TODO why you add this class?(Optional). <br>
 * date: Jul 27, 2017 9:51:26 PM <br>
 * 
 * @author dailey.yet@outlook.com
 * @since JDK 1.8
 */
public final class MessageFilterGroup implements IMessageFilter<IMessage, IEndPointSession> {
  private final Map<Class<? extends IMessage>, IMessageFilter<IMessage, IEndPointSession>> filterMap;

  public MessageFilterGroup() {
    filterMap = new HashMap<>();
  }

  @SuppressWarnings("unchecked")
  public IMessageFilter<IMessage, IEndPointSession> addMessageFilter(
      Class<? extends IMessage> msgClass,
      IMessageFilter<? extends IMessage, ? extends IEndPointSession> msgFilter) {
    return filterMap.put(msgClass, (IMessageFilter<IMessage, IEndPointSession>) msgFilter);
  }

  @Override
  public boolean match(IMessage message, IEndPointSession session) {
    IMessageFilter<IMessage, IEndPointSession> filter = filterMap.get(message.getClass());
    if (filter == null) {
      filter = filterMap.get(IMessage.class);
    }
    if (filter != null) {
      return filter.match(message, session);
    }
    return false;
  }


}
