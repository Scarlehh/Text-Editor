package ie.tcd.gourleys.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class TextWindowEvent extends WindowAdapter implements WindowListener {
	
	public void windowActivated(WindowEvent arg0) {
		System.out.println("Window activated!");
	}

	public void windowClosed(WindowEvent arg0) {
		System.out.println("Successfully closed.");
	}

	public void windowClosing(WindowEvent arg0) {
		System.out.println("Closing.");
		arg0.getWindow().dispose();
	}

	public void windowDeactivated(WindowEvent arg0) {
		System.out.println("Window off screen.");
	}

	public void windowDeiconified(WindowEvent arg0) {
	}

	public void windowIconified(WindowEvent arg0) {
	}

	public void windowOpened(WindowEvent arg0) {
		System.out.println("Window on screen.");
	}

}
