// Copyright 2006 Google Inc. All Rights Reserved.
package com.google.gwt.emultest.java.lang;

import com.google.gwt.junit.client.GWTTestCase;

public class StringTest extends GWTTestCase {

  public String getModuleName() {
    return "com.google.gwt.emultest.EmulSuite";
  }

  /** tests charAt() */
  public void testCharAt() {
    assertEquals("abc".charAt(1), 'b');
  }

  /** tests concat */
  public void testConcat() {
    assertEquals("abcdef", "abc" + "def");
    assertEquals("abcdef", "abc".concat("def"));
    assertEquals("".concat(""), "");
    char c = 'd';
    String s = "abc";
    assertEquals("abcd", "abc" + 'd');
    assertEquals("abcd", "abc" + c);
    assertEquals("abcd", s + 'd');
    assertEquals("abcd", s + c);
    s += c;
    assertEquals("abcd", s);
  }

  /** Tests string creation and equality */
  public void testContructor() {
    char[] chars = {'a', 'b', 'c', 'd', 'e', 'f'};
    String constant = "abcdef";
    String shortString = "cde";
    assertEquals(new String(constant), constant);
    assertEquals(new String(chars), constant);
    assertEquals(new String(chars, 2, 3), shortString);
    assertEquals(new String(""), "");
    assertEquals(new String(new String(new String(new String("")))), "");
    assertEquals(new String(new char[]{}), "");
  }

  /** tests endsWith */
  public void testEndsWith() {
    String haystack = "abcdefghi";
    assertTrue("a", haystack.endsWith("defghi"));
    assertTrue("b", haystack.endsWith(haystack));
    assertFalse("c", haystack.endsWith(haystack + "j"));
  }

  public void testEquals() {
    assertFalse("ABC".equals("abc"));
    assertFalse("abc".equals("ABC"));
    assertTrue("abc".equals("abc"));
    assertTrue("ABC".equals("ABC"));
    assertFalse("AbC".equals("aBC"));
    assertFalse("AbC".equals("aBC"));
    assertTrue("".equals(""));
    assertFalse("".equals(null));
  }

  public void testEqualsIgnoreCase() {
    assertTrue("ABC".equalsIgnoreCase("abc"));
    assertTrue("abc".equalsIgnoreCase("ABC"));
    assertTrue("abc".equalsIgnoreCase("abc"));
    assertTrue("ABC".equalsIgnoreCase("ABC"));
    assertTrue("AbC".equalsIgnoreCase("aBC"));
    assertTrue("AbC".equalsIgnoreCase("aBC"));
    assertTrue("".equalsIgnoreCase(""));
    assertFalse("".equalsIgnoreCase(null));
  }

  /** tests indexOf */
  public void testIndexOf() {
    String haystack = "abcdefghi";
    assertEquals(haystack.indexOf("q"), -1);
    assertEquals(haystack.indexOf('q'), -1);
    assertEquals(haystack.indexOf("a"), 0);
    assertEquals(haystack.indexOf('a'), 0);
    assertEquals(haystack.indexOf('a', 1), -1);
    assertEquals(haystack.indexOf("bc"), 1);
    assertEquals(haystack.indexOf(""), 0);
  }

  /** tests lastIndexOf */
  public void testLastIndexOf() {
    String x = "abcdeabcdef";
    assertEquals(9, x.lastIndexOf("e"));
    assertEquals(10, x.lastIndexOf("f"));
    assertEquals(-1, x.lastIndexOf("f", 1));
  }

  public void testLength() {
    assertEquals(3, "abc".length());
    String str = "x";
    for (int i = 0; i < 16; i++) {
      str = str + str;
    }
    assertEquals(1 << 16, str.length());
  }

  /** tests toLowerCase */
  public void testLowerCase() {
    assertEquals("abc", "AbC".toLowerCase());
    assertEquals("abc", "abc".toLowerCase());
    assertEquals("", "".toLowerCase());
  }

  public void testMatch() {
    assertFalse("1f", "abbbbcd".matches("b*"));
    assertFalse("2f", "abbbbcd".matches("b+"));
    assertTrue("3t", "abbbbcd".matches("ab*bcd"));
    assertTrue("4t", "abbbbcd".matches("ab+cd"));
    assertTrue("5t", "abbbbcd".matches("ab+bcd"));
    assertFalse("6f", "abbbbcd".matches(""));
    assertTrue("7t", "abbbbcd".matches("a.*d"));
    assertFalse("8f", "abbbbcd".matches("a.*e"));
  }

  /** tests replace */
  public void testReplace() {
    assertEquals("axax".replace('x', 'a'), "aaaa");
    assertEquals("aaaa".replace('x', 'a'), "aaaa");
    for (char from = 32; from < 250; ++from) {
      char to = (char) (from + 5);
      assertEquals(toS(to), toS(from).replace(from, to));
    }
    for (char to = 32; to < 250; ++to) {
      char from = (char) (to + 5);
      assertEquals(toS(to), toS(from).replace(from, to));
    }
  }

