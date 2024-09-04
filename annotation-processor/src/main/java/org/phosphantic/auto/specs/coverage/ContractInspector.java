package org.phosphantic.auto.specs.coverage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;
import org.phosphantic.auto.specs.VerifiesContract;
import org.phosphantic.auto.specs.model.UnitSpecification;

public class ContractInspector {

  private final List<UnitSpecification> specifications;

  public ContractInspector(final List<UnitSpecification> specifications) {
    this.specifications = specifications;
  }

  public List<UnitSpecification> getSpecificationForElement(final Element element) {
    final VerifiesContract verifiesContract = element.getAnnotation(VerifiesContract.class);
    List<String> contracts;
    if (verifiesContract.value().length > 1) {
      contracts = Arrays.asList(verifiesContract.value());
    } else {
      final String elementName = element.getSimpleName().toString();
      if (elementName.endsWith("Test")) {
        contracts =
            Collections.singletonList(elementName.substring(0, elementName.length() - 4));
      } else {
        contracts = Collections.emptyList();
      }
    }
    return specifications.stream()
        .filter(
            specification ->
                contracts.stream()
                    .anyMatch(
                        specClass -> specClass.equals(specification.getTargetClassSimpleName())))
        .collect(Collectors.toList());
  }
}
