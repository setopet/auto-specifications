package org.phosphantic.auto.specs.parser;

import java.io.IOException;
import java.io.InputStream;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.StandardLocation;

public class ResourceSpecificationFileAccessor implements SpecificationFileAccessor {

  private final ProcessingEnvironment processingEnvironment;

  public ResourceSpecificationFileAccessor(final ProcessingEnvironment processingEnvironment) {
    this.processingEnvironment = processingEnvironment;
  }

  public InputStream getSpecificationFileAsStream(String path) throws IOException {
    return processingEnvironment
        .getFiler()
        .getResource(StandardLocation.SOURCE_PATH, "", path)
        .openInputStream();
  }
}
