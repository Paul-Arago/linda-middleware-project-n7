package linda.test.MonoServeur;

import linda.Tuple;
import linda.server.LindaClient;

public class TestNullTuplesTemplates {
    public static void main(String[] args) {
        LindaClient client = new LindaClient("rmi://localhost:4000/LindaServer");

        try {
            System.out.println("Testing write with null tuple...");
            client.write(null);
        } catch (Exception e) {
            System.out.println("Caught exception on write(null): " + e);
        }

        try {
            System.out.println("Testing read with null template...");
            client.read(null);
        } catch (Exception e) {
            System.out.println("Caught exception on read(null): " + e);
        }

        try {
            System.out.println("Testing take with null template...");
            client.take(null);
        } catch (Exception e) {
            System.out.println("Caught exception on take(null): " + e);
        }
    }
}