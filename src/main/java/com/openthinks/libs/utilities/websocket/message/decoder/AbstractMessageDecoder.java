package com.openthinks.libs.utilities.websocket.message.decoder;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.openthinks.libs.utilities.InstanceUtilities;
import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.libs.utilities.websocket.helper.MessageTypes;
import com.openthinks.libs.utilities.websocket.message.AbstractMessage;
import com.openthinks.libs.utilities.websocket.message.IConstant;


/**
 * 
 * ClassName: AbstractMessageDecoder <br>
 * Function: TODO FUNCTION description of this class. <br>
 * Reason: TODO why you add this class?(Optional). <br>
 * date: Jul 26, 2017 4:21:04 PM <br>
 * 
 * @author dailey.yet@outlook.com
 * @since JDK 1.8
 */
public class AbstractMessageDecoder implements Decoder.Text<AbstractMessage> {

  @Override
  public void destroy() {

  }

  @Override
  public void init(EndpointConfig arg0) {

  }

  @Override
  public AbstractMessage decode(String message) throws DecodeException {
    AbstractMessage abstrMessage = AbstractMessage.EMPTY;
    JsonObject msgObj = Json.createReader(new StringReader(message)).readObject();
    String msgType = msgObj.getString(IConstant.MSG_TYPE);
    Class<AbstractMessage> clz = MessageTypes.valueOf(msgType);
    try {
      abstrMessage = InstanceUtilities.create(clz, null);
    } catch (Exception e) {
      ProcessLogger
          .error("Decode ChatMessage:[" + message + "] failed on instancing: " + e.getMessage());
    }
    return abstrMessage.decode(message);
  }

  @Override
  public boolean willDecode(String arg0) {
    return true;
  }

}
