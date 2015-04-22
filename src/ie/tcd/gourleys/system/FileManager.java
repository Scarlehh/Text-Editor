package ie.tcd.gourleys.system;

import ie.tcd.gourleys.Main;
import ie.tcd.gourleys.ui.TextEditorWindow;

import java.awt.FileDialog;
import java.awt.TextArea;
import java.io.*;

public class FileManager {
	
	public static final String DEFAULT_PATH = "C:/Users/Scarlett/Desktop/";
	public static final String POSTFIX = ".scrub";

	/* ====================================================================
	 * 		Open + Save files
	 * ====================================================================	
	 */
	
	public static TextContainer open(TextEditorWindow window) {
		FileDialog dialog = new FileDialog(window, "Open File", FileDialog.LOAD);
		dialog.setVisible(true);
		String title = dialog.getFile();
		if (title != null) {
			String path = dialog.getDirectory() + title;
			System.out.println("File selected: " + path);
			String text = loadFile(path);
			System.out.println("File loaded successfully.");
			return new TextContainer(title, text);
		}
		System.out.println("No file selected.");
		return null;
	}
	
	public static void save(TextEditorWindow window) {
		FileDialog dialog = new FileDialog(window, "Save File", FileDialog.SAVE);
		dialog.setVisible(true);
		String title = dialog.getFile();
		if (title != null) {
			if (!title.endsWith(POSTFIX)) {
				title += POSTFIX;
			}
			window.setTitle(title);
			String path = dialog.getDirectory() + title;
			System.out.println("Path selected: " + path);
			createFile(window.getText(), path);
			System.out.println("Successfully created file.");
		}
	}
	
	public static void quicksave(TextEditorWindow window) {
		String title = window.getTitle();
		if (!title.endsWith(POSTFIX)) {
			title += POSTFIX;
			window.setTitle(title);
		}
		String path = DEFAULT_PATH + title;
		System.out.println("Path selected: " + path);
		createFile(window.getText(), path);
		System.out.println("Successfully created file.");
	}

	/* ====================================================================
	 * 		get and set files
	 * ====================================================================	
	 */
	
	public static String loadFile(String path) {
		String text = "";
		try {
			File file = new File(path);
			FileReader filereader = new FileReader(file);
			BufferedReader bufferedreader = new BufferedReader(filereader);
			while (bufferedreader.ready()) {
				text += bufferedreader.readLine() + "\n";
			}
			bufferedreader.close();
			if (text == "") {
				System.out.println("Invalid file.");
			}
		} catch (FileNotFoundException e1) {
			System.out.println("File does not exist.");
		} catch (IOException e) {
			System.out.println("Input Error.");
		}
		return text;
	}
	
	public static File createFile(TextArea text, String path) {
		File file = new File(path);
		try {
			FileWriter filewriter = new FileWriter(file);
			BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
			bufferedwriter.write(text.getText());
			bufferedwriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}


	/* ====================================================================
	 * 		main()
	 * ====================================================================	
	 */
	
	public static void main(String[] args) {
		Main.main(args);
	}

}
