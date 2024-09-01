package org.phosphantic.auto.specs.generation;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;
import org.phosphantic.auto.specs.model.SpecificationItem;
import org.phosphantic.auto.specs.model.UnitSpecification;

public class InterfaceGeneratorTest {

  @Test
  public void shouldGenerateInterface() {
    final String generatedInterface =
        new InterfaceGenerator()
            .generateInterface(
                new UnitSpecification(
                    "org.phosphantic.Example",
                    ImmutableList.of(
                        new SpecificationItem("should do thing X"),
                        new SpecificationItem("should do thing Y"))));
    assertTrue(generatedInterface.contains("package org.phosphantic"));
    assertTrue(generatedInterface.contains("public interface Example"));
    assertTrue(generatedInterface.contains("void shouldDoThingX()"));
    assertTrue(generatedInterface.contains("void shouldDoThingY()"));
  }
}
