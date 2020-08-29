package com.openthinks.libs.utilities.websocket.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.openthinks.libs.utilities.websocket.helper.MessageTypes;
import com.openthinks.libs.utilities.websocket.helper.json.AnnotationExclusionStrategy;
import com.openthinks.libs.utilities.websocket.helper.json.Exclude;

/**
 * 
 * ClassName: AbstractMessage <br>
 * Function: TODO FUNCTION description of this class. <br>
 * Reason: TODO why you add this class?(Optional). <br>
 * date: Jul 26, 2017 4:20:41 PM <br>
 * 
 * @author dailey.yet@outlook.com
 * @since JDK 1.8
 */
public abstract class AbstractMessage implements IMessage, IEncoder, IDecoder<AbstractMessage> {

  @SerializedName(IConstant.MSG_TIMESTAMP)
  private String timestamp;
  @SerializedName(IConstant.MSG_CONTENT)
  private Object content;
  @SerializedName(IConstant.MSG_TYPE)
  private String type = getType();
  @SerializedName(IConstant.MSG_GROUP)
  private String group = "";
  @SerializedName(IConstant.MSG_TOKEN)
  @Exclude
  private String token;
  @Exclude
  protected static final transient Gson GSON;
  static {
    GSON = new GsonBuilder().setExclusionStrategies(new AnnotationExclusionStrategy()).create();
  }

  public AbstractMessage() {}

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = String.valueOf(timestamp);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T extends Object> T getContent() {
    return (T) content;
  }

  public <T extends Object> void setContent(T content) {
    this.content = content;
  }

  @Override
  public String encode() {
    return GSON.toJson(this);
  }

  @Override
  public AbstractMessage decode(String target) {
    return GSON.fromJson(target, this.getClass());
  }

  @Override
  public String getType() {
    return MessageTypes.lookup(this.getClass());
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public String getGroup() {
    return this.group;
  }

  public void setGroup(String group) {
    this.group = group;
  }


  @Override
  public String toString() {
    return "AbstractMessage [timestamp=" + timestamp + ", content=" + content + ", type=" + type
        + ", token=" + token + "]";
  }



  public final static AbstractMessage EMPTY = new NullMessage();

  static class NullMessage extends AbstractMessage {
    @Override
    public String getType() {
      return "EMPTY";
    }

    @Override
    public String getGroup() {
      return "";
    }
  }
}
