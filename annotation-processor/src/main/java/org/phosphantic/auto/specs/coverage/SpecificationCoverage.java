package org.phosphantic.auto.specs.coverage;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.phosphantic.auto.specs.model.SpecificationItem;
import org.phosphantic.auto.specs.model.UnitSpecification;

public class SpecificationCoverage {

  private final List<UnitSpecification> verifiedSpecifications;
  private final List<UnitSpecification> unverifiedSpecifications;
  private final Map<UnitSpecification, List<SpecificationItem>> partiallyVerifiedSpecifications;

  private SpecificationCoverage(
      final List<UnitSpecification> verifiedSpecifications,
      final List<UnitSpecification> unverifiedSpecifications,
      final Map<UnitSpecification, List<SpecificationItem>> partiallyVerifiedSpecifications) {
    this.verifiedSpecifications = verifiedSpecifications;
    this.unverifiedSpecifications = unverifiedSpecifications;
    this.partiallyVerifiedSpecifications = partiallyVerifiedSpecifications;
  }

  public List<UnitSpecification> getVerifiedSpecifications() {
    return verifiedSpecifications;
  }

  public List<UnitSpecification> getUnverifiedSpecifications() {
    return unverifiedSpecifications;
  }

  public Map<UnitSpecification, List<SpecificationItem>> getPartiallyVerifiedSpecifications() {
    return partiallyVerifiedSpecifications;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SpecificationCoverage that = (SpecificationCoverage) o;
    return Objects.equal(verifiedSpecifications, that.verifiedSpecifications)
        && Objects.equal(unverifiedSpecifications, that.unverifiedSpecifications)
        && Objects.equal(partiallyVerifiedSpecifications, that.partiallyVerifiedSpecifications);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(
        verifiedSpecifications, unverifiedSpecifications, partiallyVerifiedSpecifications);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("matchedSpecifications", verifiedSpecifications)
        .add("unmatchedSpecifications", unverifiedSpecifications)
        .add("partiallyMatchedSpecifications", partiallyVerifiedSpecifications)
        .toString();
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {

    private List<UnitSpecification> matchedSpecifications = new ArrayList<>();
    private List<UnitSpecification> unmatchedSpecifications = new ArrayList<>();
    private Map<UnitSpecification, List<SpecificationItem>> partiallyMatchedSpecifications =
        new HashMap<>();

    public Builder withMatchedSpecifications(final List<UnitSpecification> matchedSpecifications) {
      this.matchedSpecifications = matchedSpecifications;
      return this;
    }

    public Builder withUnmatchedSpecifications(
        final List<UnitSpecification> unmatchedSpecifications) {
      this.unmatchedSpecifications = unmatchedSpecifications;
      return this;
    }

    public Builder withPartiallyMatchedSpecifications(
        final Map<UnitSpecification, List<SpecificationItem>> partiallyMatchedSpecifications) {
      this.partiallyMatchedSpecifications = partiallyMatchedSpecifications;
      return this;
    }

    public SpecificationCoverage build() {
      return new SpecificationCoverage(
          matchedSpecifications, unmatchedSpecifications, partiallyMatchedSpecifications);
    }
  }
}
