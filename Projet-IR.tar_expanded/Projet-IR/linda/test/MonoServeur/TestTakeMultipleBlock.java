package linda.test.MonoServeur;

import linda.Tuple;
import linda.server.LindaClient;

public class TestTakeMultipleBlock {
    private static final int NUM_TAKERS = 4;

    public static void main(String[] args) {
        // Start taker threads
        for (int i = 0; i < NUM_TAKERS; i++) {
            final int id = i;
            new Thread(() -> {
                LindaClient client = new LindaClient("//localhost:4000/LindaServer");
                System.out.println("Taker " + id + ": Calling take...");
                Tuple t = client.take(new Tuple("token"));
                System.out.println("Taker " + id + ": Got tuple: " + t);
            }).start();
        }

        // Writer thread
        new Thread(() -> {
            LindaClient client = new LindaClient("//localhost:4000/LindaServer");
            try { Thread.sleep(2000); } catch (InterruptedException e) {}
            System.out.println("Writer: Writing one tuple...");
            client.write(new Tuple("token"));

            try { Thread.sleep(2000); } catch (InterruptedException e) {}
            System.out.println("Writer: Writing " + (NUM_TAKERS - 1) + " more tuples...");
            for (int i = 1; i < NUM_TAKERS; i++) {
                client.write(new Tuple("token"));
            }
        }).start();
    }
}