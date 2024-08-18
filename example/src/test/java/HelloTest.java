import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.phosphantic.auto.specs.Hello;
import org.phosphantic.auto.specs.HelloSpec;

public class HelloTest implements HelloSpec {

  @Test
  public void shouldSayHello() {
    assertEquals("hello", new Hello().sayHello());
  }
}
