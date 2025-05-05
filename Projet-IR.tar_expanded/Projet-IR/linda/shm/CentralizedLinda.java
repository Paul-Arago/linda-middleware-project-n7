package linda.shm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import linda.Callback;
import linda.Linda;
import linda.Tuple;

/** Shared memory implementation of Linda. */
public class CentralizedLinda implements Linda {
	
	private ArrayList<Tuple> tuplesList;
	private HashMap<Tuple, Boolean> runningTakeTemplate;
	
    public CentralizedLinda() {
    	this.tuplesList = new ArrayList<Tuple>();
    	this.runningTakeTemplate = new HashMap<>();
    }

	@Override
	public void write(Tuple t) {
		this.tuplesList.add(t.deepclone());
		//FAIRE QUELQUE CHOSE ICI POUR LE TAKE AVEC LE DICO
	}

	@Override
	public Tuple take(Tuple template) {
		return null;
		// tryTake
		// SI null 
		//    -> this.liste.add(template)
		//    -> WHILE true
			  
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
	public Tuple tryTake(Tuple template) {
		int index = -1;
		for(int i = 0; i < this.tuplesList.size(); i++) {
			if(this.tuplesList.get(i).matches(template)) {
				index = i;
				break;
			}
		}
		synchronized(this.tuplesList) {
			return index != - 1 ? this.tuplesList.remove(index) : null;
		}
	}

	@Override
	public Tuple tryRead(Tuple template) {
		for(Tuple t : this.tuplesList) {
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
