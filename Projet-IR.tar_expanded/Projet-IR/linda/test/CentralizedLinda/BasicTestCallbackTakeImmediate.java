package linda.test.CentralizedLinda;

import linda.Callback;
import linda.Linda;
import linda.Tuple;
import linda.Linda.eventMode;
import linda.Linda.eventTiming;

public class BasicTestCallbackTakeImmediate {
	private static Linda linda;
    private static Tuple cbmotif;
    
    private static class MyCallback implements Callback {
        public void call(Tuple t) {
            System.out.println("CB got "+t);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.println("CB done with "+t);
        }
    }

    public static void main(String[] a) {
        linda = new linda.shm.CentralizedLinda();
        
        Tuple t4 = new Tuple(5, "bar");
        System.out.println("(2) write: " + t4);
        linda.write(t4);
        linda.debug("(2)");

        cbmotif = new Tuple(Integer.class, String.class);
        linda.eventRegister(eventMode.TAKE, eventTiming.IMMEDIATE, cbmotif, new MyCallback());

        Tuple t1 = new Tuple(4, 5);
        System.out.println("(2) write: " + t1);
        linda.write(t1);

        Tuple t2 = new Tuple("hello", 15);
        System.out.println("(2) write: " + t2);
        linda.write(t2);
        linda.debug("(2)");

        Tuple t3 = new Tuple(4, "foo");
        System.out.println("(2) write: " + t3);
        linda.write(t3);

        linda.debug("(2)");
        
        Tuple t5 = new Tuple("oe", 15);
        System.out.println("(2) write: " + t5);
        linda.write(t5);
        linda.debug("(2)");

    }
}
