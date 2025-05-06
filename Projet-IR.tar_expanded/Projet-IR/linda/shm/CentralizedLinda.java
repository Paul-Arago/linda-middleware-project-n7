package linda.shm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import linda.Callback;
import linda.Linda;
import linda.Tuple;

/** Shared memory implementation of Linda. */
public class CentralizedLinda implements Linda {
	
	private ArrayList<Tuple> tupleSpace;
	private final Map<Thread, Tuple> waitingThreads;

    public CentralizedLinda() {
    	this.tupleSpace = new ArrayList<Tuple>();
		this.waitingThreads = new HashMap<>();
    }

	@Override
	public void write(Tuple t) {
		this.tupleSpace.add(t.deepclone());
		for (Map.Entry<Thread, Tuple> entry : waitingThreads.entrySet()) {
			if (t.matches(entry.getValue())) {
				waitingThreads.remove(entry.getKey());
				entry.getKey().notify();
				break;
			}
		}
	}

	@Override
	public Tuple take(Tuple template) {
		try {
			while (true) {
				Tuple result;
				synchronized (this) {
					result = tryTake(template);
					if (result != null) {
						return result;
					}
					waitingThreads.put(Thread.currentThread(), template);
				}

				wait();

			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			synchronized (this) {
				waitingThreads.remove(Thread.currentThread());
			}
		}
	}
	
	public Tuple read(Tuple template) {
		while(true) {
			try {
				Thread.sleep(1);
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
			this.readImpl(template);
		}

	}

	/*private Tuple readImpl(Tuple template) {
		for(Tuple t : this.tuplesList) {
			if(t.matches(template)) 
				return t.deepclone();
		}
		return null;
	}
	
	@Override
	public Tuple read(Tuple template) {
		Tuple foundTuple = this.tryRead(template);
		if(foundTuple == null) {
			this.eventRegister(eventMode.READ, null, template, this::readImpl);
			while(true) {
				try {
		            Thread.sleep(1);
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
				this.readImpl(template);
			}
		} else {
			return foundTuple;
		}
	}
	*/

	@Override
	public synchronized Tuple tryTake(Tuple template) {
		int index = -1;
		for(int i = 0; i < this.tupleSpace.size(); i++) {
			if(this.tupleSpace.get(i).matches(template)) {
				index = i;
				break;
			}
		}
        return index != - 1 ? this.tupleSpace.remove(index) : null;
	}

	@Override
	public synchronized Tuple tryRead(Tuple template) {
		for(Tuple t : this.tupleSpace) {
			if(t.matches(template)) 
				return t.deepclone();
		}
		return null;
	}

	@Override
	public Collection<Tuple> takeAll(Tuple template) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Tuple> readAll(Tuple template) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eventRegister(eventMode mode, eventTiming timing, Tuple template, Callback callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debug(String prefix) {
		// TODO Auto-generated method stub
		
	}

    // TO BE COMPLETED

}
