package ie.tcd.gourleys.ui;

import ie.tcd.gourleys.renderer.TextContainer;
import ie.tcd.gourleys.renderer.TextTransfer;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class TextEditorWindow extends Frame implements ActionListener {

	private BorderLayout layout;
	private MenuBar menubar;
	private TextArea textarea;
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

		this.setTitle(contents.getTitle());
		createMenuBar();
		textarea = new TextArea(contents.getContent());	

		layout = new BorderLayout(LAY_GAP, LAY_GAP);
		this.setLayout(layout);
		this.add(textarea, BorderLayout.CENTER);
	}

	public TextEditorWindow(int x, int y) {		
		this(x, y, new TextContainer());
	}

	public TextEditorWindow() {
		this(0, 0);
	}

	/* ====================================================================
	 * 		Create UI
	 * ====================================================================	
	 */

	private void createMenuBar() {
		menubar = new MenuBar();
		this.setMenuBar(menubar);

		Menu file = new Menu("File");
		MenuItem[] fileitem = {new MenuItem("New"), new MenuItem("Open..."), new MenuItem("Save"),
				new MenuItem("Save As...")};
		for (int i = 0; i < fileitem.length; i++) {
			file.add(fileitem[i]);
			fileitem[i].addActionListener(this);
		}
		menubar.add(file);

		Menu edit = new Menu("Edit");
		MenuItem[] edititem = {new MenuItem("Undo"), new MenuItem("Redo"), new MenuItem("Cut"),
				new MenuItem("Copy"), new MenuItem("Paste"), new MenuItem("Delete"), new MenuItem("Select All")};
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

	@Override
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
			break;
		case "save as...":
			System.out.println("Save current file as...");
			break;
		case "undo":
			System.out.println("Undo change.");
			break;
		case "redo":
			System.out.println("Redo changes.");
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
			textarea.replaceRange("", textarea.getSelectionStart(), textarea.getSelectionEnd());
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


	/* ====================================================================
	 * 		main()
	 * ====================================================================	
	 */

	public static void main(String[] args) {
		TextEditorWindow w = new TextEditorWindow();
		TextWindowEvent l = new TextWindowEvent();
		w.addWindowListener(l);
		w.setVisible(true);
	}

}
