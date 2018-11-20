/**
 * 
 */
package com.openthinks.libs.utilities;

import java.io.UnsupportedEncodingException;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class StringUtils {

	public static String trim(String processStr) {
		if (processStr == null)
			return processStr;
		return processStr.trim();
	}

	static char[] blanks = { '\t', ' ' ,(char)160,'\r','\n'};

	public static String trimBlank(String processStr) {
		if (processStr == null)
			return processStr;
		String processTemp = processStr;
		int st = 0;
		int len = processTemp.length();
		while (st < len) {
			char check = processTemp.charAt(st);
			boolean contains = false;
			for (char c : blanks) {
				if (c == check) {
					contains = true;
				}
			}
			if (contains) {
				st++;
			} else {
				break;
			}
		}

		while (st < len) {
			char check = processTemp.charAt(len - 1);
			boolean contains = false;
			for (char c : blanks) {
				if (c == check) {
					contains = true;
				}
			}
			if (contains) {
				len--;
			} else {
				break;
			}
		}
		return processTemp.substring(st, len);
	}
	static String getUnicode(String s) {
        try {
            StringBuffer out = new StringBuffer("");
            byte[] bytes = s.getBytes("unicode");
            for (int i = 0; i < bytes.length - 1; i += 2) {
                out.append("\\u");
                String str = Integer.toHexString(bytes[i + 1] & 0xff);
                for (int j = str.length(); j < 2; j++) {
                    out.append("0");
                }
                String str1 = Integer.toHexString(bytes[i] & 0xff);
                out.append(str1);
                out.append(str);
                 
            }
            return out.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
