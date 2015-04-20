package ie.tcd.gourleys.renderer;

import ie.tcd.gourleys.ui.TextEditorWindow;
import ie.tcd.gourleys.ui.TextWindowEvent;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileManager {

	public static TextContainer buildContainerFromPath(Frame window, int purpose) {
		FileDialog dialog = new FileDialog(window, "Open File", purpose);
		dialog.setVisible(true);
		String title = dialog.getFile();
		String path = dialog.getDirectory() + title;
		System.out.println("File selected.");
		String text = open(path);
		return new TextContainer(title, text);
	}
	
	public static TextContainer buildContainer() {
		return new TextContainer();
	}

	public static String open(String path) {
		String text = "";
		try {
			File file = new File(path);
			Scanner fileReader = new Scanner(file);
			int lines = 0;
			while (fileReader.hasNextLine()) {
				text += fileReader.nextLine() + "\n";
				lines++;
				System.out.println("Current lines read: " + lines);
			}
			fileReader.close();
			if (text == "") {
				System.out.println("Invalid file.");
			}
		} catch (FileNotFoundException e1) {
			System.out.println("File does not exist.");
		}
		return text;
	}

	public static void main(String[] args) {
		TextEditorWindow w = new TextEditorWindow();
		TextWindowEvent l = new TextWindowEvent();
		w.addWindowListener(l);
		w.setVisible(true);
	}

}
