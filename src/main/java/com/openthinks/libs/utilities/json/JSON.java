/**
 * 
 */
package com.openthinks.libs.utilities.json;

import java.util.Collection;
import java.util.Iterator;

import com.openthinks.libs.utilities.json.support.JSONElement;
import com.openthinks.libs.utilities.json.support.JSONPareser;
import com.openthinks.libs.utilities.json.support.JSONToken;
import com.openthinks.libs.utilities.json.support.UnMatcherTypeException;

/**
 * JSON helper class
 * 
 * @author dailey.yet@outlook.com
 *
 */
public final class JSON {
  private JSON() {}

  /**
   * create a empty instance of {@link JSONArray}
   * 
   * @return {@link JSONArray}
   */
  public static final JSONArray array() {
    return new JSONArray();
  }

  /**
   * create a instance of {@link JSONArray} with given collection of object
   * 
   * @param jsonObjects a collection of object
   * @return {@link JSONArray}
   */
  public static final JSONArray array(final Collection<?> jsonObjects) {
    return new JSONArray(jsonObjects);
  }

  /**
   * create a empty instance of {@link JSONObject}
   * 
   * @return {@link JSONObject}
   */
  public static final JSONObject object() {
    return new JSONObject();
  }

  /**
   * parse JSON string as {@link JSONElement}
   * 
   * @param jsonString JSON string
   * @return {@link JSONElement}
   */
  public static final JSONElement parse(String jsonString) {
    JSONPareser parser = new JSONPareser(jsonString);
    JSONElement element = parser.parse();
    parser.checkRemaining();
    return element;
  }

  /**
   * convert {@link JSONObject} as string
   * 
   * @param object {@link JSONObject}
   * @return JSON string
   */
  public static final String stringify(JSONObject object) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(JSONToken.LBRACE);
    boolean hasMore = false;
    Iterator<String> keyIter = object.keySet().iterator();
    while (keyIter.hasNext()) {
      if (hasMore) {
        buffer.append(JSONToken.COMMA);
      }
      String key = keyIter.next();
      buffer.append(JSONToken.DOUBLE_QUOTE).append(key).append(JSONToken.DOUBLE_QUOTE)
          .append(JSONToken.COLON);
      JSONElement element = object.getPropertyAsElement(key);
      if (element.isArray()) {
        buffer.append(stringify(element.asArray()));
      } else if (element.isObject()) {
        buffer.append(stringify(element.asObject()));
      } else if (element.isPrimitive()) {
        buffer.append(element.value());
      } else if (element.isPrimitive()) {
        buffer.append(JSONToken.DOUBLE_QUOTE).append(element.value())
            .append(JSONToken.DOUBLE_QUOTE);
      } else {
        throw new UnMatcherTypeException("Illegal value type:" + element.value().getClass());
      }
      hasMore = true;
    }

    buffer.append(JSONToken.RBRACE);
    return buffer.toString();
  }

  /**
   * convert {@link JSONArray} to string
   * 
   * @param array {@link JSONArray}
   * @return JSON string
   */
  public static final String stringify(JSONArray array) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(JSONToken.LBRACKET);
    boolean hasMore = false;
    for (int index = 0, len = array.size(); index < len; index++) {
      if (hasMore) {
        buffer.append(JSONToken.COMMA);
      }
      JSONElement element = array.getElement(index);
      if (element.isObject()) {
        buffer.append(stringify(element.asObject()));
      } else if (element.isPrimitive()) {
        buffer.append(element.value());
      } else if (element.isString()) {
        buffer.append(JSONToken.DOUBLE_QUOTE).append(element.value())
            .append(JSONToken.DOUBLE_QUOTE);
      } else {
        throw new UnMatcherTypeException("Illegal value type:" + element.value().getClass());
      }
    }
    buffer.append(JSONToken.RBRACKET);
    return buffer.toString();
  }

}
