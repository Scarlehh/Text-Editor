package ie.tcd.gourleys.system;

import ie.tcd.gourleys.ui.Window;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

public class FileManager {
	String name;
	String dir;

	public FileManager() {
		this(null, null);
	}
	
	public FileManager(String name, String dir) {
		this.name = name;
		this.dir = dir;
	}
	
	public void openFile(JFrame window) {
		JFileChooser chooser = new JFileChooser();
		if(dir != null) {
			chooser.setCurrentDirectory(new File(dir));
		}
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if(chooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line = null;
				String in = "";
				try {
					while((line = reader.readLine()) != null) {
						in += line + "\n";
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
				new Window(chooser.getName(file), in, chooser.getCurrentDirectory().getPath());
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public String saveFile(JFrame window, String file) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if(chooser.showSaveDialog(window) == JFileChooser.APPROVE_OPTION) {
			try {
				FileWriter writer = new FileWriter(chooser.getSelectedFile());
				writer.write(file);
				writer.close();
				name = chooser.getName(chooser.getSelectedFile());
				dir = chooser.getCurrentDirectory().getPath();
				return name;
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public String quickSave(JFrame window, String file) {
		if(name == null || dir == null) {
			return saveFile(window, file);
		}
		try {
			FileWriter writer = new FileWriter(new File(dir+"/"+name));
			writer.write(file);
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return name;
	}
}
