package linda.test.MonoServeur;

import linda.Tuple;
import linda.server.LindaClient;

public class TestReadSimple {
	public static void main(String[] args) {
        LindaClient client = new LindaClient("rmi://localhost:4000/LindaServer");
        client.write(new Tuple("Hello", 42));
        client.write(new Tuple("second", 2));
        Tuple result = client.read(new Tuple(String.class, Integer.class));
        System.out.println("Read: " + result);
    }
}