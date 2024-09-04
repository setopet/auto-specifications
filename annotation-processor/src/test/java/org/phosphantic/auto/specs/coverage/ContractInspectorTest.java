package org.phosphantic.auto.specs.coverage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableList;
import java.lang.annotation.Annotation;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.phosphantic.auto.specs.VerifiesContract;
import org.phosphantic.auto.specs.model.UnitSpecification;

@ExtendWith(MockitoExtension.class)
public class ContractInspectorTest {

  @Mock private Element element;

  @Test
  public void shouldGetCoveredContractsFromAnnotationValue() {
    final VerifiesContract verifiesContract =
        new VerifiesContract() {

          @Override
          public Class<? extends Annotation> annotationType() {
            return null;
          }

          @Override
          public String[] value() {
            return new String[] {"SystemUnit", "AnotherSystemUnit", "Irrelevant"};
          }
        };
    when(element.getAnnotation(VerifiesContract.class)).thenReturn(verifiesContract);
    final List<UnitSpecification> specificationList =
        ImmutableList.of(
            UnitSpecification.newBuilder().withTargetClassName("SystemUnit").build(),
            UnitSpecification.newBuilder().withTargetClassName("AnotherSystemUnit").build());
    assertEquals(
        specificationList,
        new ContractInspector(specificationList).getSpecificationForElement(element));
  }

  @Test
  public void shouldGetCoveredContractsFromTestClassName() {
    final VerifiesContract verifiesContract =
        new VerifiesContract() {

          @Override
          public Class<? extends Annotation> annotationType() {
            return null;
          }

          @Override
          public String[] value() {
            return new String[] {};
          }
        };

    when(element.getAnnotation(VerifiesContract.class)).thenReturn(verifiesContract);
    // FIXME: too much implementation-dependent mocking
    final Name name = mock(Name.class);
    when(name.toString()).thenReturn("SystemUnitTest");
    when(element.getSimpleName()).thenReturn(name);
    final List<UnitSpecification> specificationList =
        ImmutableList.of(
            UnitSpecification.newBuilder().withTargetClassName("SystemUnit").build(),
            UnitSpecification.newBuilder().withTargetClassName("AnotherSystemUnit").build());
    assertEquals(
        ImmutableList.of(UnitSpecification.newBuilder().withTargetClassName("SystemUnit").build()),
        new ContractInspector(specificationList).getSpecificationForElement(element));
  }
}
