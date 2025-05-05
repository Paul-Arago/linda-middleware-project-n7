package linda.shm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import linda.Callback;
import linda.Linda;
import linda.Tuple;

/** Shared memory implementation of Linda. */
public class CentralizedLinda implements Linda {
	
	private ArrayList<Tuple> tuplesList;
	
    public CentralizedLinda() {
    	this.tuplesList = new ArrayList<Tuple>();
    }

	@Override
	public void write(Tuple t) {
		this.tuplesList.add(t.deepclone());
	}

	@Override
	public Tuple take(Tuple template) {
		return null;
	}

	@Override
	public Tuple read(Tuple template) {
		return null;
	}

	@Override
	public Tuple tryTake(Tuple template) {
		int index = -1;
		for(int i = 0; i < this.tuplesList.size(); i++) {
			if(this.tuplesList.get(i).matches(template)) {
				index = i;
				break;
			}
		}
		return index != - 1 ? this.tuplesList.remove(index) : null;
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
