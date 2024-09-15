package org.phosphantic.auto.specs;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.phosphantic.auto.specs.parser.SpecificationFileAccessor;
import org.phosphantic.auto.specs.parser.SpecificationFileContentLoader;
import org.phosphantic.auto.specs.parser.SpecificationFileInaccessibleException;

import static org.junit.jupiter.api.Assertions.*;

public class SpecificationFileContentLoaderTest {

  @Mock private SpecificationFileAccessor specificationFileAccessor = new TestSpecificationFileAccessor();

  @Test
  public void shouldGetSpecificationFileContent() {
    final SpecificationFileContentLoader specificationFileContentLoader =
        new SpecificationFileContentLoader(specificationFileAccessor, "example-specifications.yaml");
    final String content = specificationFileContentLoader.getSpecificationFileContent();
    assertTrue(content.contains("org.phosphantic.auto.specs.CatFoodStore:"));
    assertTrue(content.contains("  - should only serve customers if not empty"));
    assertTrue(content.contains("  - should only serve cats"));
    assertTrue(content.contains("org.phosphantic.auto.specs.Cat:"));
    assertTrue(content.contains("  - should get food from CatFoodStore"));
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
