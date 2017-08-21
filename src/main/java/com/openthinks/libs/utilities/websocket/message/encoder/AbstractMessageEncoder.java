package com.openthinks.libs.utilities.websocket.message.encoder;


import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.openthinks.libs.utilities.websocket.message.AbstractMessage;

public class AbstractMessageEncoder implements Encoder.Text<AbstractMessage> {

  @Override
  public void destroy() {}

  @Override
  public void init(EndpointConfig arg0) {}

  @Override
  public String encode(AbstractMessage abstractMessage) throws EncodeException {
    return abstractMessage.encode();
  }

}
