package com.openthinks.libs.utilities.websocket.support;

import javax.websocket.EndpointConfig;

import com.openthinks.libs.utilities.websocket.message.IMessage;
import com.openthinks.libs.utilities.websocket.message.MessageFilterGroup;

public abstract class AbstractEndPointSupport
    implements IEndPointSupported<IEndPoint, AbstractEndPointSession> {

  @SuppressWarnings("unused")
  private EndpointConfig endpointConfig;

  private MessageFilterGroup messageFilterGrp;

  private final SessionCache<AbstractEndPointSession> sessionCache;

  private AbstractEndPointSupport() {
    this(null);
  }

  private AbstractEndPointSupport(EndpointConfig endpointConfig) {
    this.endpointConfig = endpointConfig;
    this.sessionCache = new SessionCache<AbstractEndPointSession>();
    this.messageFilterGrp = new MessageFilterGroup();
  }

  public final MessageFilterGroup getMessageFilterGrp() {
    return messageFilterGrp;
  }

  /**
   * 
   * sendMessage:send message to all connected client if it is business. <br>
   * 
   * @author dailey.dai@cn.bosch.com DAD2SZH
   * @param msg {@link IMessage}
   * @since JDK 1.8
   */
  public void sendMessage(IMessage msg) {
    if (msg == null)
      return;
    for (AbstractEndPointSession cs : sessionCache.allSession()) {
      if (!cs.isOpen())
        continue;
      if (!this.messageFilterGrp.match(msg, cs)) {
        continue;
      }
      cs.sendMessageSync(msg);
    }
  }

  public void setEndpointConfig(EndpointConfig endpointConfig) {
    this.endpointConfig = endpointConfig;
  }

}
