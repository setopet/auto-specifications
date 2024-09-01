package org.phosphantic.auto.specs.parser;

import java.io.IOException;
import java.io.InputStream;

public class SpecificationFileContentLoader {

  private final ResourceFileAccessor resourceFileAccessor;
  private final String specificationFileName;

  public SpecificationFileContentLoader(
      ResourceFileAccessor resourceFileAccessor, final String specificationFileName) {
    this.resourceFileAccessor = resourceFileAccessor;
    this.specificationFileName = specificationFileName;
  }

  public String getSpecificationFileContent() {
    try (final InputStream inputStream =
        resourceFileAccessor.getResourceFileStream(specificationFileName)) {
      return new String(inputStream.readAllBytes());
    } catch (final IOException e) {
      throw new SpecificationFileInaccessibleException(specificationFileName, e);
    }
  }
}
