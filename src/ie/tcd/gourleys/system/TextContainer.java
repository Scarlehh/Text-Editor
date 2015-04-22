package ie.tcd.gourleys.system;

public class TextContainer {

	public static final String DEFAULT_TITLE = "Untitled Notepad";
	private String title;
	private String content;
	
	public TextContainer(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
	public TextContainer() {
		this(DEFAULT_TITLE, null);
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContent() {
		return content;
	}
}
