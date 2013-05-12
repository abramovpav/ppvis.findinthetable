package by.bsuir.iit.abramov.ppvis.findinthetable.util;

import java.util.ArrayList;
import java.util.List;

public enum MenuContent {
	FILE("file", MenuItem.OPEN, MenuItem.SAVE, MenuItem.CLOSE, MenuItem.EXIT), EDIT(
			"edit", MenuItem.ADD, MenuItem.DELETE, MenuItem.FIND,
			MenuItem.LANGUAGE_ENGLISH, MenuItem.LANGUAGE_RUSSIAN), ABOUT("about",
			MenuItem.AUTHOR);

	private String			section;
	private List<MenuItem>	items;

	private MenuContent(final String section, final MenuItem... items) {

		this.section = section;
		this.items = new ArrayList<MenuItem>();
		for (final MenuItem str : items) {
			this.items.add(str);
		}
	}

	public List<MenuItem> getItems() {

		return items;
	}

	public String getSection() {

		return section;
	}
}
