package ie.tcd.gourleys.system;

public class History {

	public static final int LENGTH = 30;
	private String[] history;
	private int index;
	
	public History() {
		history = new String[LENGTH];
		index = 0;
	}
	
	public void addHistory(String text) {
		if (index == LENGTH) {
			shiftHistory();
		}
		history[index] = text;
		index++;
	}
	
	public void addHistory(char letter) {
		addHistory(letter + "");
	}
	
	public void shiftHistory() {
		for (int i = 0; i < history.length-1; i++) {
			history[i] = history[i+1];
		}
	}
	
	public String previous() {
		if (index > 1) {
			index--;
			return history[index];
		}
		return history[index-1];
	}
	
	public String next() {
		if (index < LENGTH) {
			if (history[index] != null) {
				index++;
				if (history[index] != null) {
					return history[index];
				}
			}
		}
		return history[index-1];
	}
	
}
