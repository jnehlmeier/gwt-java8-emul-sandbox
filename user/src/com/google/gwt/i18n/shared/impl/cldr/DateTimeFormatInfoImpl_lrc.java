/*
 * Copyright 2012 Google Inc.
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
package com.google.gwt.i18n.shared.impl.cldr;
// DO NOT EDIT - GENERATED FROM CLDR AND ICU DATA

/**
 * Implementation of DateTimeFormatInfo for the "lrc" locale.
 */
public class DateTimeFormatInfoImpl_lrc extends DateTimeFormatInfoImpl {

  @Override
  public String[] erasFull() {
    return new String[] {
        "BCE",
        "CE"
    };
  }

  @Override
  public String[] erasShort() {
    return new String[] {
        "BCE",
        "CE"
    };
  }

  @Override
  public int firstDayOfTheWeek() {
    return 6;
  }

  @Override
  public String formatYearMonthAbbrev() {
    return "G y MMM";
  }

  @Override
  public String formatYearMonthAbbrevDay() {
    return "G y MMM d";
  }

  @Override
  public String formatYearMonthFull() {
    return "G y MMMM";
  }

  @Override
  public String formatYearMonthFullDay() {
    return "G y MMMM d";
  }

  @Override
  public String formatYearMonthNum() {
    return "GGGGG y-MM";
  }

  @Override
  public String formatYearMonthNumDay() {
    return "GGGGG y-MM-dd";
  }

  @Override
  public String formatYearMonthWeekdayDay() {
    return "G y MMM d, EEE";
  }

  @Override
  public String formatYearQuarterFull() {
    return "G y QQQQ";
  }

  @Override
  public String formatYearQuarterShort() {
    return "G y Q";
  }

  @Override
  public String[] monthsFull() {
    return new String[] {
        "جانڤیە",
        "فئڤریە",
        "مارس",
        "آڤریل",
        "مئی",
        "جوٙأن",
        "جوٙلا",
        "آگوست",
        "سئپتامر",
        "ئوکتوڤر",
        "نوڤامر",
        "دئسامر"
    };
  }

  @Override
  public String[] monthsNarrow() {
    return new String[] {
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "11",
        "12"
    };
  }

  @Override
  public String[] monthsShort() {
    return new String[] {
        "جانڤیە",
        "فئڤریە",
        "مارس",
        "آڤریل",
        "مئی",
        "جوٙأن",
        "جوٙلا",
        "آگوست",
        "سئپتامر",
        "ئوکتوڤر",
        "نوڤامر",
        "دئسامر"
    };
  }

  @Override
  public String[] quartersFull() {
    return new String[] {
        "Q1",
        "Q2",
        "Q3",
        "Q4"
    };
  }

  @Override
  public String[] weekdaysFull() {
    return new String[] {
        "Sun",
        "Mon",
        "Tue",
        "Wed",
        "Thu",
        "Fri",
        "Sat"
    };
  }

  @Override
  public int weekendEnd() {
    return 5;
  }

  @Override
  public int weekendStart() {
    return 5;
  }
}
