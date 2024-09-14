# Java Auto Specifications

Write specifications of system units to a YAML file. Use the annotation processor to ensure coverage of those
specifications by test cases.

The intention of this project is to encourage a lightweight behaviour-driven approach (BDD) for writing code and
programmer's test, as you and your team can define functionality of a system unit before focusing on implementation
details.

## Getting Started

### Gradle/ Maven setup

TBD

### Define specifications

Place a `specifications.yaml` in the `src/test/resources` folder of your project:

```yaml
org.phosphantic.example.Cat:
  - should meow when hungry
  - should purr when satisfied

org.phosphantic.example.PetSupplyStore:
  - should sell appropriate food
  - should be open on working days

org.phosphantic.example.Bathroom:
  - should induce a clean cat state
```

Use `@VerifiesContract` to track coverage:

```java

@VerifiesContract
public class PetSupplyStoreTest {

    @Test
    public void shouldSellAppropriateFood() {
        final PetSupplyStore petSupplyStore = new PetSupplyStore();
        assertTrue(petSupplyStore.getCatFood().isAppropriateFor(DietaryType.CARNIVOROUS));
    }

    @Test
    public void shouldBeOpenOnWorkingDays() {
        final PetSupplyStore petSupplyStore = new PetSupplyStore();
        final LocalDate someWorkingDay = LocalDate.of(2024, 9, 4);
        assertTrue(petSupplyStore.isOpenOn(someWorkingDay));
    }
}

@VerifiesContract
public class CatTest {
    @Test
    public void shouldMeowWhenHungry() {
        final Cat cat = Cat.newBuilder().withHungry(true).build();
        assertEquals("Meow!", cat.noise());
    }
}
```

The annotation processor will then generate the following output when compiling the test source set:

```
  1 verified specification
  1 unverified specification
    org.phosphantic.example.Bathroom
  1 partially unverified specification
    org.phosphantic.example.Cat
      - should purr when satisfied
```
