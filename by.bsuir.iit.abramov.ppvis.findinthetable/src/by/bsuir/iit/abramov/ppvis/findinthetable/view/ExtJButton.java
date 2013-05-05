package by.bsuir.iit.abramov.ppvis.findinthetable.view;

import javax.swing.JButton;
import javax.swing.JPanel;

import by.bsuir.iit.abramov.ppvis.findinthetable.util.ActionButton;

public class ExtJButton extends JButton implements ActionButton {
	private final JPanel	container;
	private final String	caption;

	public ExtJButton(final String caption, final JPanel container) {

		super(caption);
		this.container = container;
		this.caption = caption;
		setFocusable(false);
	}

	public String getCaption() {

		return caption;
	}

	@Override
	public final JPanel getContainer() {

		return container;
	}
}
