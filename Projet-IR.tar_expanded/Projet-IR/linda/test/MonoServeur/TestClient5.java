package linda.test.MonoServeur;

import java.util.Collection;

import linda.Tuple;
import linda.server.LindaClient;

public class TestClient5 {
	public static void main(String[] args) {
		new Thread() {
            public void run() {
            	LindaClient client = new LindaClient("//localhost:4000/LindaServer");
            	client.write(new Tuple("Hello", 42));
            	client.write(new Tuple(32, 42));
            	client.write(new Tuple("World", 89));
            }
        }.start();
        
        new Thread() {
            public void run() {
            	try {
            		Thread.sleep(2000);
            	} catch(InterruptedException e) {
            	}
            	LindaClient client = new LindaClient("//localhost:4000/LindaServer");
            	Collection<Tuple> res = client.takeAll(new Tuple(String.class, Integer.class));
            	System.out.println("Expected : [ Hello 42 ], [World 89]");
            	System.out.println("Res : ");
            	for(Tuple t : res) {
            		System.out.println(t);
            	}
            	Collection<Tuple> res2 = client.readAll(new Tuple(String.class, Integer.class));
            	System.out.println("Expected size for String, Integer : 0\nSize of TupleSpace for String,Integer : " + res2.size());
            	Collection<Tuple> res3 = client.readAll(new Tuple(Integer.class, Integer.class));
            	System.out.println("Expected size for Integer, Integer: 1\nSize of TupleSpace for Integer,Integer : " + res3.size());
            }
        }.start();
    }
}
