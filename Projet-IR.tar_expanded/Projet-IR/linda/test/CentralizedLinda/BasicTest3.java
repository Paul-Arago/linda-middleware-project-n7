package linda.test.CentralizedLinda;

import linda.Linda;
import linda.Tuple;

public class BasicTest3 {
	public static void main(String[] a) {
        
        final Linda linda = new linda.shm.CentralizedLinda();
        // final Linda linda = new linda.server.LindaClient("rmi://localhost:4000/LindaServer");
                
        new Thread() {
            public void run() {
                Tuple motif = new Tuple(Integer.class, Integer.class);
                Tuple res = linda.take(motif);
                System.out.println("(1) Resultat:" + res);
                linda.debug("(1)");
            }
        }.start();
                
        new Thread() {
            public void run() {
            	try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Tuple t1 = new Tuple(4, 5);
                System.out.println("(2) write: " + t1);
                linda.write(t1);

                Tuple t2 = new Tuple("hello", 15);
                System.out.println("(2) write: " + t2);
                linda.write(t2);

                Tuple t3 = new Tuple(4, "foo");
                System.out.println("(2) write: " + t3);
                linda.write(t3);
                                
                linda.debug("(2)");
            }
        }.start();
                
    }
}
