package org.phosphantic.auto.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.phosphantic.auto.specs.model.ClassSpecification;
import org.phosphantic.auto.specs.model.SpecificationItem;

public class SpecificationItemParserTest {

  @Test
  public void shouldParseEmptyInput() {
    final SpecificationParser specificationParser = new SpecificationParser(new YAMLMapper());
    assertEquals(ImmutableList.of(), specificationParser.parseSpecifications(""));
  }

  @Test
  public void shouldParseSpecificationsFromYaml() {
    final SpecificationParser specificationParser = new SpecificationParser(new YAMLMapper());
    final List<ClassSpecification> classSpecifications =
        Arrays.asList(
            new ClassSpecification(
                "org.phosphantic.auto.specs.CatFoodStore",
                Arrays.asList(
                    new SpecificationItem("should only serve customers if not empty"),
                    new SpecificationItem("should only serve cats"))),
            new ClassSpecification(
                "org.phosphantic.auto.specs.Cat",
                Arrays.asList(new SpecificationItem("should get food from CatFoodStore"))));
    assertEquals(
        classSpecifications, specificationParser.parseSpecifications(getContentFromResourceFile()));
  }

  @Test
  public void shouldThrowOnInvalidInput() {
    final SpecificationParser specificationParser =
        new SpecificationParser(new ObjectMapper(new YAMLFactory()));
    assertThrows(
        Exception.class,
        () ->
            specificationParser.parseSpecifications(
                "SomeClassMissingColonHere\n"
                    + "- some specification here\n"
                    + "- another specification here\n"));
  }

  private String getContentFromResourceFile() {
    try {
      return new String(
          new TestResourceFileAccessor()
              .getResourceFileStream("example-specifications.yaml")
              .readAllBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
