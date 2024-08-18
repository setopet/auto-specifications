package org.phosphantic.auto.specs.model;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class ClassSpecification {

  private final String targetClassFullName;
  private final List<SpecificationItem> specificationItems;

  public ClassSpecification(
      String targetClassFullName, List<SpecificationItem> specificationItems) {
    checkArgument(
        !Strings.isNullOrEmpty(targetClassFullName), "class name must not be null or empty!");
    this.targetClassFullName = targetClassFullName;
    this.specificationItems = specificationItems;
  }

  public String getTargetClassFullName() {
    return targetClassFullName;
  }

  public String getTargetClassSimpleName() {
    final String[] splittedClassName = targetClassFullName.split("\\.");
    return splittedClassName[splittedClassName.length - 1];
  }

  public String getPackageName() {
    final String[] splittedClassName = targetClassFullName.split("\\.");
    if (splittedClassName.length > 1) {
      final List<String> stringList =
          Arrays.asList(splittedClassName).subList(0, splittedClassName.length - 1);
      return String.join(".", stringList);
    } else {
      return "";
    }
  }

  public String getSpecClassSimpleName() {
    return getTargetClassSimpleName() + "Spec";
  }

  public List<SpecificationItem> getSpecifications() {
    return specificationItems;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ClassSpecification that = (ClassSpecification) o;
    return Objects.equals(targetClassFullName, that.targetClassFullName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(targetClassFullName);
  }

  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("className", targetClassFullName)
        .add("specifications", specificationItems)
        .toString();
  }
}
