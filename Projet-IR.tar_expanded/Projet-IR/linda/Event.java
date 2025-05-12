package linda;

import linda.Linda.eventMode;
import linda.Linda.eventTiming;

public class Event {
	private eventTiming timing;
	private eventMode mode;
	private Tuple template;
	private Callback callback;
	
	public Event(eventMode mode, eventTiming timing, Tuple template, Callback callback) {
		this.mode = mode;
		this.timing = timing;
		this.template = template;
		this.callback = callback;
	}
	
	public eventTiming getTiming() {
		return timing;
	}
	public void setTiming(eventTiming timing) {
		this.timing = timing;
	}
	public eventMode getMode() {
		return mode;
	}
	public void setMode(eventMode mode) {
		this.mode = mode;
	}
	public Tuple getTemplate() {
		return template;
	}
	public void setTemplate(Tuple tuple) {
		this.template = tuple;
	}
	public Callback getCallback() {
		return callback;
	}
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
}
