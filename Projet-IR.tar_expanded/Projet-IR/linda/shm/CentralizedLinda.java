package linda.shm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import linda.Callback;
import linda.Event;
import linda.Linda;
import linda.Tuple;

/** Shared memory implementation of Linda. */
public class CentralizedLinda implements Linda {
	
	private ArrayList<Tuple> tupleSpace;
	private final Map<Thread, Tuple> waitingThreads;
	private ArrayList<Event> registeredEvents;

    public CentralizedLinda() {
    	this.tupleSpace = new ArrayList<Tuple>();
		this.waitingThreads = new HashMap<>();
		this.registeredEvents = new ArrayList<>();
    }

	@Override
	public synchronized void write(Tuple t) {
		this.tupleSpace.add(t.deepclone());
		
		for(int i = 0; i < this.registeredEvents.size(); i++) {
			Event event = this.registeredEvents.get(i);
			this.triggerCallback(event.getTemplate(), event.getMode(), event.getCallback(), i);
		}
		
		var iterator = waitingThreads.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Thread, Tuple> entry = iterator.next();
			if (t.matches(entry.getValue())) {
				iterator.remove(); // Safely remove the entry
				synchronized (entry.getKey()) {
					entry.getKey().notify();
				}
			}
		}
	}

	@Override
	public Tuple take(Tuple template) {
		try {
			Tuple result;
			while (true) {
				synchronized (this) {
					result = tryTake(template);
					if (result != null) {
						return result;
					}
					waitingThreads.put(Thread.currentThread(), template);
				}
				
                synchronized (Thread.currentThread()) {
                    Thread.currentThread().wait();
                }

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

	@Override
	public Tuple read(Tuple template) {
		try {
			Tuple result;
			while (true) {
				synchronized (this) {
					result = this.tryRead(template);
					if (result != null) {
						return result;
					}
					waitingThreads.put(Thread.currentThread(), template);
				}

				synchronized (Thread.currentThread()) {
					Thread.currentThread().wait();
				}
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
	public synchronized Collection<Tuple> takeAll(Tuple template) {
		Collection<Tuple> result = new ArrayList<>();
		for (Tuple t : this.tupleSpace) {
			if (t.matches(template)) {
				result.add(t);
			}
		}
		this.tupleSpace.removeAll(result);
		return result;
	}

	@Override
	public synchronized Collection<Tuple> readAll(Tuple template) {
		Collection<Tuple> result = new ArrayList<>();
		for(Tuple t : this.tupleSpace) {
			if(t.matches(template)) {
				result.add(t.deepclone());
			}
		}
		return result;
	}
	
	private void triggerCallback(Tuple template, eventMode mode, Callback callback, int index) {
		Tuple tuple;
		if(mode == eventMode.READ) {
			tuple = this.tryRead(template);
		} else {
			tuple = this.tryTake(template);		
		}
		if(tuple != null) {
			callback.call(tuple);
			this.registeredEvents.remove(index);
		}
	}

	@Override
	public void eventRegister(eventMode mode, eventTiming timing, Tuple template, Callback callback) {
		Event event = new Event(mode, timing, template, callback);
		this.registeredEvents.add(event);
		if(timing == eventTiming.IMMEDIATE) {
			this.triggerCallback(template, mode, callback, this.registeredEvents.size() - 1);
		}
	}

	@Override
	public synchronized void debug(String prefix) {
		System.out.println(" ------------ " + prefix + " ---------");
		for (Tuple t : this.tupleSpace) {
			System.out.println(t.toString());
		}
	 		System.out.println(" --------------------------");
	}
}
