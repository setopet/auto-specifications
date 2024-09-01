package org.phosphantic.auto.specs.generation;

import com.squareup.javapoet.*;
import javax.lang.model.element.Modifier;
import org.phosphantic.auto.specs.model.UnitSpecification;
import org.phosphantic.auto.specs.model.SpecificationItem;

public class InterfaceGenerator {

  public String generateInterface(final UnitSpecification unitSpecification) {
    final ClassName className =
        ClassName.get(
            unitSpecification.getPackageName(), unitSpecification.getSpecClassSimpleName());
    final TypeSpec.Builder typeSpecBuilder =
        TypeSpec.interfaceBuilder(className).addModifiers(Modifier.PUBLIC);
    for (final SpecificationItem specificationItem : unitSpecification.getSpecificationItems()) {
      typeSpecBuilder.addMethod(
          MethodSpec.methodBuilder(specificationItem.getMethodName())
              .addModifiers(Modifier.ABSTRACT)
              .addModifiers(Modifier.PUBLIC)
              .returns(TypeName.VOID)
              .build());
    }
    return JavaFile.builder(className.packageName(), typeSpecBuilder.build()).build().toString();
  }
}
