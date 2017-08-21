package com.openthinks.libs.utilities.websocket.support;

import com.openthinks.libs.utilities.websocket.message.IMessage;

/**
 * 
 * ClassName: IMessageHander <br/>
 * Function: TODO FUNCTION description of this class. <br/>
 * Reason: TODO why you add this class?(Optional). <br/>
 * date: Jul 26, 2017 4:22:15 PM <br/>
 * 
 * @author dailey.yet@outlook.com
 * @version
 * @since JDK 1.8
 */
public interface IMessageHander extends IHander<IMessage> {

  public void processSessionUser();
}
