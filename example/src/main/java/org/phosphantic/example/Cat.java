package org.phosphantic.example;

public class Cat {

  private final boolean hungry;

  private Cat(boolean hungry) {
    this.hungry = hungry;
  }

  public String noise() {
    if (hungry) {
      return "Meow!";
    }
    return "Purrr!";
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {

    private boolean hungry;

    public Builder withHungry(final boolean hungry) {
      this.hungry = hungry;
      return this;
    }

    public Cat build() {
      return new Cat(hungry);
    }
  }
}
