package org.phosphantic.auto.specs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.phosphantic.auto.specs.parser.SpecificationFileAccessor;
import org.phosphantic.auto.specs.parser.SpecificationFileContentLoader;
import org.phosphantic.auto.specs.parser.SpecificationFileInaccessibleException;

public class SpecificationItemFileContentLoaderTest {

  @Mock private SpecificationFileAccessor specificationFileAccessor = new TestSpecificationFileAccessor();

  @Test
  public void shouldGetSpecificationFileContent() {
    final SpecificationFileContentLoader specificationFileContentLoader =
        new SpecificationFileContentLoader(specificationFileAccessor, "example-specifications.yaml");
    assertEquals(
        "org.phosphantic.auto.specs.CatFoodStore:\n"
            + "  - should only serve customers if not empty\n"
            + "  - should only serve cats\n"
            + "\n"
            + "org.phosphantic.auto.specs.Cat:\n"
            + "  - should get food from CatFoodStore\n",
        specificationFileContentLoader.getSpecificationFileContent());
  }

  @Test
  public void shouldThrowOnMissingSpecificationFile() throws IOException {
    final String notExistingSpecificationFile = "not-existing-specification-file.yaml";
    final SpecificationFileContentLoader specificationFileContentLoader =
        new SpecificationFileContentLoader(specificationFileAccessor, notExistingSpecificationFile);
    assertThrows(
        SpecificationFileInaccessibleException.class,
        specificationFileContentLoader::getSpecificationFileContent);
  }
}
