package com.openthinks.libs.utilities.json.support;

import com.openthinks.libs.utilities.json.JSONArray;
import com.openthinks.libs.utilities.json.JSONObject;

public final class JSONHolder {
	private final Object result;

	public JSONHolder(Object result) {
		this.result = result;
	}

	public boolean isArray() {
		return this.result instanceof JSONArray;
	}

	public boolean isObject() {
		return this.result instanceof JSONObject;
	}

	public JSONArray asArray() {
		if (isArray())
			return (JSONArray) result;
		throw new UnMatcherTypeException("Not type of JSONArray");
	}

	public JSONObject asObject() {
		if (isObject())
			return (JSONObject) result;
		throw new UnMatcherTypeException("Not type of JSONObject");
	}
}