package com.openthinks.libs.utilities.json.support;

import com.openthinks.libs.utilities.json.JSON;
import com.openthinks.libs.utilities.json.JSONArray;
import com.openthinks.libs.utilities.json.JSONObject;

/**
 * JSON parser helper
 * 
 * @author dailey
 *
 */
public class JSONFinder {
	private int index = 0;
	private int totalLen = 0;
	private final String jsonStr;

	public JSONFinder(String strJson) {
		this.jsonStr = strJson;
		this.totalLen = this.jsonStr.length();
	}

	/**
	 * judge given character is space or not
	 * 
	 * @param c
	 *            character
	 * @return true or false
	 */
	final public boolean isSpaceChar(char c) {
		return (c == ' ' || c == '\n' || c == '\r');
	}

	/**
	 * 移动当前指针至下一个有效的位置，可选择是否跳过空字符
	 * 
	 * @param skipSpace
	 *            是否跳过空字符
	 * @return true: move next success, false: already move to the end
	 */
	public boolean moveNext(boolean skipSpace) {
		if (!skipSpace) {
			if (index >= totalLen - 1) {
				return false;
			}
			index++;
			return true;
		}
		for (;;) {
			if (index >= totalLen - 1) {
				return false;
			}
			index++;
			char nextChar = jsonStr.charAt(index);
			if (!isSpaceChar(nextChar)) {
				break;
			}
		}
		return true;
	}

	/**
	 * 移动当前指针至下一个有效的位置，并获取新位置的字符，可选择是否跳过空字符
	 * 
	 * @param skipSpace
	 *            是否跳过空字符
	 * @return 下一个字符; throw {@link JSONParseException} when move next failed
	 */
	public char moveNextAndGet(boolean skipSpace) {
		boolean isMoveSuccess = moveNext(skipSpace);
		if (!isMoveSuccess) {
			throw new JSONParseException("unexpected string end.");
		}
		return getCurrent();
	}

	/**
	 * 获取当前字符，不跳过空字符，不会移动指针
	 * 
	 * @return char
	 */
	public char getCurrent() {
		return jsonStr.charAt(index);
	}

	/**
	 * 读取当前字符，跳过空字符，移动指针
	 * 
	 * @return char current character exclude space character
	 */
	public char read() {
		char c = getCurrent();
		if (isSpaceChar(c)) {
			return moveNextAndGet(true);
		}
		return c;
	}

	public void move(int distance) {
		for (int i = 0; i < distance; i++)
			if (!moveNext(false)) {
				throw new JSONParseException("unexpected string end.");
			}
	}

	/**
	 * 判断后续的几个字符是给定的字符，不跳过空字符,不会移动指针
	 * 
	 * @param assignedChars
	 *            char array
	 * @return true or false
	 */
	public boolean assertNext(char... assignedChars) {
		int currentPos = index, nextStart = currentPos + 1, len = assignedChars.length;
		if (assignedChars == null || len == 0)
			return true;
		for (int start = 0; start < len; start++) {
			if (start + nextStart >= totalLen)
				return false;
			if (jsonStr.charAt(start + nextStart) != assignedChars[start])
				return false;
		}
		return true;
	}

	/**
	 * 核心方法，获取下一个JSON元素
	 * 
	 * @return object
	 */
	public Object nextObject() {
		if (index >= totalLen) {
			throw new JSONParseException("unexpected string end.");
		}
		char currentChar = read();
		switch (currentChar) {
		case JSONToken.LBRACE:
			return findObject();
		case JSONToken.LBRACKET:
			return findArray();
		case JSONToken.DOUBLE_QUOTE:
			return findString();
		// value : true,false,null
		case 't':
		case 'T':
			if (assertNext('r', 'u', 'e')) {
				move(4);
				return true;
			}
			break;
		case 'f':
		case 'F':
			if (assertNext('a', 'l', 's', 'e')) {
				move(5);
				return false;
			}
			break;
		case 'n':
		case 'N':
			if (assertNext('u', 'l', 'l')) {
				move(4);
				return null;
			}
			break;
		}
		// value : number value
		Object number = findNumber(currentChar);
		if (number != null) {
			return number;
		}

		return null;
	}

	private Number findNumber(char currentChar) {
		StringBuilder builder = new StringBuilder();
		boolean isContainPoint = false;
		while (currentChar >= '0' && currentChar <= '9' || currentChar == '.' || currentChar == '+'
				|| currentChar == '-') {
			if (isContainPoint == false && currentChar == '.') {
				isContainPoint = true;
			}
			builder.append(currentChar);
			currentChar = moveNextAndGet(false);
		}
		if (builder.length() > 0) {
			try {
				if (isContainPoint) {
					return Double.parseDouble(builder.toString());
				} else {
					return Integer.parseInt(builder.toString());
				}
			} catch (NumberFormatException e) {
			}
		}
		return null;
	}

	/**
	 * 从当前位置查找字符串值
	 * 
	 * @return 字符串值
	 */
	private String findString() {
		StringBuilder builder = new StringBuilder();
		for (;;) {
			char currentChar = moveNextAndGet(false);
			if (currentChar == JSONToken.ESCAPE) {
				char next = moveNextAndGet(false);
				switch (next) {
				case 'r':
					builder.append('\r');
					break;
				case 'n':
					builder.append('\n');
					break;
				case 't':
					builder.append('\t');
					break;
				case 'b':
					builder.append('\b');
					break;
				case JSONToken.DOUBLE_QUOTE:
					builder.append(JSONToken.DOUBLE_QUOTE);
					break;
				default:
					throw new JSONParseException("unknown escape char : " + next);
				}
			} else if (currentChar == JSONToken.DOUBLE_QUOTE) {
				break;
			} else {
				builder.append(currentChar);
			}
		}
		moveNext(false);
		return builder.toString();
	}

	/**
	 * 从当前位置查找数组
	 * @return JSON数组
	 */
	private JSONArray findArray() {
		JSONArray jsonArr = JSON.array();
		boolean hasMoreElement = false;
		char next = moveNextAndGet(true);
		while (next != JSONToken.RBRACKET) {
			if (hasMoreElement) {
				if (next != JSONToken.COMMA) {
					throw new JSONParseException("illegal json object.");
				}
				moveNext(false);
			}
			jsonArr.add(nextObject());
			hasMoreElement = true;
			next = read();
		}
		moveNext(false);
		return jsonArr;
	}

	/**
	 * 从当前位置查找JSON对象
	 * @return JSON对象
	 */
	private JSONObject findObject() {
		JSONObject jsonObj = JSON.object();
		char next = moveNextAndGet(true);
		boolean hasMoreProps = false;
		while (next != JSONToken.RBRACE) {
			if (hasMoreProps) {
				if (next != JSONToken.COMMA) {
					throw new JSONParseException("illegal json object.");
				}
				moveNext(false);
			}
			// get key
			Object key = nextObject();
			if (key == null || !(key instanceof String)) {
				throw new JSONParseException("illegal json object.key must be a string:" + key);
			}
			next = read();
			if (next != JSONToken.COLON) {
				throw new JSONParseException("illegal json object.");
			}
			moveNext(false);
			// get value
			Object value = nextObject();
			jsonObj.addProperty((String) key, value);
			hasMoreProps = true;
			next = read();
		}
		moveNext(false);
		return jsonObj;
	}

	/**
	 * get current position
	 * 
	 * @return current position
	 */
	public int getPosition() {
		return index;
	}

	/**
	 * get JSON string total length
	 * 
	 * @return total JSON string length
	 */
	public int getTotalLen() {
		return totalLen;
	}

}
