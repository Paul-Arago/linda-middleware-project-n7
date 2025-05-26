package linda.test.CentralizedLinda;

import java.util.Collection;

import linda.Linda;
import linda.Tuple;

public class ReadAllTest2 {
	/*
	 * takeAll test 2
	 */
	public static void main(String[] a) {
        final Linda linda = new linda.shm.CentralizedLinda();
        // final Linda linda = new linda.server.LindaClient("rmi://localhost:4000/MonServeur");
                
        new Thread() {
            public void run() {  
            	try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            	
            	Tuple motif = new Tuple(Integer.class, Integer.class);
            	Collection<Tuple> res = linda.readAll(motif);
            	System.out.println("Résultats : ");
            	for(Tuple t : res) {
            		System.out.println(t);
            	}
                linda.debug("(2)");
            }
        }.start();
        
        new Thread() {
            public void run() {
                Tuple t1 = new Tuple(4, 5);
                System.out.println("(1) write: " + t1);
                linda.write(t1);

                Tuple t2 = new Tuple("hello", 15);
                System.out.println("(1) write: " + t2);
                linda.write(t2);

                Tuple t3 = new Tuple(4, "foo");
                System.out.println("(1) write: " + t3);
                linda.write(t3);                            
                
                Tuple t4 = new Tuple(4, 5);
                System.out.println("(1) write: " + t4);
                linda.write(t4);
                
                Tuple t5 = new Tuple(4, 5);
                System.out.println("(1) write: " + t5);
                linda.write(t5);
                
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                linda.debug("(1)");
            }
        }.start();
                
    }
}
