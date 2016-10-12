package ie.tcd.gourleys.system;

import java.util.Stack;

public class History {

	public static final int LENGTH = 30;
	private Stack<String> history;
	private Stack<String> save;
	
	public History() {
		history = new Stack<>();
		save = new Stack<>();
	}
	
	public void addHistory(String text) {
		if(!save.empty()) {
			save.removeAllElements();
		}
		if (history.size() >= LENGTH) {
			history.removeElementAt(0);
		}
		history.push(text);
	}
	
	public String previous() {
		if(!history.empty()) {
			System.out.println(history.peek());
			save.push(history.pop());
			return save.peek();
		}
		return null;
	}
	
	public String next() {
		if(!save.empty()) {
			history.push(save.pop());
			return history.peek();
		}
		return null;
	}
	
}
