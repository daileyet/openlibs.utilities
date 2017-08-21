package com.openthinks.libs.utilities.websocket.support;


/**
 * 
 * ClassName: IEndPointSession <br/>
 * Function: TODO FUNCTION description of this class. <br/>
 * Reason: TODO why you add this class?(Optional). <br/>
 * date: Jul 26, 2017 4:22:29 PM <br/>
 * 
 * @author dailey.yet@outlook.com
 * @version
 * @since JDK 1.8
 */
public interface IEndPointSession {

  public boolean isOpen();

  public String getId();

  public String getGroup();

}
