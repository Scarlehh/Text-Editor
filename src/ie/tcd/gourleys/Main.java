package ie.tcd.gourleys;

import java.awt.FileDialog;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import ie.tcd.gourleys.renderer.*;
import ie.tcd.gourleys.ui.*;

public class Main {
	static ArrayList<TextEditorWindow> windowList;
	
	public static void addWindow(ArrayList<TextEditorWindow> windowList, TextContainer contents) {
		int offset = windowList.size() * 50;
		if (contents == null) {
			contents = new TextContainer();
		}
		TextEditorWindow window = new TextEditorWindow(offset, offset, contents);
		TextWindowEvent listener = new TextWindowEvent();
		window.addWindowListener(listener);
		window.setVisible(true);
		windowList.add(window);
	}
	
	public static void addWindow(ArrayList<TextEditorWindow> windowList) {
		addWindow(windowList, null);
	}
	
	/* ====================================================================
	 * 		main()
	 * ====================================================================	
	 */
	
	public static void main(String[] args) {
		windowList = new ArrayList<TextEditorWindow>();
		Main.addWindow(windowList);
		
		while (windowList.size() != 0) {
			for (int i = 0; i < windowList.size(); i++) {
				if (windowList.get(i).isNewWindow()) {
					windowList.get(i).setNewWindow(false);
					Main.addWindow(windowList);
				}
				if (windowList.get(i).isOpenWindow()) {
					windowList.get(i).setOpenWindow(false);
					TextContainer contents = FileManager.buildContainerFromPath(windowList.get(i), FileDialog.LOAD);
					Main.addWindow(windowList, contents);
				}
				
				// Removes window from array list if disposed.
				if (!windowList.get(i).isDisplayable()) {
					windowList.remove(i);
				}
			}
		}
		System.out.println("All windows are closed.");
	}

}
