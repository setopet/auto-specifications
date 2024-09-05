package org.phosphantic.example;

public class CatFood implements Food {

  @Override
  public boolean isAppropriateFor(DietaryType dietaryType) {
    return dietaryType == DietaryType.CARNIVOROUS;
  }
}
