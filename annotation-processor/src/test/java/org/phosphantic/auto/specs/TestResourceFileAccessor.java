package org.phosphantic.auto.specs;

import java.io.IOException;
import java.io.InputStream;

public class TestResourceFileAccessor implements ResourceFileAccessor {
  @Override
  public InputStream getResourceFileStream(String filename) throws IOException {
    final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
    if (inputStream != null) {
      return inputStream;
    }
    throw new IOException();
  }
}
