package com.openthinks.libs.utilities.json;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * ClassName: Underline2Camel <br>
 * date: Aug 17, 2018 2:51:59 PM <br>
 * 
 * @author dailey.yet@outlook.com
 */
public class Underline2Camel {

  /**
   * 下划线转驼峰法
   * 
   * @param line 源字符串
   * @param smallCamel 大小驼峰,是否为小驼峰
   * @return 转换后的字符串
   */
  public static String underline2Camel(String line, boolean smallCamel) {
    if (line == null || "".equals(line)) {
      return "";
    }
    StringBuffer sb = new StringBuffer();
    Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
    Matcher matcher = pattern.matcher(line);
    while (matcher.find()) {
      String word = matcher.group();
      sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0))
          : Character.toUpperCase(word.charAt(0)));
      int index = word.lastIndexOf('_');
      if (index > 0) {
        sb.append(word.substring(1, index).toLowerCase());
      } else {
        sb.append(word.substring(1).toLowerCase());
      }
    }
    return sb.toString();
  }

  /**
   * 驼峰法转下划线
   * 
   * @param line 源字符串
   * @return 转换后的字符串 大写
   */
  public static String camel2Underline(String line) {
    if (line == null || "".equals(line)) {
      return "";
    }
    line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
    StringBuffer sb = new StringBuffer();
    Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
    Matcher matcher = pattern.matcher(line);
    while (matcher.find()) {
      String word = matcher.group();
      sb.append(word.toUpperCase());
      sb.append(matcher.end() == line.length() ? "" : "_");
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    String line = "HasDog";
    System.out.println(camel2Underline(line));
  }
}
