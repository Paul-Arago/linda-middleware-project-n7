package linda.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

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
}
