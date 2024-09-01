package org.phosphantic.auto.specs.coverage;

import java.util.*;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;
import org.phosphantic.auto.specs.model.SpecificationItem;
import org.phosphantic.auto.specs.model.UnitSpecification;

public class SpecificationCoverageAnalyzer {

  private final MethodInspector methodInspector;

  public SpecificationCoverageAnalyzer(final MethodInspector methodInspector) {
    this.methodInspector = methodInspector;
  }

  public SpecificationCoverage determineCoverage(
      final List<UnitSpecification> unitSpecifications,
      final Map<Element, List<UnitSpecification>> elementToSpecsMap) {
    final List<UnitSpecification> unmatchedSpecifications =
        unitSpecifications.stream()
            .filter(
                specification ->
                    elementToSpecsMap.values().stream()
                        .noneMatch(specificationList -> specificationList.contains(specification)))
            .collect(Collectors.toList());
    final List<UnitSpecification> matchedSpecifications = new ArrayList<>();
    final Map<UnitSpecification, List<SpecificationItem>> partiallyMatchedSpecifications =
        new HashMap<>();
    for (final Element element : elementToSpecsMap.keySet()) {
      for (final UnitSpecification specification : elementToSpecsMap.get(element)) {
        final List<SpecificationItem> unmatchedSpecificationItems =
            getUnmatchedSpecificationItems(element, specification);
        if (unmatchedSpecificationItems.isEmpty()) {
          matchedSpecifications.add(specification);
        } else {
          partiallyMatchedSpecifications.put(specification, unmatchedSpecificationItems);
        }
      }
    }
    return SpecificationCoverage.newBuilder()
        .withMatchedSpecifications(matchedSpecifications)
        .withPartiallyMatchedSpecifications(partiallyMatchedSpecifications)
        .withUnmatchedSpecifications(unmatchedSpecifications)
        .build();
  }

  private List<SpecificationItem> getUnmatchedSpecificationItems(
      final Element element, UnitSpecification specification) {
    final Set<SpecificationItem> unmatchedSpecificationItems =
        new HashSet<>(specification.getSpecificationItems());
    final Set<String> methodsOfElement = methodInspector.getMethodsOfElement(element);
    for (final SpecificationItem specificationItem : new ArrayList<>(unmatchedSpecificationItems)) {
      if (methodsOfElement.contains(specificationItem.getMethodName())) {
        unmatchedSpecificationItems.remove(specificationItem);
      }
    }
    return unmatchedSpecificationItems.stream().collect(Collectors.toList());
  }
}
