package com.mine.manager.util;

import java.util.List;
import java.util.stream.Collectors;

public class StringUtil {

  public static String toLowerCase(String input) {
    if (input == null || input.isEmpty()) {
      return null;
    }
    return input.toLowerCase();
  }

  public static String nullIfEquals(String input, String nullIf) {
    if (nullIf==null) return null;
    return nullIf.equalsIgnoreCase(input)?null:input;
  }

  public static List<String> listToLowerCase(List<String> input) {
    if (input == null) {
      return null;
    }
    return input.stream()
        .map(s -> (s != null) ? s.toLowerCase() : null)
        .collect(Collectors.toList());
  }

  public static String concatenate(String str1, String str2, String separator) {
    if (str1 == null && str2 == null) {
      return "";
    } else if (str1 == null) {
      return str2;
    } else if (str2 == null) {
      return str1;
    } else {
      return str1 + separator + str2;
    }
  }

  public static String removeExtraSpaces(String input) {
    if (input == null) {
      return null;
    }
    return input.trim().replaceAll("\\s+", " ");
  }

}
