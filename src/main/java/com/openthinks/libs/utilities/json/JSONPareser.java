/**
 * 
 */
package com.openthinks.libs.utilities.json;

import java.util.Queue;
import java.util.StringTokenizer;

/**
 * @author dailey.yet@outlook.com
 *
 */
public final class JSONPareser {
	private final String strJSON;

	public JSONPareser(String jsonString) {
		this.strJSON = jsonString == null ? "" : jsonString.trim();
	}

	public final class JSONResultHolder {
		private final Object result;

		JSONResultHolder(Object result) {
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

	interface JSONToken {
		char LBRACE = '{';
		char RBRACE = '}';
		char LBRACKET = '[';
		char RBRACKET = ']';
		char COMMA = ',';
		char COLON = ':';
	}

	class ParseInfo {
		int index = 0;
		int mark = 0;
		int totalLen = 0;
	}

	public JSONResultHolder parse() {
		Object result = null;
		// process index for json string
		ParseInfo pi = new ParseInfo();
		pi.totalLen = strJSON.length();
		char startChar = strJSON.charAt(0);
		switch (startChar) {
		case JSONToken.LBRACE:
			parseObj(pi);
			break;
		case JSONToken.LBRACKET:
			parseArr(pi);
			break;
		default:
			break;
		}

		return new JSONResultHolder(result);
	}

	private void parseArr(ParseInfo pi) {

	}

	private void parseObj(ParseInfo pi) {
		do {
			pi.index++;
			char indexChar = strJSON.charAt(pi.index);
			if(indexChar==JSONToken.COLON) {
				pi.mark++;
				System.out.println(strJSON.substring(pi.mark, pi.index));
			}
			
		} while (pi.index < pi.totalLen-1);

	}

	public static void main(String[] args) {
		final String strJSON = "{\"key1\":1,\"key2\":\"ab,c\",\"key3\":true,\"key4\":{\"sukey4-1\":1,\"sukey4-2\":false},\"key5\":[1,2,3]}";
		JSONPareser parser= new JSONPareser(strJSON);
		parser.parse();
	}

}
