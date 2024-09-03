package org.phosphantic.auto.specs.parser;

import java.io.IOException;
import java.io.InputStream;

public class SpecificationFileContentLoader {

  private final SpecificationFileAccessor specificationFileAccessor;
  private final String specificationFileName;

  public SpecificationFileContentLoader(
          SpecificationFileAccessor specificationFileAccessor, final String specificationFileName) {
    this.specificationFileAccessor = specificationFileAccessor;
    this.specificationFileName = specificationFileName;
  }

  public String getSpecificationFileContent() {
    try (final InputStream inputStream =
        specificationFileAccessor.getSpecificationFileAsStream(specificationFileName)) {
      return new String(inputStream.readAllBytes());
    } catch (final IOException e) {
      throw new SpecificationFileInaccessibleException(specificationFileName, e);
    }
  }
}
