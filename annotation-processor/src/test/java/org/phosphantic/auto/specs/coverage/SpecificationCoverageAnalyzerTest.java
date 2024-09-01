package org.phosphantic.auto.specs.coverage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.Element;
import org.junit.jupiter.api.Test;
import org.phosphantic.auto.specs.model.SpecificationItem;
import org.phosphantic.auto.specs.model.UnitSpecification;

public class SpecificationCoverageAnalyzerTest {

  private final MethodInspector methodInspector = mock(MethodInspector.class);

  @Test
  public void shouldAnalyzeMatchedCoverage() {
    final UnitSpecification specification1 =
        UnitSpecification.newBuilder()
            .withTargetClassName("TargetClassA")
            .withSpecificationItems(ImmutableList.of(new SpecificationItem("should do thing x")))
            .build();
    final List<UnitSpecification> specificationList = ImmutableList.of(specification1);
    final Element element = mock(Element.class);
    final Map<Element, List<UnitSpecification>> elementListMap =
        ImmutableMap.of(element, ImmutableList.of(specification1));
    when(methodInspector.getMethodsOfElement(element))
        .thenReturn(ImmutableSet.of("shouldDoThingX"));
    assertEquals(
        SpecificationCoverage.newBuilder()
            .withMatchedSpecifications(ImmutableList.of(specification1))
            .withPartiallyMatchedSpecifications(ImmutableMap.of())
            .withUnmatchedSpecifications(ImmutableList.of())
            .build(),
        new SpecificationCoverageAnalyzer(methodInspector)
            .determineCoverage(specificationList, elementListMap));
  }

  @Test
  public void shouldAnalyzePartiallyMatchedSpecifications() {
    final MethodInspector methodInspector = mock(MethodInspector.class);
    final UnitSpecification specification =
        UnitSpecification.newBuilder()
            .withTargetClassName("TargetClassB")
            .withSpecificationItems(
                ImmutableList.of(
                    new SpecificationItem("should do thing y"),
                    new SpecificationItem("should do thing z")))
            .build();
    final List<UnitSpecification> specificationList = ImmutableList.of(specification);
    final Element element = mock(Element.class);
    final Map<Element, List<UnitSpecification>> elementListMap =
        ImmutableMap.of(element, ImmutableList.of(specification));
    when(methodInspector.getMethodsOfElement(element))
        .thenReturn(ImmutableSet.of("shouldDoThingY"));
    assertEquals(
        SpecificationCoverage.newBuilder()
            .withMatchedSpecifications(ImmutableList.of())
            .withPartiallyMatchedSpecifications(
                ImmutableMap.of(
                    specification, ImmutableList.of(new SpecificationItem("should do thing z"))))
            .withUnmatchedSpecifications(ImmutableList.of())
            .build(),
        new SpecificationCoverageAnalyzer(methodInspector)
            .determineCoverage(specificationList, elementListMap));
  }

  @Test
  public void shouldAnalyzeUnmatchedCoverage() {
    final UnitSpecification specification =
        UnitSpecification.newBuilder()
            .withTargetClassName("TargetClassC")
            .withSpecificationItems(ImmutableList.of(new SpecificationItem("should do thing o")))
            .build();
    final List<UnitSpecification> specificationList = ImmutableList.of(specification);
    final Map<Element, List<UnitSpecification>> elementListMap = ImmutableMap.of();
    assertEquals(
        SpecificationCoverage.newBuilder()
            .withMatchedSpecifications(ImmutableList.of())
            .withPartiallyMatchedSpecifications(ImmutableMap.of())
            .withUnmatchedSpecifications(ImmutableList.of(specification))
            .build(),
        new SpecificationCoverageAnalyzer(methodInspector)
            .determineCoverage(specificationList, elementListMap));
  }
}
