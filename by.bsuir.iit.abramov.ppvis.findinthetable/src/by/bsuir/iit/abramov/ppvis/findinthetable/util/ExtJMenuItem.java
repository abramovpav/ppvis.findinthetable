package by.bsuir.iit.abramov.ppvis.findinthetable.util;

import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class ExtJMenuItem extends JMenuItem implements ActionButton {
	private final JPanel	container;

	public ExtJMenuItem(final String caption, final JPanel container) {

		super(caption);
		this.container = container;
	}

	@Override
	public JPanel getContainer() {

		return container;
	}
}
