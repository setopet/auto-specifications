package org.phosphantic.auto.specs;

import java.io.IOException;
import java.io.InputStream;
import org.phosphantic.auto.specs.parser.SpecificationFileAccessor;

public class TestSpecificationFileAccessor implements SpecificationFileAccessor {
  @Override
  public InputStream getSpecificationFileAsStream(String path) throws IOException {
    final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
    if (inputStream != null) {
      return inputStream;
    }
    throw new IOException();
  }
}
