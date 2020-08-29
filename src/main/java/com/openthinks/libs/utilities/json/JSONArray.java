package com.openthinks.libs.utilities.json;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import com.openthinks.libs.utilities.json.support.JSONElement;
import com.openthinks.libs.utilities.json.support.UnMatcherTypeException;

/**
 * represent JSON array, used {@link LinkedList} as baseline.
 * 
 * @author dailey
 *
 */
public final class JSONArray extends LinkedList<Object> implements Serializable {
  private static final long serialVersionUID = 3030798354129233047L;

  JSONArray() {
    super();
  }

  JSONArray(Collection<? extends Object> jsonObjects) {
    super(jsonObjects);
  }

  @SuppressWarnings("unchecked")
  public JSONObject getEmbedJSONObj(int index) {
    Object element = get(index);
    Class<?> runtimeClassType = element.getClass();
    if (Map.class.isAssignableFrom(runtimeClassType)) {
      return new JSONObject((Map<? extends String, ? extends Object>) element);
    }
    if (element instanceof JSONObject) {
      return (JSONObject) element;
    }
    throw new UnMatcherTypeException();
  }

  /**
   * get element from JSON array by index
   * 
   * @param index index of array
   * @return {@link JSONElement}
   */
  public JSONElement getElement(int index) {
    Object element = get(index);
    return new JSONElement(element);
  }

  public JSONArray addElement(JSONElement element) {
    add(element);
    return this;
  }


  JSONArray addEmbedJSONObj(JSONObject object) {
    add(object);
    return this;
  }

  JSONArray addEmbedJSONObj(Collection<JSONObject> jsonObjCollection) {
    addAll(jsonObjCollection);
    return this;
  }

  /**
   * create and add {@link JSONObject} element, then return created {@link JSONObject} element
   * 
   * @return {@link JSONObject}
   */
  public JSONObject createEmbedJSONObj() {
    JSONObject element = new JSONObject();
    addEmbedJSONObj(element);
    return element;
  }

}
