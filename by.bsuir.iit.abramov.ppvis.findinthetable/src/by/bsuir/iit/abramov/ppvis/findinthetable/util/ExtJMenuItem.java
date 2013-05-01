package by.bsuir.iit.abramov.ppvis.findinthetable.util;

import javax.swing.JFrame;
import javax.swing.JMenuItem;

public class ExtJMenuItem extends JMenuItem {
	private final JFrame	container;

	public ExtJMenuItem(final String caption, final JFrame container) {

		super(caption);
		this.container = container;
	}

	public JFrame getContainer() {

		return container;
	}
}
