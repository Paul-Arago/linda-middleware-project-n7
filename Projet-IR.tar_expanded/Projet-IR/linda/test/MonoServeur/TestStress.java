package linda.test.MonoServeur;

import linda.Tuple;
import linda.server.LindaClient;

import java.util.Collection;

public class TestStress {
    private static final int NUM_TUPLES = 1000;

    public static void main(String[] args) {
        // Writer thread
        new Thread(() -> {
            LindaClient client = new LindaClient("//localhost:4000/LindaServer");
            for (int i = 0; i < NUM_TUPLES; i++) {
                client.write(new Tuple("stress", i));
            }
            System.out.println("Writer: Finished writing " + NUM_TUPLES + " tuples.");
        }).start();

        // Reader thread
        new Thread(() -> {
            LindaClient client = new LindaClient("//localhost:4000/LindaServer");
            int taken = 0;
            for (int i = 0; i < NUM_TUPLES; i++) {
                Tuple t = client.take(new Tuple("stress", Integer.class));
                taken++;
                if (taken % 100 == 0) {
                    System.out.println("Reader: Taken " + taken + " tuples.");
                }
            }
            System.out.println("Reader: Finished taking " + taken + " tuples.");

            // Check tuple space is empty for this pattern
            Collection<Tuple> remaining = client.readAll(new Tuple("stress", Integer.class));
            System.out.println("Remaining tuples matching (\"stress\", Integer.class): " + remaining.size());
            if (remaining.isEmpty()) {
                System.out.println("Test passed: Tuple space is empty for the tested pattern.");
            } else {
                System.out.println("Test failed: Tuple space is not empty!");
            }
        }).start();
    }
}