/**
 * 
 */
package com.openthinks.libs.utilities.json;

import com.openthinks.libs.utilities.json.support.JSONFinder;
import com.openthinks.libs.utilities.json.support.JSONElement;

/**
 * @author dailey.yet@outlook.com
 *
 */
public final class JSONPareser {
	private final String strJSON;

	public JSONPareser(String jsonString) {
		this.strJSON = jsonString == null ? "" : jsonString.trim();
	}

	public JSONElement parse() {
		Object result = null;
		JSONFinder finder = new JSONFinder(strJSON);
		result = finder.nextObject();
		return new JSONElement(result);
	}

	/*
	 * public static void main(String[] args) { String strJSON =
	 * "{\"key1\":1,\"key2\":\"ab\\\",c\",\"key3\":true,\"key4\":{\"sukey4-1\":1,\"sukey4-2\":false},\"key5\":[1,2,3]}";
	 * JSONPareser parser = new JSONPareser(strJSON); JSONElement holder =
	 * parser.parse(); System.out.println(holder+" is object ?"+holder.isObject());
	 * }
	 */
}
