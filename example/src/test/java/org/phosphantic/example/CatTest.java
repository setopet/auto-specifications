package org.phosphantic.example;

import org.junit.jupiter.api.Test;
import org.phosphantic.auto.specs.VerifiesContract;

import static org.junit.jupiter.api.Assertions.assertEquals;

@VerifiesContract
public class CatTest {
    @Test
    public void shouldMeowWhenHungry() {
        final Cat cat = Cat.newBuilder().withHungry(true).build();
        assertEquals("Meow!", cat.noise());
    }
}
