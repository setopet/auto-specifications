package org.phosphantic.auto.specs;

import java.io.IOException;
import java.io.InputStream;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.StandardLocation;

public class ResourceFileAccessorImpl implements ResourceFileAccessor{

  private final ProcessingEnvironment processingEnvironment;

  public ResourceFileAccessorImpl(final ProcessingEnvironment processingEnvironment) {
    this.processingEnvironment = processingEnvironment;
  }

  public InputStream getResourceFileStream(String filename) throws IOException {
    return processingEnvironment
        .getFiler()
        .getResource(StandardLocation.SOURCE_PATH, "", filename)
        .openInputStream();
  }
}
