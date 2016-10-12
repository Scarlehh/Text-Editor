package ie.tcd.gourleys.ui;

import ie.tcd.gourleys.system.*;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.GridLayout;
import java.awt.event.*;

public class Window {
	private JFrame window;
	private TTextArea textArea;
	private FileManager fileManager;

	private class TTextArea extends JTextArea implements Runnable {
		History history;
		private String prev;
		
		public TTextArea() {
			super();
			history = new History();
			prev = this.getText();
		}
		
		public void start() {
			Thread thread = new Thread(this);
			thread.start();
		}
		
		@Override
		public void run() {
			while(true) {
				if(!prev.equals(this.getText())) {
					prev = this.getText();
					history.addHistory(this.getText());
				}
				try {
					Thread.sleep(1000);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public void redo() {
			this.setText(history.next());
			if(prev.equals(this.getText())) {
				this.setText(history.next());
			}
			prev = this.getText();
		}

		public void undo() {
			this.setText(history.previous());
			if(prev.equals(this.getText())) {
				this.setText(history.previous());
			}
			prev = this.getText();
		}
	}

	public Window() {
		this(null, "", null);
	}

	public Window(String title) {
		this(title, "", null);
	}

	public Window(String title, String contents) {
		this(title, contents, null);
	}

	public Window(String title, String contents, String dir) {
		if(title == null) {
			window = new JFrame("New Editor");
		} else {
			window = new JFrame(title);
		}
		window.setSize(300, 400);
		window.setLayout(new GridLayout(1, 1));

		textArea = new TTextArea();
		textArea.setLineWrap(true);
		textArea.setText(contents);
		JScrollPane scrollPane = new JScrollPane(textArea);
		window.add(scrollPane);

		setMenuButtons();
		textArea.start();
		fileManager = new FileManager(title, dir);
		window.setVisible(true);
	}

	private void setMenuButtons() {
		JMenuBar menubar = new JMenuBar();

		JMenu filem = new JMenu("File");
		JMenuItem newm = new JMenuItem(new AbstractAction("New") {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					new Window();
				}
			});
		newm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
												   KeyEvent.CTRL_DOWN_MASK));
		
		JMenuItem openm = new JMenuItem(new AbstractAction("Open") {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					fileManager.openFile(window);
				}
			});
		openm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
													KeyEvent.CTRL_DOWN_MASK));
		
		JMenuItem savem = new JMenuItem(new AbstractAction("Save") {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					fileManager.quickSave(window, textArea.getText());
				}
			});
		savem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
													KeyEvent.CTRL_DOWN_MASK));

		JMenuItem saveasm = new JMenuItem(new AbstractAction("Save As...") {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String title = fileManager.saveFile(window, textArea.getText());
					if(title != null) {
						window.setTitle(title);
					}
				}
			});
		saveasm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
													  KeyEvent.CTRL_DOWN_MASK
													  + KeyEvent.SHIFT_DOWN_MASK));

		filem.add(newm);
		filem.add(openm);
		filem.add(savem);
		filem.add(saveasm);
		menubar.add(filem);

		JMenu editm = new JMenu("Edit");
		JMenuItem undom = new JMenuItem(new AbstractAction("Undo") {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					textArea.undo();
				}
			});
		undom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
													KeyEvent.CTRL_DOWN_MASK));
		
		JMenuItem redom = new JMenuItem(new AbstractAction("Redo") {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					textArea.redo();
				}
			});
		redom.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,
													KeyEvent.CTRL_DOWN_MASK));
		
		JMenuItem cutm = new JMenuItem(new AbstractAction("Cut") {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					ClipboardManager.setClipboard(textArea.getSelectedText());
					textArea.replaceRange("", textArea.getSelectionStart(),
										  textArea.getSelectionEnd());
				}
			});
		cutm.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
												   KeyEvent.CTRL_DOWN_MASK));

		JMenuItem copym = new JMenuItem(new AbstractAction("Copy") {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					ClipboardManager.setClipboard(textArea.getSelectedText());
				}
			});
		copym.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
													KeyEvent.CTRL_DOWN_MASK));

		JMenuItem pastem = new JMenuItem(new AbstractAction("Paste") {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					textArea.replaceRange(ClipboardManager.getClipboard(),
										  textArea.getSelectionStart(),
										  textArea.getSelectionEnd());
				}
			});
		pastem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
													 KeyEvent.CTRL_DOWN_MASK));

		JMenuItem deletem = new JMenuItem(new AbstractAction("Delete") {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(textArea.getSelectionStart() != textArea.getSelectionEnd()) {
						textArea.replaceRange("", textArea.getSelectionStart(),
											  textArea.getSelectionEnd());
					} else {
						textArea.replaceRange("", textArea.getCaretPosition()-1,
											  textArea.getCaretPosition());
					}
				}
			});
		deletem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
													  KeyEvent.CTRL_DOWN_MASK));

		editm.add(undom);
		editm.add(redom);
		editm.add(cutm);
		editm.add(copym);
		editm.add(pastem);
		editm.add(deletem);
		menubar.add(editm);

		window.setJMenuBar(menubar);
	}
}
