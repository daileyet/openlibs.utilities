package com.openthinks.libs.utilities.websocket.support;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.websocket.EncodeException;
import javax.websocket.Session;

import com.openthinks.libs.utilities.CommonUtilities;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.libs.utilities.websocket.message.IConstant;
import com.openthinks.libs.utilities.websocket.message.IMessage;

/**
 * ClassName: AbstractEndPointSession <br/>
 * Function: TODO FUNCTION description of this class. <br/>
 * Reason: TODO why you add this class?(Optional). <br/>
 * date: Jul 26, 2017 2:51:51 PM <br/>
 * 
 * @author dailey.yet@outlook.com
 * @version
 * @since JDK 1.8
 */
public abstract class AbstractEndPointSession implements IEndPointSession {
  protected final Session session;

  protected AbstractEndPointSession(Session session) {
    super();
    this.session = session;
  }

  /**
   * TODO description the overrided function and changes.
   * 
   * @see com.bosch.ccu.cloud.web.websocket.support.IEndPointSession#isOpen()
   */
  @Override
  public boolean isOpen() {
    return this.session.isOpen();
  }

  /**
   * TODO description the overrided function and changes.
   * 
   * @see com.bosch.ccu.cloud.web.websocket.support.IEndPointSession#getId()
   */
  @Override
  public String getId() {
    return getGroup() + IConstant.JOIN_AT + session.getId();
  }


  public final Session getInstance() {
    return this.session;
  }

  public Optional<Object> getHttpSessionAttribute(String attributeName) {
    HttpSession httpSession =
        (HttpSession) session.getUserProperties().get(IConstant.ATTRIBUTE_HTTP_SESSION);
    if (httpSession != null) {
      return Optional.ofNullable(httpSession.getAttribute(attributeName));
    }
    return Optional.empty();
  }

  public void sendMessage(IMessage message, boolean isAsync) {
    try {
      ProcessLogger.debug("Sending message from Server:" + message.getClass());
      if (isAsync) {
        this.session.getAsyncRemote().sendObject(message);
      } else {
        this.session.getBasicRemote().sendObject(message);
      }
      ProcessLogger.debug("Send message from Server finished:" + message.getClass());
    } catch (IOException e) {
      ProcessLogger.error(CommonUtilities.getCurrentInvokerMethod() + ":" + e.getMessage());
    } catch (EncodeException e) {
      ProcessLogger.error(CommonUtilities.getCurrentInvokerMethod() + ":" + e.getMessage());
    } catch (Exception e) {
      ProcessLogger.error(CommonUtilities.getCurrentInvokerMethod() + ":" + e.getMessage());
    }
  }

  public void sendMessageSync(IMessage message) {
    sendMessage(message, false);
  }

  public void sendMessageAsync(IMessage message) {
    sendMessage(message, true);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    IEndPointSession other = (IEndPointSession) obj;
    if (getId() == null) {
      if (other.getId() != null)
        return false;
    } else if (!getId().equals(other.getId()))
      return false;
    return true;
  }
}
