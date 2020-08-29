package com.openthinks.libs.utilities;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * ClassName: NIOByteUtils <br>
 * date: Aug 7, 2018 1:53:27 PM <br>
 * 
 */
public final class NIOByteUtils {
  private NIOByteUtils() {}

  public static final byte[] long2EightBytes(long l) {
    ByteBuffer buffer = ByteBuffer.allocate(8);
    buffer.putLong(l);
    byte[] bytes = buffer.array();
    return bytes;
  }

  public static final byte[] long2SixBytes(long l) {
    ByteBuffer buffer = ByteBuffer.allocate(8);
    buffer.putLong(l);
    byte[] bytes = buffer.array();
    byte[] target = new byte[6];
    System.arraycopy(bytes, 2, target, 0, 6);
    return target;
  }


  public static final byte[] int2TwoBytes(int i) {
    ByteBuffer buffer = ByteBuffer.allocate(4);
    buffer.putInt(i);
    byte[] bytes = buffer.array();
    byte[] target = new byte[2];
    System.arraycopy(bytes, 2, target, 0, 2);
    return target;
  }

  public static final byte[] short2TwoBytes(short i) {
    ByteBuffer buffer = ByteBuffer.allocate(2);
    buffer.putShort(i);
    return buffer.array();
  }

  public static final byte[] int2FourBytes(int i) {
    ByteBuffer buffer = ByteBuffer.allocate(4);
    buffer.putInt(i);
    return buffer.array();
  }

  public static short toShort(byte[] vals) {
    ByteBuffer buffer = ByteBuffer.allocate(2);
    buffer.put(copyOf(vals, 2, true));
    buffer.flip();
    return buffer.getShort();
  }

  public static int toInt(byte[] vals) {
    ByteBuffer buffer = ByteBuffer.allocate(4);
    buffer.put(copyOf(vals, 4, true));
    buffer.flip();
    return buffer.getInt();
  }

  public static long toLong(byte[] vals) {
    ByteBuffer buffer = ByteBuffer.allocate(8);
    buffer.put(copyOf(vals, 8, true));
    buffer.flip();
    return buffer.getLong();
  }

  public static long toLong(byte[] vals, byte[] other) {
    ByteBuffer buffer = ByteBuffer.allocate(8);
    buffer.put(vals);
    int left = 8 - vals.length;
    buffer.put(Arrays.copyOf(other, Math.min(left, other.length)));
    buffer.flip();
    return buffer.getLong();
  }

  public static String toString(byte[] vals) {
    try {
      return new String(vals, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      return new String(vals);
    }
  }

  /**
   * 
   * toStringTrim:remove last byte element is 0x00. <br>
   * 
   * @param vals byte array
   * @return String 
   */
  public static String toStringTrim(byte[] vals) {
    int index = vals.length - 1;
    for (int i = vals.length - 1, j = 0; i > j; i--) {
      index = i;
      if (vals[i] == 0x00)
        ;
      else
        break;
    }
    return toString(Arrays.copyOf(vals, index + 1));

  }

  /**
   * 
   * copyOf:copy of given byte array with fix length,and fill left space with 0x00. <br>
   * 
   * @param src byte array need copy
   * @param length new byte array fix length
   * @param fillLeft boolean fill 0x00
   * @return byte array
   */
  public static byte[] copyOf(byte[] src, int length, boolean fillLeft) {
    int srcLen = src.length;
    if (!fillLeft || srcLen > length) {
      return Arrays.copyOf(src, length);
    }
    byte[] target = new byte[length];
    int index = length - srcLen;
    System.arraycopy(src, 0, target, index, srcLen);
    return target;
  }
}
