package com.openthinks.libs.utilities.json;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

public final class JSONArray extends LinkedList<JSONObject> implements Serializable {
	private static final long serialVersionUID = 3030798354129233047L;

	JSONArray() {
		super();
	}

	JSONArray(Collection<? extends JSONObject> jsonObjects) {
		super(jsonObjects);
	}

	@SuppressWarnings("unchecked")
	public JSONObject getEmbedJSONObj(int index) {
		Object element = get(index);
		Class<?> runtimeClassType = element.getClass();
		if (Map.class.isAssignableFrom(runtimeClassType)) {
			return new JSONObject((Map<? extends String, ? extends Object>) element);
		}
		return (JSONObject) element;
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
	 * create and add {@link JSONObject} element, then return created
	 * {@link JSONObject} element
	 * 
	 * @return {@link JSONObject}
	 */
	public JSONObject createEmbedJSONObj() {
		JSONObject element = new JSONObject();
		addEmbedJSONObj(element);
		return element;
	}

}