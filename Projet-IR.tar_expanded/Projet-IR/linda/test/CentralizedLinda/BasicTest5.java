package linda.test.CentralizedLinda;

import linda.Linda;
import linda.Tuple;

public class BasicTest5 {
	
	public static void main(String[] a) {
        final Linda linda = new linda.shm.CentralizedLinda();
        // final Linda linda = new linda.server.LindaClient("rmi://localhost:4000/MonServeur");
                
        for (int i = 1; i <= 3; i++) {
            final int j = i;
            new Thread() {  
                public void run() {
                    Tuple motif = new Tuple(Integer.class, Integer.class);
                    Tuple res = linda.take(motif);
                    System.out.println("("+j+") Resultat:" + res);
                    linda.debug("("+j+")");
                }
            }.start();
        }
                
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Tuple t1 = new Tuple(4, 5);
                System.out.println("(4) write: " + t1);
                linda.write(t1);

                Tuple t2 = new Tuple("hello", 15);
                System.out.println("(4) write: " + t2);
                linda.write(t2);

                Tuple t3 = new Tuple(4, "foo");
                System.out.println("(4) write: " + t3);
                linda.write(t3);                            
                
                Tuple t4 = new Tuple(4, 5);
                System.out.println("(4) write: " + t4);
                linda.write(t4);
                
                Tuple t5 = new Tuple(4, 5);
                System.out.println("(4) write: " + t5);
                linda.write(t5);
                
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                linda.debug("(4)");
            }
        }.start();
                
    }
}
