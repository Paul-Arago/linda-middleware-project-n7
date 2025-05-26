package linda.test.MonoServeur;

import linda.Tuple;
import linda.server.LindaClient;

public class TestTakeBlocking {
    public static void main(String[] args) {
        new Thread() {
            public void run() {
                LindaClient client = new LindaClient("//localhost:4000/LindaServer");
                Tuple pattern = new Tuple(String.class, Integer.class);
                System.out.println("Thread 1: Calling take, will block until a matching tuple is available...");
                Tuple result = client.take(pattern);
                System.out.println("Thread 1: Unblocked! Got tuple: " + result);
            }
        }.start();

        new Thread() {
            public void run() {
                LindaClient client = new LindaClient("//localhost:4000/LindaServer");
                try { Thread.sleep(2000); } catch (InterruptedException e) {}
                client.write(new Tuple(42, 99));
                System.out.println("Thread 2: Wrote [42, 99] (not matching)");
                client.write(new Tuple("foo", "bar"));
                System.out.println("Thread 2: Wrote [foo, bar] (not matching)");
                try { Thread.sleep(2000); } catch (InterruptedException e) {}
                Tuple matching = new Tuple("Hello", 123);
                client.write(matching);
                System.out.println("Thread 2: Wrote " + matching + " (matching)");
            }
        }.start();
    }
}