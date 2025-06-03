package linda.test.MonoServeur;

import linda.Callback;
import linda.Linda;
import linda.Tuple;
import linda.Linda.eventMode;
import linda.Linda.eventTiming;

public class TestImmediateTake {
	private static Linda linda;
    private static Tuple cbmotif;

    private static class MyCallback implements Callback {
        public void call(Tuple t) {
            System.out.println(">>> CB got " + t);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.println(">>> CB done with " + t);
        }
    }

    public static void main(String[] args) {
        // Connexion au serveur
        linda = new linda.server.LindaClient("rmi://localhost:4000/LindaServer");

        Tuple t1 = new Tuple(42, "foo");
        System.out.println("(1) write: " + t1);
        linda.write(t1);
        linda.debug("Après premier write");

        cbmotif = new Tuple(Integer.class, String.class);
        linda.eventRegister(eventMode.TAKE, eventTiming.IMMEDIATE, cbmotif, new MyCallback());

        Tuple t2 = new Tuple("bar", 17);
        System.out.println("(1) write: " + t2);
        linda.write(t2);

        Tuple t3 = new Tuple(7, "baz");
        System.out.println("(1) write: " + t3);
        linda.write(t3);

        linda.debug("Fin du test");
    }
}
