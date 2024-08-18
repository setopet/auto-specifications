package org.phosphantic.auto.specs;

import java.io.IOException;

public class SpecificationFileInaccessibleException extends RuntimeException {
  public SpecificationFileInaccessibleException(
      String specificationFilename, IOException exception) {
    super(
        "Specification file " + specificationFilename + " was not found or could not be opened!",
        exception);
  }
}