  /** tests replaceAll */
  public void testReplaceAll() {
    assertEquals("abcdef", "xxxxabcxxdexf".replaceAll("x*", ""));
    assertEquals("1\\1abc123\\123de1234\\1234f", "1abc123de1234f".replaceAll(
      "([1234]+)", "$1\\\\$1"));
    assertEquals("\n  \n", "x  x".replaceAll("x", "\n"));
    assertEquals("x  x", "\n  \n".replaceAll("\\\n", "x"));
    assertEquals("x\"\\", "x".replaceAll("x", "\\x\\\"\\\\"));
    assertEquals("$$x$", "x".replaceAll("(x)", "\\$\\$$1\\$"));
  }

  /** tests split */
  public void testSplit() {
    compareList("fullSplit", new String[]{"abc", "", "", "de", "f"},
      "abcxxxdexfxx".split("x"));
    compareList("emptyRegexSplit", new String[]{
      "", "a", "b", "c", "x", "x", "d", "e", "x", "f", "x"},
      "abcxxdexfx".split(""));
    compareList("2:", "boo:and:foo".split(":", 2), new String[]{
      "boo", "and:foo"});
    compareList("5:", "boo:and:foo".split(":", 5), new String[]{
      "boo", "and", "foo"});
    compareList("-2:", "boo:and:foo".split(":", -2), new String[]{
      "boo", "and", "foo"});
    compareList("5o", "boo:and:foo".split("o", 5), new String[]{
      "b", "", ":and:f", "", ""});
    compareList("-2o", "boo:and:foo".split("o", -2), new String[]{
      "b", "", ":and:f", "", ""});
    compareList("0o", "boo:and:foo".split("o", 0), new String[]{
      "b", "", ":and:f"});
    compareList("0:", "boo:and:foo".split(":", 0), new String[]{
      "boo", "and", "foo"});

  }

  /** tests startsWith */
  public void testStartsWith() {
    String haystack = "abcdefghi";
    assertTrue(haystack.startsWith("abc"));
    assertTrue(haystack.startsWith("bc", 1));
    assertTrue(haystack.startsWith(haystack));
    assertFalse(haystack.startsWith(haystack + "j"));
  }

  public void testSubstring() {
    String haystack = "abcdefghi";
    assertEquals("cd", haystack.substring(2, 4));
    assertEquals("bc", "abcdef".substring(1, 3));
    assertEquals("bcdef", "abcdef".substring(1));
  }

  /** tests toCharArray */
  public void testToCharArray() {
    char[] a1 = "abc".toCharArray();
    char[] a2 = new char[]{'a', 'b', 'c'};
    for (int i = 0; i < a1.length; i++) {
      assertEquals(a1[i], a2[i]);
    }
  }

  /** tests trim */
  public void testTrim() {
    assertEquals("abc", "   \t abc \n  ".trim());
    assertEquals("abc", "abc".trim());
    assertEquals("", "".trim());
    assertEquals("", "   \t ".trim());
  }

  /** tests toUpperCase */
  public void testUpperCase() {
    assertEquals("abc", "AbC".toLowerCase());
    assertEquals("abc", "abc".toLowerCase());
    assertEquals("", "".toLowerCase());
  }

  public void testValueOf() {
    assertTrue(String.valueOf(C.FLOAT_VALUE).startsWith(C.FLOAT_STRING));
    assertEquals(C.INT_STRING, String.valueOf(C.INT_VALUE));
    assertEquals(C.LONG_STRING, String.valueOf(C.LONG_VALUE));
    assertTrue(String.valueOf(C.DOUBLE_VALUE).startsWith(C.DOUBLE_STRING));
    assertEquals(C.CHAR_STRING, String.valueOf(C.CHAR_VALUE));
    assertEquals(C.CHAR_ARRAY_STRING, String.valueOf(C.CHAR_ARRAY_VALUE));
    assertEquals(C.CHAR_ARRAY_STRING_SUB, String.valueOf(C.CHAR_ARRAY_VALUE, 1,
      4));
    assertEquals(C.FALSE_STRING, String.valueOf(C.FALSE_VALUE));
    assertEquals(C.TRUE_STRING, String.valueOf(C.TRUE_VALUE));
  }

  private void compareList(String category, String[] desired, String[] got) {
    assertEquals(category + " length", desired.length, got.length);
    for (int i = 0; i < desired.length; i++) {
      assertEquals(category + " " + i, desired[i], got[i]);
    }
  }

  private String toS(char from) {
    return Character.toString(from);
  }

}
