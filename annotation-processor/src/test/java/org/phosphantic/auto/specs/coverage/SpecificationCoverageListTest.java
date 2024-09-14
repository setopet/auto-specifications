package org.phosphantic.auto.specs.coverage;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;
import org.phosphantic.auto.specs.model.SpecificationItem;
import org.phosphantic.auto.specs.model.UnitSpecification;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpecificationCoverageListTest {

  @Test
  public void shouldDisplayVerifiedSpecifications() {
    SpecificationCoverageOutput view =
        new SpecificationCoverageOutput(
            SpecificationCoverage.newBuilder()
                .withMatchedSpecifications(
                    ImmutableList.of(
                        UnitSpecification.newBuilder()
                            .withTargetClassName("specification")
                            .build()))
                .build());
    assertTrue(view.asString().contains("1 verified specification"));
  }

  @Test
  public void shouldDisplayUnverifiedSpecifications() {
    SpecificationCoverageOutput view =
        new SpecificationCoverageOutput(
            SpecificationCoverage.newBuilder()
                .withUnmatchedSpecifications(
                    ImmutableList.of(
                        UnitSpecification.newBuilder()
                            .withTargetClassName("specification 1")
                            .build(),
                        UnitSpecification.newBuilder()
                            .withTargetClassName("specification 2")
                            .build()))
                .build());
    assertTrue(view.asString().contains("2 unverified specifications"));
    assertTrue(view.asString().contains("specification 1"));
    assertTrue(view.asString().contains("specification 2"));
  }

  @Test
  public void shouldDisplayPartiallyVerifiedSpecifications() {
    SpecificationCoverageOutput view =
        new SpecificationCoverageOutput(
            SpecificationCoverage.newBuilder()
                .withPartiallyMatchedSpecifications(
                    ImmutableMap.of(
                        UnitSpecification.newBuilder()
                            .withTargetClassName("specification 1")
                            .build(),
                        ImmutableList.of(
                            new SpecificationItem("unverified item of specification 1"))))
                .build());
    assertTrue(view.asString().contains("1 partially unverified specification"));
    assertTrue(view.asString().contains("specification 1"));
    assertTrue(view.asString().contains("unverified item of specification 1"));
  }
}
