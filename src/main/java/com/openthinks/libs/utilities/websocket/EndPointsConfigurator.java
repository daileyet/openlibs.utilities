package com.openthinks.libs.utilities.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

import com.openthinks.libs.utilities.websocket.message.IConstant;


/**
 * 
 * ClassName: EndPointsConfigurator <br>
 * Function: TODO FUNCTION description of this class. <br>
 * Reason: TODO why you add this class?(Optional). <br>
 * date: Jul 26, 2017 4:19:29 PM <br>
 * 
 * @author dailey.yet@outlook.com
 * @since JDK 1.8
 */
public class EndPointsConfigurator extends Configurator {
  @Override
  public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request,
      HandshakeResponse response) {
    super.modifyHandshake(config, request, response);
    HttpSession httpSession = (HttpSession) request.getHttpSession();
    if (httpSession != null)
      config.getUserProperties().put(IConstant.ATTRIBUTE_HTTP_SESSION, httpSession);
  }
}
