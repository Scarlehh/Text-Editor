package ie.tcd.gourleys.ui;

import ie.tcd.gourleys.Main;
import ie.tcd.gourleys.system.*;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class TextEditorWindow extends Frame implements ActionListener {

	private BorderLayout layout;
	private MenuBar menubar;
	private TextEditorArea textarea;
	private TextTransfer clipboard = new TextTransfer();
	private static final int FRAMEX = 500;
	private static final int FRAMEY = 500;
	private static final int LAY_GAP = 3;

	private boolean newWindow = false;
	private boolean openWindow = false;


	/* ====================================================================
	 * 		Constructors
	 * ====================================================================	
	 */

	public TextEditorWindow(int x, int y, TextContainer contents) {
		this.setSize(FRAMEX,FRAMEY);
		this.setLocation(x, y);

		createMenuBar();
		textarea = new TextEditorArea(contents.getContent());
		layout = new BorderLayout(LAY_GAP, LAY_GAP);
		this.setLayout(layout);
		this.setTitle(contents.getTitle());
		this.add(textarea, BorderLayout.CENTER);
	}

	public TextEditorWindow(int x, int y) {		
		this(x, y, new TextContainer());
	}

	public TextEditorWindow() {
		this(0, 0);
	}

	/* ====================================================================
	 * 		Create menu bar
	 * ====================================================================	
	 */

	private void createMenuBar() {
		menubar = new MenuBar();
		this.setMenuBar(menubar);

		// File menu
		Menu file = new Menu("File");
		MenuItem newFile = new MenuItem("New");
		newFile.setShortcut(new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('n')));
		MenuItem open = new MenuItem("Open...");
		open.setShortcut(new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('o')));
		MenuItem save = new MenuItem("Save");
		save.setShortcut(new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('s')));
		MenuItem saveAs = new MenuItem("Save As...");
		saveAs.setShortcut(new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('s'), true));

		MenuItem[] fileitem = {newFile, open, save, saveAs};
		for (int i = 0; i < fileitem.length; i++) {
			file.add(fileitem[i]);
			fileitem[i].addActionListener(this);
		}
		menubar.add(file);

		// Edit menu
		Menu edit = new Menu("Edit");
		MenuItem undo = new MenuItem("Undo");
		undo.setShortcut(new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('z')));
		MenuItem redo = new MenuItem("Redo");
		redo.setShortcut(new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('y')));
		MenuItem cut = new MenuItem("Cut");
		cut.setShortcut(new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('x')));
		MenuItem copy = new MenuItem("Copy");
		copy.setShortcut(new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('c')));
		MenuItem paste = new MenuItem("Paste");
		paste.setShortcut(new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('v')));
		MenuItem delete = new MenuItem("Delete");
		delete.setShortcut(new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('d')));
		MenuItem select = new MenuItem("Select All");
		select.setShortcut(new MenuShortcut(KeyEvent.getExtendedKeyCodeForChar('a')));
		MenuItem[] edititem = {undo, redo, cut,	copy, paste, delete, select};
		for (int i = 0; i < edititem.length; i++) {
			edit.add(edititem[i]);
			edititem[i].addActionListener(this);
		}
		menubar.add(edit);

		Menu format = new Menu("Format");
		MenuItem[] formatitem = {new CheckboxMenuItem("Word Wrap"), new MenuItem("Font...")};
		for (int i = 0; i < formatitem.length; i++) {
			format.add(formatitem[i]);
			formatitem[i].addActionListener(this);
		}
		menubar.add(format);
	}

	/* ====================================================================
	 * 		Action Listener methods
	 * ====================================================================	
	 */

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		switch(action.toLowerCase()) {
		case "new":
			System.out.println("New file.");
			newWindow = true;
			break;
		case "open...":
			System.out.println("Open existing file.");
			openWindow = true;
			break;
		case "save":
			System.out.println("Save current file.");
			FileManager.quicksave(this);
			break;
		case "save as...":
			System.out.println("Save current file as...");
			FileManager.save(this);
			break;
		case "undo":
			System.out.println("Undo change.");
			textarea.undo();
			break;
		case "redo":
			System.out.println("Redo changes.");
			textarea.redo();
			break;
		case "cut":
			System.out.println("Cut item.");
			clipboard.setClipboard(textarea.getSelectedText());
			textarea.replaceRange("", textarea.getSelectionStart(), textarea.getSelectionEnd());
			break;
		case "copy":
			System.out.println("Copy item.");
			clipboard.setClipboard(textarea.getSelectedText());
			break;
		case "paste":
			System.out.println("Paste item.");
			textarea.replaceRange(clipboard.getClipboard(), textarea.getSelectionStart(), textarea.getSelectionEnd());
			break;
		case "delete":
			System.out.println("Delete item.");
			if (textarea.getSelectionStart() != textarea.getSelectionEnd()) {
				textarea.replaceRange("", textarea.getSelectionStart(), textarea.getSelectionEnd());
			} else {
				textarea.replaceRange("", textarea.getCaretPosition()-1, textarea.getCaretPosition());
			}
			break;
		case "select all":
			System.out.println("All Selected");
			textarea.selectAll();
			break;
		}
	}
	
	/* ====================================================================
	 * 		is()
	 * ====================================================================	
	 */

	public boolean isNewWindow() {
		return newWindow;
	}

	public boolean isOpenWindow() {
		return openWindow;
	}

	/* ====================================================================
	 * 		getters + setters
	 * ====================================================================	
	 */

	public void setNewWindow(boolean state) {
		newWindow = state;
	}

	public void setOpenWindow(boolean state) {
		openWindow = state;
	}
	
	public TextArea getText() {
		return textarea;
	}
	
	public TextContainer getOpen() {
		return FileManager.open(this);
	}


	/* ====================================================================
	 * 		main()
	 * ====================================================================	
	 */

	public static void main(String[] args) {
		Main.main(args);
	}

}
