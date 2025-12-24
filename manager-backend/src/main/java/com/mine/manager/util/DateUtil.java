package com.mine.manager.util;

import com.mine.manager.exception.InvalidDateException;
import com.mine.manager.exception.InvalidValueException;

import java.time.DateTimeException;
import java.time.LocalDate;

public class DateUtil {

  public static void validateDateYear(LocalDate date, Integer year) {
    if (date.getYear() != year) {
      throw new InvalidValueException("Fecha", date.toString());
    }
  }
  public static LocalDate createLocalDate(int year, int month, int day) {
    try {
      return LocalDate.of(year, month, day);
    } catch (DateTimeException e) {
      throw new InvalidDateException(day, month, year);
    }
  }
  
  public static boolean checkForOverlap(LocalDate oldStartDate, LocalDate oldEndDate, LocalDate newStartDate, LocalDate newEndDate) {
    return (oldEndDate == null || !newStartDate.isAfter(oldEndDate)) &&
        (newEndDate == null || !newEndDate.isBefore(oldStartDate));
  }  
}
