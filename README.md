# Java Auto Specifications

Generate Java interfaces for specifications written to a YAML file. Use those interfaces for test classes to track their coverage.

The intention of this project is to encourage a lightweight design-driven approach for writing programmer's test, as you and your team are working with a non-Java source files when specifiying the functionality of a system unit.

## Getting Started

### Gradle/ Maven setup
TBD

### Define specifications
Place a `specifications.yaml` in the `src/main/resources` folder of your project:
```yaml
org.phosphantic.example.Cat:
  - should attract attention when hungry
  - should make a beautiful noise when satisfied

org.phosphantic.exampel.PetSupplyStore:
  - should sell appropriate food
  - should throw PetSupplyStoreEmptyException when empty
```

The annotation processor will then generate the following interfaces:
```java
public interface CatSpec {

  void shouldAttractAttentionWhenHungry();

  void shouldMakeABeautifulNoiseWhenSatisfied();
}

public interface PetSupplyStoreSpec {

  void shouldSellAppropriateFood();

  void shouldThrowPetSupplyEmptyExceptionWhenEmpty();
}
```

Those interfaces can then be implemented by a test class to ensure coverage:

```java
public class PetSupplyStoreTest implements PetSupplyStoreSpec {
```
