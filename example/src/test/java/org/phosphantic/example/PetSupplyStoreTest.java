package org.phosphantic.example;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.phosphantic.auto.specs.VerifiesContract;

@VerifiesContract
public class PetSupplyStoreTest {

  @Test
  public void shouldSellAppropriateFood() {
    final PetSupplyStore petSupplyStore = new PetSupplyStore();
    assertTrue(petSupplyStore.getCatFood().isAppropriateFor(DietaryType.CARNIVOROUS));
  }

  @Test
  public void shouldBeOpenOnWorkingDays() {
    final PetSupplyStore petSupplyStore = new PetSupplyStore();
    final LocalDate someWorkingDay = LocalDate.of(2024, 9, 4);
    assertTrue(petSupplyStore.isOpenOn(someWorkingDay));
  }
}
