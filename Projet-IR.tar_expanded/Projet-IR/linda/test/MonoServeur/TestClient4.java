package linda.test.MonoServeur;

import java.util.Collection;

import linda.Tuple;
import linda.server.LindaClient;

public class TestClient4 {
	public static void main(String[] args) {
		new Thread() {
            public void run() {
            	LindaClient client = new LindaClient("//localhost:4000/LindaServer");
            	client.write(new Tuple("Hello", 42));
            }
        }.start();
        
        new Thread() {
            public void run() {
            	LindaClient client = new LindaClient("//localhost:4000/LindaServer");
            	Tuple res = client.take(new Tuple(String.class, Integer.class));
            	System.out.println("Expected : [ Hello 42 ]");
            	System.out.println("Res : " + res);
            	Collection<Tuple> res2 = client.readAll(new Tuple(String.class, Integer.class));
            	System.out.println("Expected size : 0\nSize of TupleSpace : " + res2.size());
            }
        }.start();
    }
}
