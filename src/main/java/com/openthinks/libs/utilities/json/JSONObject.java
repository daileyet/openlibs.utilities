/**
 * 
 */
package com.openthinks.libs.utilities.json;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.openthinks.libs.utilities.json.support.JSONElement;

/**
 * represent JSON object, used {@link LinkedHashMap} as baseline
 * 
 * @author dailey.yet@outlook.com
 *
 */
public final class JSONObject extends LinkedHashMap<String, Object> implements Serializable {
  private static final long serialVersionUID = -5354418059526451221L;

  JSONObject() {}

  JSONObject(Map<? extends String, ? extends Object> map) {
    super(map);
  }

  /**
   * get property value by property name from this JSON object, and cast assigned type
   * 
   * @param propertyName property name
   * @param propertyType property type
   * @return property value
   */
  @SuppressWarnings("unchecked")
  public <T> T getProperty(String propertyName, Class<T> propertyType) {
    Object value = getProperty(propertyName);
    Class<?> runtimClassType = value.getClass();
    if (runtimClassType != JSONArray.class && propertyType == JSONArray.class
        && Collection.class.isAssignableFrom(runtimClassType)) {
      return (T) new JSONArray((Collection<JSONObject>) value);
    } else if (runtimClassType != JSONObject.class && propertyType == JSONObject.class
        && Map.class.isAssignableFrom(runtimClassType)) {
      return (T) new JSONObject((Map<? extends String, ? extends Object>) value);
    } else {
      // TODO process other propertyType like int.class,long.class
      // Integer.class,Long.class
    }
    return (T) value;
  }

  /**
   * get property value by property name from this JSON object
   * 
   * @param propertyName property name
   * @return property value
   */
  public Object getProperty(String propertyName) {
    return get(propertyName);
  }

  public JSONElement getPropertyAsElement(String propertyName) {
    return new JSONElement(propertyName);
  }

  /**
   * add new key and value entry into JSON object
   * 
   * @param propertyName property name
   * @param value property value
   * @return current JSON object
   */
  public JSONObject addProperty(String propertyName, final Object value) {
    Object tmpVal = value;
    if (value != null && (value.getClass() == long.class || value.getClass() == Long.class
        || value.getClass() == double.class || value.getClass() == Double.class)) {
      tmpVal = String.valueOf(value);
    } else if (value instanceof Valueable) {
      tmpVal = ((Valueable<?>) value).value();
    }
    put(propertyName, tmpVal);
    return this;
  }

  /**
   * add new key and value entry into JSON object, and the value type is {@link JSONArray}
   * 
   * @param propertyName property name
   * @param value property value which type is {@link JSONArray}
   * @return current JSON object
   */
  JSONObject addEmbedArray(String propertyName, final JSONArray value) {
    addProperty(propertyName, value);
    return this;
  }

  JSONObject addEmbedJSONObj(String propertyName, final JSONObject value) {
    addProperty(propertyName, value);
    return this;
  }

  /**
   * create a new embed {@link JSONObject} and put it in its entries
   * 
   * @param propertyName String property name
   * @return {@link JSONObject} created embed {@link JSONObject}
   */
  public JSONObject createEmbedJSONObj(String propertyName) {
    JSONObject embedJSONObj = new JSONObject();
    addProperty(propertyName, embedJSONObj);
    return embedJSONObj;
  }

  /**
   * create a new embed {@link JSONArray} and put it in its entries
   * 
   * @param propertyName String property name
   * @return {@link JSONArray} created embed {@link JSONArray}
   */
  public JSONArray createEmbedArray(String propertyName) {
    JSONArray value = new JSONArray();
    addProperty(propertyName, value);
    return value;
  }

  /**
   * remove a entry in JSON object by property name
   * 
   * @param propertyName property name
   * @return current JSON object
   */
  public JSONObject removeProperty(String propertyName) {
    remove(propertyName);
    return this;
  }
}
