package linda.test.MonoServeur;

import linda.Callback;
import linda.Linda;
import linda.Tuple;
import linda.Linda.eventMode;
import linda.Linda.eventTiming;

public class TestImmediateTakeReregister {
	private static Linda linda;
    private static Tuple cbmotif;

    private static class MyCallback implements Callback {
        public void call(Tuple t) {
            System.out.println(">>> CB got " + t);
            linda.eventRegister(eventMode.TAKE, eventTiming.IMMEDIATE, cbmotif, this);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            System.out.println(">>> CB done with " + t);
        }
    }

    public static void main(String[] args) {
        linda = new linda.server.LindaClient("//localhost:4000/LindaServer");

        cbmotif = new Tuple(Integer.class, String.class);
        linda.eventRegister(eventMode.TAKE, eventTiming.IMMEDIATE, cbmotif, new MyCallback());

        linda.write(new Tuple(1, "a"));
        linda.write(new Tuple(2, "b"));
        linda.write(new Tuple(3, "c"));
        linda.write(new Tuple("ignore", 5));
        linda.write(new Tuple(4, "d"));

        try {
            Thread.sleep(3000); // attendre que tous les callbacks se terminent
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        linda.debug("Fin du test de re-enregistrement");
    }
}
