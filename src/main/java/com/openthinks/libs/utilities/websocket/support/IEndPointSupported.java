package com.openthinks.libs.utilities.websocket.support;


import javax.websocket.CloseReason;
import javax.websocket.Session;

/**
 * 
 * ClassName: IEndPointSupported <br/>
 * Function: TODO FUNCTION description of this class. <br/>
 * Reason: TODO why you add this class?(Optional). <br/>
 * date: Jul 26, 2017 4:22:39 PM <br/>
 * 
 * @author dailey.yet@outlook.com
 * @version @param <T>
 * @version @param <E>
 * @since JDK 1.8
 */
public interface IEndPointSupported<T extends IEndPoint, E extends IEndPointSession> {

  public void addSession(E endPointSession);

  public IErrorHander getErrorHander(final Session session);

  public IMessageHander getMessageHander(final Session session);

  public void remove(Session session, CloseReason closeReason);

}
