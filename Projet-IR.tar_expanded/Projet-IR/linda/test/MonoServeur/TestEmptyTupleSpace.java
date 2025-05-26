package linda.test.MonoServeur;

import linda.Tuple;
import linda.server.LindaClient;

public class TestEmptyTupleSpace {
    public static void main(String[] args) {
        LindaClient client = new LindaClient("//localhost:4000/LindaServer");

        System.out.println("Testing read on empty tuple space (should block)...");
        new Thread(() -> {
            Tuple result = client.read(new Tuple(String.class, Integer.class));
            System.out.println("Read result: " + result);
        }).start();

        System.out.println("Testing take on empty tuple space (should block)...");
        new Thread(() -> {
            Tuple result = client.take(new Tuple(String.class, Integer.class));
            System.out.println("Take result: " + result);
        }).start();

        System.out.println("Testing tryTake on empty tuple space (should return null)...");
        Tuple tryTakeResult = client.tryTake(new Tuple(String.class, Integer.class));
        System.out.println("tryTake result: " + tryTakeResult);
    }
}