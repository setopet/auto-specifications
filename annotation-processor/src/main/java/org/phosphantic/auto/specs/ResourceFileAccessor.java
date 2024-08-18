package org.phosphantic.auto.specs;

import java.io.IOException;
import java.io.InputStream;

public interface ResourceFileAccessor {

  InputStream getResourceFileStream(String filename) throws IOException;
}
