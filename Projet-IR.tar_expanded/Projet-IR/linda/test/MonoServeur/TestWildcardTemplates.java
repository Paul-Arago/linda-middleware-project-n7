package linda.test.MonoServeur;

import linda.Tuple;
import linda.server.LindaClient;

public class TestWildcardTemplates {
    public static void main(String[] args) {
        LindaClient client = new LindaClient("//localhost:4000/LindaServer");
        client.write(new Tuple(123, "bar"));
        client.write(new Tuple("baz", 3.14));
        client.write(new Tuple("foo", 42));

        System.out.println("Reading with template (Object.class, Integer.class)...");
        Tuple result1 = client.read(new Tuple(Object.class, Integer.class));
        System.out.println("Result: " + result1);

        System.out.println("Reading with template (String.class, Object.class)...");
        Tuple result2 = client.read(new Tuple(String.class, Object.class));
        System.out.println("Result: " + result2);

        System.out.println("Reading with template (Object.class, Object.class)...");
        Tuple result3 = client.read(new Tuple(Object.class, Object.class));
        System.out.println("Result: " + result3);
    }
}