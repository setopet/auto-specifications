# Java Auto Specifications

Write specifications of system units to a YAML file. Use the annotation processor to ensure coverage of those specifications by test cases.

The intention of this project is to encourage a lightweight behaviour-driven approach (BDD) for writing code and programmer's test, as you and your team can define functionality of a system unit before focusing on implementation details.

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
  - should be open on working days
```

Use `@VerifiesContract` to track coverage:

```java
@VerifiesContract
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
