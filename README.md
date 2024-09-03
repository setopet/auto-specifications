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

  void shouldBeOpenOnWorkingDays();
}
```

 `@VerifiesContract` can then be used to ensure coverage:

```java
import java.time.LocalDateTime;

@VerifiesContract("PetSupplyStoreSpec")
public class PetSupplyStoreTest {

    @Test
    public void shouldSellAppropriateFood() {
        final PetSupplyStore petSupplyStore = new PetSupplyStore();
        final Cat myCat = new Cat();
        assertTrue(petSupplyStore.getFoodFor(myCat).isAppropriateFor(DietaryType.CARNIVOROUS));
    }

    @Test
    public void shouldBeOpenOnWorkingDays() {
        final PetSupplyStore petSupplyStore = new PetSupplyStore();
        final LocalDateTime dateTime =  LocalDateTime.of(LocalDate.of(2024, 9, 4), LocalTime.of(10, 0));
        assertTrue(petSupplyStore.isOpenOn(dateTime));
    }

}
```
