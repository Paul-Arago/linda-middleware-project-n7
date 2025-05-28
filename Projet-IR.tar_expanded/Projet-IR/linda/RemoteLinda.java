package linda;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

import linda.Linda.eventMode;
import linda.Linda.eventTiming;

public interface RemoteLinda extends Remote {
	void write(Tuple t) throws RemoteException;
    Tuple take(Tuple template) throws RemoteException;
    Tuple read(Tuple template) throws RemoteException;
    Tuple tryTake(Tuple template) throws RemoteException;
    Tuple tryRead(Tuple template) throws RemoteException;
    Collection<Tuple> takeAll(Tuple template) throws RemoteException;
    Collection<Tuple> readAll(Tuple template) throws RemoteException;
    void debug(String prefix) throws RemoteException;
    void eventRegister(eventMode mode, eventTiming timing, Tuple template, RemoteCallback cb) throws RemoteException;
    public enum eventMode { READ, TAKE };
    public enum eventTiming { IMMEDIATE, FUTURE };
}
