/**
 * 
 */
package com.openthinks.libs.utilities.json.support;

import com.openthinks.libs.utilities.json.JSONArray;
import com.openthinks.libs.utilities.json.JSONObject;

/**
 * JSON parser
 * 
 * @author dailey.yet@outlook.com
 *
 */
public final class JSONPareser {
  private final String strJSON;
  private final JSONFinder finder;

  public JSONPareser(String jsonString) {
    this.strJSON = jsonString == null ? "" : jsonString.trim();
    this.finder = new JSONFinder(strJSON);
  }

  /**
   * parse JSON string to object; array will be wrap as {@link JSONArray}; object will be wrap as
   * {@link JSONObject}
   * 
   * @return {@link JSONElement}
   */
  public JSONElement parse() {
    Object result = null;

    result = finder.nextObject();
    return new JSONElement(result);
  }

  /**
   * check after parse, if still remaining characters, should be space, else throw
   * {@link JSONParseException}
   */
  public void checkRemaining() {
    if (this.finder.getPosition() < this.finder.getTotalLen() - 1) {
      this.finder.moveNext(true);
      if (this.finder.getPosition() < this.finder.getTotalLen() - 1)
        throw new JSONParseException("illegal json object.");
    }
  }
}
