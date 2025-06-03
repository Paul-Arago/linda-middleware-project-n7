package linda.server;

import java.net.URI;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collection;

import linda.Callback;
import linda.Linda;
import linda.RemoteLinda;
import linda.Tuple;
import linda.RemoteCallback;
import linda.CallbackProxy;

/** Client part of a client/server implementation of Linda.
 * It implements the Linda interface and propagates everything to the server it is connected to.
 * */
public class LindaClient implements Linda {
	
    private RemoteLinda server;

    public LindaClient(String serverURI) {
    	try {
            URI uri = new URI(serverURI);
            String host = uri.getHost();
            int port = uri.getPort();
            String name = uri.getPath().substring(1);
            Registry registry = LocateRegistry.getRegistry(host, port);
            this.server = (RemoteLinda) registry.lookup(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(Tuple t) {
        try {
            server.write(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Tuple take(Tuple template) {
        try {
            return server.take(template);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Tuple read(Tuple template) {
        try {
            return server.read(template);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Tuple tryTake(Tuple template) {
        try {
            return server.tryTake(template);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Tuple tryRead(Tuple template) {
        try {
            return server.tryRead(template);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Collection<Tuple> takeAll(Tuple template) {
        try {
            return server.takeAll(template);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Collection<Tuple> readAll(Tuple template) {
        try {
            return server.readAll(template);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void eventRegister(eventMode mode, eventTiming timing, Tuple template, Callback callback) {
    	try {
            RemoteCallback cbProxy = new CallbackProxy(callback);
            // Convertir les enums vers ceux attendus par RemoteLinda
            RemoteLinda.eventMode rMode = RemoteLinda.eventMode.valueOf(mode.name());
            RemoteLinda.eventTiming rTiming = RemoteLinda.eventTiming.valueOf(timing.name());
            server.eventRegister(rMode, rTiming, template, cbProxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void debug(String prefix) {
        try {
            server.debug(prefix);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
