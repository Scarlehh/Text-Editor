package ie.tcd.gourleys.renderer;

public class TextContainer {

	private String title;
	private String content;
	
	public TextContainer(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
	public TextContainer() {
		this("Untitled Notepad", null);
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContent() {
		return content;
	}
}
