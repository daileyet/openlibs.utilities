/**
 * 
 */
package com.openthinks.libs.utilities.json;

import com.openthinks.libs.utilities.json.support.JSONHolder;
import com.openthinks.libs.utilities.json.support.JSONParseException;
import com.openthinks.libs.utilities.json.support.JSONFinder;

/**
 * @author dailey.yet@outlook.com
 *
 */
public final class JSONPareser {
	private final String strJSON;

	public JSONPareser(String jsonString) {
		this.strJSON = jsonString == null ? "" : jsonString.trim();
	}

	public JSONHolder parse() {
		Object result = null;
		// process index for json string
		JSONFinder finder = new JSONFinder(strJSON);
		boolean remaining = finder.moveNext();
		if (!remaining)
			throw new JSONParseException("Invalid JSON format");
		char startChar = finder.read();
		switch (startChar) {
		case JSONToken.LBRACE:
			result = JSON.object();
			parseObj(finder, (JSONObject) result);
			break;
		case JSONToken.LBRACKET:
			parseArr(finder);
			break;
		default:
			break;
		}

		return new JSONHolder(result);
	}

	private void parseArr(JSONFinder finder) {

	}

	private void parseObj(JSONFinder finder, JSONObject root) {
		boolean isMarked = false;
		while (finder.moveNext() && finder.read() != JSONToken.COLON) {
			if (!isMarked)
				finder.mark();
		}
		String property = unwrap(finder.getMarked());
		boolean remaining = finder.moveNext();
		if (!remaining)
			throw new JSONParseException("Invalid JSON format");
		char valStart = finder.read();
		switch (valStart) {
		case JSONToken.LBRACE:

			break;
		case JSONToken.LBRACKET:

			break;
		default:
			finder.mark();
			if (valStart == JSONToken.DOUBLE_QUOTE) {

			} else {
				while (finder.moveNext() && (finder.read() != JSONToken.COMMA || finder.read() != JSONToken.RBRACE
						|| finder.read() != JSONToken.RBRACKET)) {
				}
				finder.getMarked();
			}

			break;
		}

	}

	private String unwrap(char[] marked) {
		int len = marked.length;
		if (marked[0] == marked[len - 1] && marked[0] == JSONToken.DOUBLE_QUOTE) {
			return String.valueOf(marked, 1, len - 2);
		}
		return String.valueOf(marked);
	}

	public static void main(String[] args) {
		String strJSON = "{\"key1\":1,\"key2\":\"ab,c\",\"key3\":true,\"key4\":{\"sukey4-1\":1,\"sukey4-2\":false},\"key5\":[1,2,3]}";
		// JSONPareser parser = new JSONPareser(strJSON);
		// parser.parse();
		//
		strJSON = "{\"key\":\"a \\\" b\"}";

		for (char c : strJSON.toCharArray()) {
			System.out.print(c);
			if (c == JSONToken.ESCAPE) {
				System.out.print(" >>>");
			}
			System.out.println();
		}

		System.out.println(JSONToken.ESCAPE);
	}

}
