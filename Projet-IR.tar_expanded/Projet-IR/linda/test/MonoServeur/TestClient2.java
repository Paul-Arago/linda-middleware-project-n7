package linda.test.MonoServeur;

import linda.Tuple;
import linda.server.LindaClient;

public class TestClient2 {
	public static void main(String[] args) {
        LindaClient client = new LindaClient("//localhost:4000/LindaServer");
        LindaClient client2 = new LindaClient("//localhost:4000/LindaServer");
        client.write(new Tuple("Hello", 42));
        client2.write(new Tuple(42, 2));
        System.out.println("Expected : [42 2]");
        Tuple result = client.read(new Tuple(Integer.class, Integer.class));
        System.out.println("Result : " + result);
    }
}
