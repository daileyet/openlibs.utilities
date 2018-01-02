package com.openthinks.libs.utilities.json.support;

import java.util.Stack;

public class JSONFinder {
	private int index = 0;
	private int mark = 0;
	private int totalLen = 0;
	private final String jsonStr;
	private final Stack<Character> stack = new Stack<>();

	public JSONFinder(String strJson) {
		this.jsonStr = strJson;
		this.totalLen = this.jsonStr.length();
	}

	public int getIndex() {
		return index;
	}

	public int getMark() {
		return mark;
	}

	public int getTotalLen() {
		return totalLen;
	}

	/**
	 * 移动当前指针至下一个有效的位置，跳过空字符
	 * @return true: move next success, false: already move to the end  
	 */
	public boolean moveNext() {
		for (;;) {
			if (index == totalLen) {
				return false;
			}
			char nextChar = jsonStr.charAt(index);
			if (nextChar != 32) {
				break;
			}
			index++;
		}
		return true;
	}

	public char read() {
		return jsonStr.charAt(index);
	}

	/**
	 * 读取下一个有效的字符，会跳过空字符，不会移动指针
	 * 
	 * @return char 下一个有效字符 或 null已读到结束
	 */
	public Character readNext() {
		return readNext(index);
	}

	private Character readNext(int position) {
		int next = position + 1;
		for (;;) {
			if (next >= totalLen) {
				return null;
			}
			char nextChar = jsonStr.charAt(next);
			if (nextChar != 32) {
				break;
			}
			next++;
		}
		return jsonStr.charAt(next);
	}
	
	/**
	 * 读取上一个有效的字符，会跳过空字符，不会移动指针
	 * 
	 * @return char 上一个有效字符 或 null已读到开端
	 */
	public Character readPrev() {
		return readPrev(index);
	}
	
	private Character readPrev(int position) {
		int prev = position - 1;
		for (;;) {
			if (prev < 0) {
				return null;
			}
			char prevChar = jsonStr.charAt(prev);
			if (prevChar != 32) {
				break;
			}
			prev--;
		}
		return jsonStr.charAt(prev);
	}
	
	
	public Character readFirst() {
		return readNext(-1);
	}
	
	public Character readLast() {
		return readPrev(totalLen);
	}

	public void mark() {
		mark = index;
	}

	public void mark(int position) {
		mark = position;
	}

	public char[] getMarked() {
		if (mark > index || index >= totalLen)
			return new char[0];
		return jsonStr.substring(mark, index).toCharArray();
	}
	
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	public JSONHolder nextElement() {
		
		
		
		
		
		
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}