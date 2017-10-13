package com.openthinks.libs.utilities.websocket.message;


/**
 * 
 * ClassName: IConstant <br>
 * Function: constant for websocket and its message. <br>
 * Reason: TODO why you add this class?(Optional). <br>
 * date: Jul 26, 2017 4:20:07 PM <br>
 * 
 * @author dailey.yet@outlook.com
 * @since JDK 1.8
 */
public interface IConstant {
  String EMPTY_CHAT_MESSAGE_TYPE = "C99";
  String EMPTY_ACTION_MESSAGE_TYPE = "A99";

  //////////////////////////////
  String MSG_TYPE = "mt";
  String MSG_ID = "id";
  String MSG_CONTENT = "co";
  String MSG_TIMESTAMP = "ti";
  String MSG_COUNT = "cou";
  String MSG_GROUP = "gp";
  String MSG_OFFSET = "of";
  String MSG_TOKEN = "tok";

  String MSG_CONTENT_ACK = "ACK";
  /////////////////////////////
  String MSG_CAR_LINE = "cl";

  /////////////////////////////
  String JOIN_AT = "@";
  String MSG_CAR_LINE_DEFAULT_INSTANCE = "NULL";
  int FETCH_COUNT_DEFAULT = 15;

  ////////////////////////////////
  String ATTRIBUTE_HTTP_SESSION = "HTTP_SESSION_ATTRIBUTE";

}
