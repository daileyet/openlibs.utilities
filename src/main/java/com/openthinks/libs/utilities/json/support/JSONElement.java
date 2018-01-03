package com.openthinks.libs.utilities.json.support;

import com.openthinks.libs.utilities.json.JSONArray;
import com.openthinks.libs.utilities.json.JSONObject;

public final class JSONElement {
	private final Object result;

	public JSONElement(Object result) {
		this.result = result;
	}

	public boolean isArray() {
		return this.result instanceof JSONArray;
	}

	public boolean isObject() {
		return this.result instanceof JSONObject;
	}

	public boolean isPrimitive() {
		return result != null && (Number.class.isAssignableFrom(result.getClass()) || result.getClass() == int.class
				|| result.getClass() == byte.class || result.getClass() == short.class
				|| result.getClass() == long.class || result.getClass() == float.class
				|| result.getClass() == double.class || result.getClass() == char.class
				|| result.getClass() == Character.class || result.getClass() == boolean.class
				|| result.getClass() == Boolean.class);
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

	@Override
	public String toString() {
		return "JSONHolder [result=" + result + "]";
	}
}