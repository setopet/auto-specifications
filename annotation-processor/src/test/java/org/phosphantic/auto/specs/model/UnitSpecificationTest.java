package org.phosphantic.auto.specs.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

public class UnitSpecificationTest {

  @Test
  public void shouldThrowOnMissingClassName() {
    assertThrows(
        IllegalArgumentException.class, () -> new UnitSpecification("", ImmutableList.of()));
  }

  @Test
  public void shouldGetTargetClassSimpleName() {
    assertEquals(
        "Example",
        new UnitSpecification("org.phosphantic.Example", ImmutableList.of())
            .getTargetClassSimpleName());
    assertEquals(
        "Example", new UnitSpecification("Example", ImmutableList.of()).getTargetClassSimpleName());
  }

  @Test
  public void shouldGetPackageName() {
    assertEquals(
        "org.phosphantic",
        new UnitSpecification("org.phosphantic.Example", ImmutableList.of()).getPackageName());
    assertEquals("", new UnitSpecification("Example", ImmutableList.of()).getPackageName());
  }

  @Test
  public void shouldGetSpecClassSimpleName() {
    assertEquals(
        "ExampleSpec",
        new UnitSpecification("org.phosphantic.Example", ImmutableList.of())
            .getSpecClassSimpleName());
  }
}
