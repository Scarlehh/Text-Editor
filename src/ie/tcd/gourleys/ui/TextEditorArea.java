package ie.tcd.gourleys.ui;

import ie.tcd.gourleys.system.History;

import java.awt.TextArea;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@SuppressWarnings("serial")
public class TextEditorArea extends TextArea implements KeyListener {

	private History history = new History();
	boolean invalid = false;

	public TextEditorArea(String contents) {
		super(contents);
		this.addKeyListener(this);
		history.addHistory(this.getText());
		System.out.println("Updated history");
	}
	
	public TextEditorArea() {
		this("");
	}
	
	/* ====================================================================
	 * 		history handlers
	 * ====================================================================	
	 */
	
	public void undo() {
		int caret = getCaretPosition();
		this.setText(history.previous());
		setCaretPosition(caret);
	}
	
	public void redo() {
		int caret = getCaretPosition();
		this.setText(history.next());
		setCaretPosition(caret);
	}
	
	/* ====================================================================
	 * 		key listener methods
	 * ====================================================================	
	 */
	
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_CONTROL) {
			invalid = true;
		}
	}

	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_CONTROL) {
			invalid = false;
		}
	}

	public void keyTyped(KeyEvent arg0) {
		if (!invalid) {
			history.addHistory(this.getText());
			System.out.println("Updated history");
		}
	}

}
