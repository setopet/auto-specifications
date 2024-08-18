package org.phosphantic.auto.specs.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

public class ClassSpecificationTest {

  @Test
  public void shouldThrowOnMissingClassName() {
    assertThrows(
        IllegalArgumentException.class, () -> new ClassSpecification("", ImmutableList.of()));
  }

  @Test
  public void shouldGetTargetClassSimpleName() {
    assertEquals(
        "Example",
        new ClassSpecification("org.phosphantic.Example", ImmutableList.of())
            .getTargetClassSimpleName());
    assertEquals(
        "Example",
        new ClassSpecification("Example", ImmutableList.of()).getTargetClassSimpleName());
  }

  @Test
  public void shouldGetPackageName() {
    assertEquals(
        "org.phosphantic",
        new ClassSpecification("org.phosphantic.Example", ImmutableList.of()).getPackageName());
    assertEquals("", new ClassSpecification("Example", ImmutableList.of()).getPackageName());
  }

  @Test
  public void shouldGetSpecClassSimpleName() {
    assertEquals(
        "ExampleSpec",
        new ClassSpecification("org.phosphantic.Example", ImmutableList.of())
            .getSpecClassSimpleName());
  }
}

