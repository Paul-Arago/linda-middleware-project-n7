package linda.test.MonoServeur;

import linda.Tuple;
import linda.server.LindaClient;

public class TryTakeTestClient {
    public static void main(String[] args) {
        LindaClient client = new LindaClient("//localhost:4000/LindaServer");

        new Thread() {
            public void run() {
                LindaClient client = new LindaClient("//localhost:4000/LindaServer");
                Tuple motif = new Tuple(Integer.class, Integer.class);
                Tuple result = client.tryTake(motif);
                System.out.println("(2) Resultat:" + result);

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                result = client.tryTake(motif);
                System.out.println("(2) Resultat:" + result);
            }
        }.start();


        new Thread() {
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                LindaClient client = new LindaClient("//localhost:4000/LindaServer");
                Tuple t1 = new Tuple(4, 5);
                System.out.println("(1) write: " + t1);
                client.write(t1);

                Tuple t2 = new Tuple("hello", 15);
                System.out.println("(1) write: " + t2);
                client.write(t2);

                Tuple t3 = new Tuple(4, "foo");
                System.out.println("(1) write: " + t3);
                client.write(t3);

                Tuple t4 = new Tuple(4, 5);
                System.out.println("(1) write: " + t4);
                client.write(t4);

                Tuple t5 = new Tuple(4, 5);
                System.out.println("(1) write: " + t5);
                client.write(t5);

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
