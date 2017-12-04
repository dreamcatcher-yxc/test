package example;
import org.junit.*;

public class HelloWorldTest {

    @Test
    public void testSayHello() {
        new HelloWorld().sayHello();
    }
}
