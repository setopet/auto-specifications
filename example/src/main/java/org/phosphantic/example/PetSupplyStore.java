package org.phosphantic.example;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

public class PetSupplyStore {

  private final Set<DayOfWeek> businessDays =
      Set.of(
          DayOfWeek.MONDAY,
          DayOfWeek.TUESDAY,
          DayOfWeek.WEDNESDAY,
          DayOfWeek.THURSDAY,
          DayOfWeek.FRIDAY);

  public Food getCatFood() {
    return new CatFood();
  }

  public boolean isOpenOn(LocalDate dateTime) {
    return businessDays.contains(dateTime.getDayOfWeek());
  }
}
