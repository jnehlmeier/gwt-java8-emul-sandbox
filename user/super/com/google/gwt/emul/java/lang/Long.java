/*
 * Copyright 2006 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package java.lang;

/**
 * Wraps a primitive <code>long</code> as an object.
 */
public final class Long extends Number implements Comparable {
  public static final long MIN_VALUE = 0x8000000000000000L;
  public static final long MAX_VALUE = 0x7fffffffffffffffL;

  private final long fValue;

  public Long(long value) {
    fValue = value;
  }

  public Long(String s) {
    fValue = parseLong(s);
  }

  public int compareTo(Object o) {
    return compareTo((Long) o);
  }

  public int hashCode() {
    return (int) fValue;
  }

  public int compareTo(Long b) {
    if (fValue < b.fValue) {
      return -1;
    } else if (fValue > b.fValue) {
      return 1;
    } else {
      return 0;
    }
  }

  public boolean equals(Object o) {
    return (o instanceof Long) && (((Long) o).fValue == fValue);
  }

  public static String toString(long b) {
    return String.valueOf(b);
  }

  public String toString() {
    return toString(fValue);
  }

  public static Long decode(String s) throws NumberFormatException {
    long x = __parseLongInfer(s);
    if (__isLongNaN(x)) {
      throw new NumberFormatException(s);
    } else {
      return new Long(x);
    }
  }

  public static Long valueOf(String s) throws NumberFormatException {
    return new Long(Long.parseLong(s));
  }

  public static Long valueOf(String s, int radix) throws NumberFormatException {
    return new Long(Long.parseLong(s, radix));
  }

  public byte byteValue() {
    return (byte) fValue;
  }

  public double doubleValue() {
    return fValue;
  }

  public float floatValue() {
    return fValue;
  }

  public int intValue() {
    return (int) fValue;
  }

  public long longValue() {
    return fValue;
  }

  public short shortValue() {
    return (short) fValue;
  }

  public static long parseLong(String s, int radix)
      throws NumberFormatException {
    long x = __parseLongRadix(s, radix);
    if (__isLongNaN(x)) {
      throw new NumberFormatException(s);
    } else {
      return x;
    }
  }

  public static long parseLong(String s) throws NumberFormatException {
    return parseLong(s, 10);
  }

  public static String toHexString(long x) {
    if (x == 0) {
      return "0";
    }
    String hexStr = "";
    while (x != 0) {
      int nibble = (int) x & 0xF;
      hexStr = __hexDigits[nibble] + hexStr;
      x = x >>> 4;
    }
    return hexStr;
  }

  public static String toBinaryString(long x) {
    if (x == 0) {
      return "0";
    }
    String binStr = "";
    while (x != 0) {
      int bit = (int) x & 0x1;
      binStr = __hexDigits[bit] + binStr;
      x = x >>> 1;
    }
    return binStr;
  }

}
