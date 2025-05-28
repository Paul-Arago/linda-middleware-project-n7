package linda.test.MonoServeur;

import linda.*;

public class TestCallbackReadImmediate {
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
        linda = new linda.server.LindaClient("//localhost:4000/LindaServer");

        cbmotif = new Tuple(Integer.class, String.class);
        linda.eventRegister(Linda.eventMode.READ, Linda.eventTiming.IMMEDIATE, cbmotif, new MyCallback());

        linda.write(new Tuple(4, 5));
        //linda.write(new Tuple("hello", 15));
        //linda.write(new Tuple(4, "foo"));
    }
}
