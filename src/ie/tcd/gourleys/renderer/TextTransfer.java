package ie.tcd.gourleys.renderer;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class TextTransfer implements ClipboardOwner {
	
	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {	}
	
	public void setClipboard(String text) {
		StringSelection clipText = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(clipText, this);
	}
	
	public String getClipboard() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable clipContents = clipboard.getContents(this);
		if (clipContents != null && clipContents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			try {
				return (String) clipContents.getTransferData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException | IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

}
