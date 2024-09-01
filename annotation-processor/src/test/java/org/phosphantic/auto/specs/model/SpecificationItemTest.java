package org.phosphantic.auto.specs.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SpecificationItemTest {

  @Test
  public void shouldReturnSpecificationAsText() {
    final SpecificationItem specificationItem =
        new SpecificationItem("should meow loudly when hungry");
    assertEquals("should meow loudly when hungry", specificationItem.getText());
  }

  @Test
  public void shouldReturnMethodNameInCamelCase() {
    final SpecificationItem specificationItem =
        new SpecificationItem("should meow loudly when hungry");
    assertEquals("shouldMeowLoudlyWhenHungry", specificationItem.getMethodName());
  }
}
