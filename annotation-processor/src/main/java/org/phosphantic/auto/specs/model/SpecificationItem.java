package org.phosphantic.auto.specs.model;

import com.google.common.base.CaseFormat;
import com.google.common.base.MoreObjects;
import java.util.Objects;

public class SpecificationItem {

  private final String text;

  public SpecificationItem(final String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  public String getMethodName() {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, text.replace(" ", "_"));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SpecificationItem that = (SpecificationItem) o;
    return Objects.equals(text, that.text);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(text);
  }

  public String toString() {
    return MoreObjects.toStringHelper(this).add("text", text).toString();
  }
}
