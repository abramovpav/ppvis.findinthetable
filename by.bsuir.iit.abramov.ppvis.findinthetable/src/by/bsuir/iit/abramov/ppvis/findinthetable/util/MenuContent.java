package by.bsuir.iit.abramov.ppvis.findinthetable.util;

public enum MenuContent {
	File("File", "Open...", "Save as...", "Close", "Exit"), Edit("Edit", "Add...",
			"Delete...", "Find..."), ABOUT("About", "Author");

	private String		section;
	private String[]	items;

	private MenuContent(final String section, final String... items) {

		this.section = section;
		this.items = items;
	}

	public int getItemIndex(final String item) {

		for (int i = 0; i < items.length; ++i) {
			if (items[i].equalsIgnoreCase(item)) {
				return i;
			}
		}
		return -1;
	}

	public String[] getItems() {

		return items;
	}

	public String getSection() {

		return section;
	}
}
