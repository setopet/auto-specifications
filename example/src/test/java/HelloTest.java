import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.phosphantic.auto.specs.Hello;
import org.phosphantic.auto.specs.VerifiesContract;

// TODO: adapt examples to documentation
@VerifiesContract
public class HelloTest {

  @Test
  public void shouldSayHello() {
    assertEquals("hello", new Hello().sayHello());
  }

}
