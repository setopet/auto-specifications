package org.phosphantic.auto.specs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.auto.service.AutoService;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import org.phosphantic.auto.specs.coverage.*;
import org.phosphantic.auto.specs.generation.InterfaceGenerator;
import org.phosphantic.auto.specs.model.UnitSpecification;
import org.phosphantic.auto.specs.parser.ResourceSpecificationFileAccessor;
import org.phosphantic.auto.specs.parser.SpecificationFileAccessor;
import org.phosphantic.auto.specs.parser.SpecificationFileContentLoader;
import org.phosphantic.auto.specs.parser.SpecificationParser;

@SupportedAnnotationTypes("*") // Make the processor always run
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class AnnotationProcessor extends AbstractProcessor {

  private static final String specificationFileName = "specifications.yaml";
  private final ObjectMapper objectMapper = new YAMLMapper();
  private final InterfaceGenerator interfaceGenerator = new InterfaceGenerator();
  private final MethodInspector methodInspector = new MethodInspector();
  private List<UnitSpecification> specifications;

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (specifications == null) {
      specifications = generateSpecifications(processingEnv);
      try {
        generateInterfaceSourceFile(specifications);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    final Set<? extends Element> elements =
        roundEnv.getElementsAnnotatedWith(VerifiesContract.class);
    if (elements.isEmpty()) {
      return false;
    }
    final ContractInspector contractInspector = new ContractInspector(specifications);
    final Map<Element, List<UnitSpecification>> elementsToSpecificationsMap =
        elements.stream()
            .collect(Collectors.toMap(element -> element, contractInspector::getSpecificationForElement));
    final SpecificationCoverage specificationCoverage =
        new SpecificationCoverageAnalyzer(methodInspector)
            .determineCoverage(specifications, elementsToSpecificationsMap);
    final Notifier notifier = new Notifier(processingEnv.getMessager());
    notifier.writeNote(new SpecificationCoverageOutput(specificationCoverage).asString());
    return false;
  }

  private List<UnitSpecification> generateSpecifications(
      final ProcessingEnvironment processingEnvironment) {
    final SpecificationFileAccessor specificationFileAccessor =
        new ResourceSpecificationFileAccessor(processingEnvironment);
    final SpecificationFileContentLoader contentLoader =
        new SpecificationFileContentLoader(specificationFileAccessor, specificationFileName);
    final SpecificationParser specificationParser = new SpecificationParser(objectMapper);
    return specificationParser.parseSpecifications(contentLoader.getSpecificationFileContent());
  }

  private void generateInterfaceSourceFile(final List<UnitSpecification> unitSpecifications)
      throws IOException {
    for (final UnitSpecification unitSpecification : unitSpecifications) {
      JavaFileObject javaFileObject =
          processingEnv.getFiler().createSourceFile(unitSpecification.getSpecClassSimpleName());
      try (final Writer writer = javaFileObject.openWriter()) {
        writer.write(interfaceGenerator.generateInterface(unitSpecification));
      }
    }
  }
}
