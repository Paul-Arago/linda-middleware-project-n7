package linda.test;

import linda.*;
import linda.shm.CentralizedLinda;

public class BasicTest4 {

    public static void main(String[] a) {
        final Linda linda = new linda.shm.CentralizedLinda();
        // final Linda linda = new linda.server.LindaClient("rmi://localhost:4000/MonServeur");
        int max = 3000;
        for (int i = 1; i <= max; i++) {
            final int j = i;
            new Thread() {  
                public void run() {
                    Tuple motif;
                    if (j < max/3) {
                        motif = new Tuple(Integer.class, Integer.class);
                    }
                    else if (j > max/3 && j < 2*max/3) {
                        motif = new Tuple(String.class, Integer.class);
                    } else {
                        motif = new Tuple(String.class, String.class);
                    }

                    Tuple res = linda.take(motif);
                    System.out.println("("+j+") Resultat (take):" + res);
                    linda.debug("("+j+")");
                }
            }.start();
        }

        for (int i = 1; i <= max; i++) {
            final int j = i+max;

            new Thread() {  
                public void run() {
                    Tuple t;
                    if ((j-max) < max/3) {
                        t = new Tuple(4, 5);
                    } 
                    else if ((j-max) > max/3 && (j-max) < 2*max/3) {
                        t = new Tuple("hello", 15);
                    } else {
                        t = new Tuple("foo", "bar");
                    }

                    linda.write(t);
                    linda.debug("("+j+")");
                }
            }.start();
        }
    }
}
