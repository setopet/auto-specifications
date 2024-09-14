package org.phosphantic.auto.specs.coverage;

import org.phosphantic.auto.specs.model.SpecificationItem;
import org.phosphantic.auto.specs.model.UnitSpecification;

import java.util.List;
import java.util.Map;

public class SpecificationCoverageOutput {

  private final SpecificationCoverage coverage;

  public SpecificationCoverageOutput(final SpecificationCoverage coverage) {
    this.coverage = coverage;
  }

  public String asString() {
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("\n");
    stringBuilder.append(coverage.getVerifiedSpecifications().size());
    stringBuilder.append(
        coverage.getVerifiedSpecifications().size() == 1
            ? " verified specification"
            : " verified specifications");
    final List<UnitSpecification> unverifiedSpecifications = coverage.getUnverifiedSpecifications();
    if (!unverifiedSpecifications.isEmpty()) {
      stringBuilder
          .append("\n")
          .append(unverifiedSpecifications.size())
          .append(
              unverifiedSpecifications.size() == 1
                  ? " unverified specification"
                  : " unverified specifications");
    }
    for (final UnitSpecification unverifiedSpecification : coverage.getUnverifiedSpecifications()) {
      stringBuilder.append(getUnitSpecificationOutput(unverifiedSpecification));
    }
    final Map<UnitSpecification, List<SpecificationItem>> partiallyVerifiedSpecifications =
        coverage.getPartiallyVerifiedSpecifications();
    if (!partiallyVerifiedSpecifications.isEmpty()) {
      stringBuilder
          .append("\n")
          .append(partiallyVerifiedSpecifications.size())
          .append(
              partiallyVerifiedSpecifications.size() == 1
                  ? " partially unverified specification"
                  : " partially unverified specifications");
    }
    for (final Map.Entry<UnitSpecification, List<SpecificationItem>> specificationEntry :
        partiallyVerifiedSpecifications.entrySet()) {
      stringBuilder.append(getUnitSpecificationOutput(specificationEntry.getKey()));
      for(final SpecificationItem item: specificationEntry.getValue()){
        stringBuilder.append(getSpecificationItemOutput(item));
      }
    }
    return stringBuilder.toString();
  }

  private static String getUnitSpecificationOutput(final UnitSpecification unitSpecification) {
    return "\n  " + unitSpecification.getTargetClassFullName();
  }

  private static String getSpecificationItemOutput(final SpecificationItem item) {
    return "\n    - " + item.getText();
  }
}
