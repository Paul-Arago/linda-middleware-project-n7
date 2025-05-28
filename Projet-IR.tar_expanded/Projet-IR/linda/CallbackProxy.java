package linda;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CallbackProxy extends UnicastRemoteObject implements RemoteCallback {
	
	private final Callback localCallback;

    public CallbackProxy(Callback cb) throws RemoteException {
        this.localCallback = cb;
    }

    @Override
    public void call(Tuple t) throws RemoteException {
        localCallback.call(t);
    }
}
