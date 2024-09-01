package org.phosphantic.auto.specs.coverage;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

public class MethodInspector {

  public Set<String> getMethodsOfElement(final Element element) {
    if (element instanceof TypeElement) {
      TypeElement typeElement = (TypeElement) element;
      final List<? extends ExecutableElement> executableElements =
          typeElement.getEnclosedElements().stream()
              .filter(e -> e instanceof ExecutableElement)
              .map(enclosingElement -> (ExecutableElement) enclosingElement)
              .collect(Collectors.toList());
      return executableElements.stream()
          .map(executableElement -> executableElement.getSimpleName().toString())
          .collect(Collectors.toSet());
    } else {
      throw new IllegalStateException("Not a TypeElement: " + element);
    }
  }
}
