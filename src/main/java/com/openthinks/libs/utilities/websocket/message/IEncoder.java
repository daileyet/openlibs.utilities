package com.openthinks.libs.utilities.websocket.message;

/**
 * 
 * ClassName: IEncoder <br/>
 * Function: Encode the value of {@link IMessage#getContent()}<br/>
 * Reason: TODO why you add this class?(Optional). <br/>
 * date: Jul 26, 2017 1:11:22 PM <br/>
 * 
 * @author dailey.yet@outlook.com
 * @version
 * @since JDK 1.8
 */
@FunctionalInterface
public interface IEncoder {
  public String encode();
}
