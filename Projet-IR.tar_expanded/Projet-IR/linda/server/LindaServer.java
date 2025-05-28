package linda.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

import linda.Callback;
import linda.Linda;
import linda.RemoteCallback;
import linda.RemoteLinda;
import linda.Tuple;
import linda.shm.CentralizedLinda;

public class LindaServer extends UnicastRemoteObject implements RemoteLinda {
	private CentralizedLinda linda;

    public LindaServer() throws RemoteException {
        super();
        this.linda = new CentralizedLinda();
    }

    public void write(Tuple t) throws RemoteException {
        linda.write(t);
    }

    public Tuple take(Tuple template) throws RemoteException {
        return linda.take(template);
    }

    public Tuple read(Tuple template) throws RemoteException {
        return linda.read(template);
    }

    public Tuple tryTake(Tuple template) throws RemoteException {
        return linda.tryTake(template);
    }

    public Tuple tryRead(Tuple template) throws RemoteException {
        return linda.tryRead(template);
    }

    public Collection<Tuple> takeAll(Tuple template) throws RemoteException {
        return linda.takeAll(template);
    }

    public Collection<Tuple> readAll(Tuple template) throws RemoteException {
        return linda.readAll(template);
    }

    public void debug(String prefix) throws RemoteException {
        linda.debug(prefix);
    }

	@Override
	public void eventRegister(RemoteLinda.eventMode mode, RemoteLinda.eventTiming timing, Tuple template, RemoteCallback remoteCb) throws RemoteException {
	    // Convertir vers les enums de Linda attendus par l’implémentation locale
	    Linda.eventMode lMode = Linda.eventMode.valueOf(mode.name());
	    Linda.eventTiming lTiming = Linda.eventTiming.valueOf(timing.name());

	    // Wrapping du RemoteCallback dans un Callback local
	    Callback cb = (Tuple t) -> {
	        try {
	            remoteCb.call(t);
	        } catch (RemoteException e) {
	            e.printStackTrace();
	        }
	    };

	    // Enregistrement du callback
	    this.linda.eventRegister(lMode, lTiming, template, cb);
	}
}
