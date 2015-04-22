package ie.tcd.gourleys;

import java.util.ArrayList;

import ie.tcd.gourleys.system.*;
import ie.tcd.gourleys.ui.*;
import ie.tcd.gourleys.ui.listener.*;

public class Main {
	static ArrayList<TextEditorWindow> windowList;
	
	public static void addWindow(ArrayList<TextEditorWindow> windowList, TextContainer contents) {
		int offset = windowList.size() * 50;
		if (contents != null) {
			TextEditorWindow window = new TextEditorWindow(offset, offset, contents);
			window.addWindowListener(new TextWindowListener());
			window.setVisible(true);
			windowList.add(window);
		}
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
		Main.addWindow(windowList, new TextContainer());

		while (windowList.size() != 0) {
			for (int i = 0; i < windowList.size(); i++) {
				if (windowList.get(i).isNewWindow()) {
					windowList.get(i).setNewWindow(false);
					Main.addWindow(windowList, new TextContainer());
				}
				if (windowList.get(i).isOpenWindow()) {
					windowList.get(i).setOpenWindow(false);
					Main.addWindow(windowList, windowList.get(i).getOpen());
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
