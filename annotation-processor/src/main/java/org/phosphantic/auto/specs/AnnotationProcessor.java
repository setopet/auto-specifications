package org.phosphantic.auto.specs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.auto.service.AutoService;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import org.phosphantic.auto.specs.generation.InterfaceGenerator;
import org.phosphantic.auto.specs.model.ClassSpecification;

@SupportedAnnotationTypes("*") // Make the processor run independently of present annotations
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class AnnotationProcessor extends AbstractProcessor {

  private static final String specificationFileName = "specifications.yaml";
  private final ObjectMapper objectMapper = new YAMLMapper();
  private final InterfaceGenerator interfaceGenerator = new InterfaceGenerator();
  // FIXME: Better way to avoid multiple executions?
  private boolean finished = false;

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (finished) {
      return false;
    }
    processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing");
    final List<ClassSpecification> specifications = getClassSpecifications(processingEnv);
    try {
      generateInterfaceSourceFile(specifications);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    finished = true;
    return false;
  }

  private List<ClassSpecification> getClassSpecifications(
      ProcessingEnvironment processingEnvironment) {
    final ResourceFileAccessor resourceFileAccessor =
        new ResourceFileAccessorImpl(processingEnvironment);
    final SpecificationFileContentLoader contentLoader =
        new SpecificationFileContentLoader(resourceFileAccessor, specificationFileName);
    final SpecificationParser specificationParser = new SpecificationParser(objectMapper);
    final List<ClassSpecification> specifications =
        specificationParser.parseSpecifications(contentLoader.getSpecificationFileContent());
    return specifications;
  }

  private void generateInterfaceSourceFile(final List<ClassSpecification> classSpecifications)
      throws IOException {
    for (final ClassSpecification classSpecification : classSpecifications) {
      JavaFileObject builderFile =
          processingEnv.getFiler().createSourceFile(classSpecification.getSpecClassSimpleName());
      try (final Writer writer = builderFile.openWriter()) {
        writer.write(interfaceGenerator.generateInterface(classSpecification));
      }
    }
  }
}
