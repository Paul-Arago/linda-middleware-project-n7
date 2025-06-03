package linda.test.MonoServeur;

import linda.Callback;
import linda.Linda;
import linda.Tuple;

public class TestCallbackReadFuture {
	private static Linda linda;
    private static Tuple cbmotif;

    private static class MyCallback implements Callback {
        public void call(Tuple t) {
            System.out.println("CB got " + t);
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            System.out.println("CB done with " + t);
        }
    }

    public static void main(String[] args) {
        linda = new linda.server.LindaClient("rmi://localhost:4000/LindaServer");

        Tuple t4 = new Tuple(5, "bar");
        linda.write(t4);

        cbmotif = new Tuple(Integer.class, String.class);
        linda.eventRegister(Linda.eventMode.READ, Linda.eventTiming.FUTURE, cbmotif, new MyCallback());
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        linda.write(new Tuple(4, 5));
    }
}
