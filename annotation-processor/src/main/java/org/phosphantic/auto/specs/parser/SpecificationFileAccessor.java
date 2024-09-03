package org.phosphantic.auto.specs.parser;

import java.io.IOException;
import java.io.InputStream;

public interface SpecificationFileAccessor {

  InputStream getSpecificationFileAsStream(String path) throws IOException;
}
