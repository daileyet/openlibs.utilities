package com.openthinks.libs.utilities.json.support;

import com.openthinks.libs.utilities.json.JSONArray;
import com.openthinks.libs.utilities.json.JSONObject;
import com.openthinks.libs.utilities.json.Valueable;

/**
 * an warp for JSON element, it can be {@link JSONObject}, {@link JSONArray}, and java
 * primitive(boxed) object
 * 
 * @author dailey
 *
 */
public final class JSONElement implements Valueable<Object> {
  private final Object result;

  public JSONElement(Object result) {
    this.result = result;
  }

  /**
   * is {@link JSONArray} type
   * 
   * @return true or false
   */
  public boolean isArray() {
    return this.result instanceof JSONArray;
  }

  /**
   * is {@link JSONObject} type
   * 
   * @return true or false
   */
  public boolean isObject() {
    return this.result instanceof JSONObject;
  }

  /**
   * is primitive or boxed java type
   * 
   * @return true or false
   */
  public boolean isPrimitive() {
    return result != null && (Number.class.isAssignableFrom(result.getClass())
        || result.getClass() == int.class || result.getClass() == byte.class
        || result.getClass() == short.class || result.getClass() == long.class
        || result.getClass() == float.class || result.getClass() == double.class
        // || result.getClass() == char.class || result.getClass() == Character.class
        || result.getClass() == boolean.class || result.getClass() == Boolean.class);
  }

  public boolean isString() {
    return result != null && (result.getClass() == String.class || result.getClass() == char.class
        || result.getClass() == Character.class);
  }

  /**
   * cast as {@link JSONArray}
   * 
   * @return a instance of {@link JSONArray}, or throw {@link UnMatcherTypeException} when not
   *         {@link JSONArray}
   */
  public JSONArray asArray() {
    if (isArray())
      return (JSONArray) result;
    throw new UnMatcherTypeException("Not type of JSONArray");
  }

  /**
   * cast as {@link JSONObject}
   * 
   * @return a instance of {@link JSONObject}, or throw {@link UnMatcherTypeException} when not
   *         {@link JSONObject}
   */
  public JSONObject asObject() {
    if (isObject())
      return (JSONObject) result;
    throw new UnMatcherTypeException("Not type of JSONObject");
  }

  @Override
  public Object value() {
    return result;
  }

  @Override
  public String toString() {
    return "JSONHolder [result=" + result + "]";
  }
}
