package org.phosphantic.auto.specs.coverage;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.util.List;
import java.util.Map;
import org.phosphantic.auto.specs.model.SpecificationItem;
import org.phosphantic.auto.specs.model.UnitSpecification;

public class SpecificationCoverage {

  private final List<UnitSpecification> matchedSpecifications;
  private final List<UnitSpecification> unmatchedSpecifications;
  private final Map<UnitSpecification, List<SpecificationItem>> partiallyMatchedSpecifications;

  private SpecificationCoverage(
      final List<UnitSpecification> matchedSpecifications,
      final List<UnitSpecification> unmatchedSpecifications,
      final Map<UnitSpecification, List<SpecificationItem>> partiallyMatchedSpecifications) {
    this.matchedSpecifications = matchedSpecifications;
    this.unmatchedSpecifications = unmatchedSpecifications;
    this.partiallyMatchedSpecifications = partiallyMatchedSpecifications;
  }

  public List<UnitSpecification> getMatchedSpecifications() {
    return matchedSpecifications;
  }

  public List<UnitSpecification> getUnmatchedSpecifications() {
    return unmatchedSpecifications;
  }

  public Map<UnitSpecification, List<SpecificationItem>> getPartiallyMatchedSpecifications() {
    return partiallyMatchedSpecifications;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SpecificationCoverage that = (SpecificationCoverage) o;
    return Objects.equal(matchedSpecifications, that.matchedSpecifications)
        && Objects.equal(unmatchedSpecifications, that.unmatchedSpecifications)
        && Objects.equal(partiallyMatchedSpecifications, that.partiallyMatchedSpecifications);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(
        matchedSpecifications, unmatchedSpecifications, partiallyMatchedSpecifications);
  }

  @Override
  public String toString(){
    return MoreObjects.toStringHelper(this)
        .add("matchedSpecifications", matchedSpecifications)
        .add("unmatchedSpecifications", unmatchedSpecifications)
        .add("partiallyMatchedSpecifications", partiallyMatchedSpecifications)
        .toString();
}

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {

    private List<UnitSpecification> matchedSpecifications;
    private List<UnitSpecification> unmatchedSpecifications;
    private Map<UnitSpecification, List<SpecificationItem>> partiallyMatchedSpecifications;

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
