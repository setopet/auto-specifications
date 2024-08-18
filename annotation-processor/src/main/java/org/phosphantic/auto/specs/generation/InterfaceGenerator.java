package org.phosphantic.auto.specs.generation;

import com.squareup.javapoet.*;
import javax.lang.model.element.Modifier;
import org.phosphantic.auto.specs.model.ClassSpecification;
import org.phosphantic.auto.specs.model.SpecificationItem;

public class InterfaceGenerator {

  public String generateInterface(final ClassSpecification classSpecification) {
    final ClassName className =
        ClassName.get(
            classSpecification.getPackageName(), classSpecification.getSpecClassSimpleName());
    final TypeSpec.Builder typeSpecBuilder =
        TypeSpec.interfaceBuilder(className).addModifiers(Modifier.PUBLIC);
    for (final SpecificationItem specificationItem : classSpecification.getSpecifications()) {
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
