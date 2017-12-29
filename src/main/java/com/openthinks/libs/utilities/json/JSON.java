/**
 * 
 */
package com.openthinks.libs.utilities.json;

import java.util.Collection;

/**
 * @author dailey.yet@outlook.com
 *
 */
public final class JSON {
	private JSON() {
	}

	public static final JSONArray array() {
		return new JSONArray();
	}

	public static final JSONArray array(final Collection<? extends JSONObject> jsonObjects) {
		return new JSONArray(jsonObjects);
	}

	public static final JSONObject object() {
		return new JSONObject();
	}
}
