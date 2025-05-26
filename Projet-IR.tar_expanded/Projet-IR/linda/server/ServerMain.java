package linda.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {
	public static void main(String[] args) {
        try {
            RemoteLinda lindaServer = new LindaServer();
            Registry registry = LocateRegistry.createRegistry(4000);
            registry.bind("LindaServer", lindaServer);
            System.out.println("Linda server ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}