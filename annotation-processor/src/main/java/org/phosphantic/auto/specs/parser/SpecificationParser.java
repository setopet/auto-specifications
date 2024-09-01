package org.phosphantic.auto.specs.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.phosphantic.auto.specs.model.UnitSpecification;
import org.phosphantic.auto.specs.model.SpecificationItem;

public class SpecificationParser {

  private final ObjectMapper objectMapper;

  public SpecificationParser(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public List<UnitSpecification> parseSpecifications(final String content) {
    if( Strings.isNullOrEmpty(content)){
      return ImmutableList.of();
    }
    final Map<String, List<String>> specificationMap;
    try {
      specificationMap =
          objectMapper.readValue(content, new TypeReference<>() {});
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Could not parse specifications!", e);
    }

    return specificationMap.entrySet().stream()
        .map(
            entry ->
                new UnitSpecification(
                    entry.getKey(),
                    entry.getValue().stream().map(SpecificationItem::new).collect(Collectors.toList())))
        .collect(Collectors.toList());
  }
}
