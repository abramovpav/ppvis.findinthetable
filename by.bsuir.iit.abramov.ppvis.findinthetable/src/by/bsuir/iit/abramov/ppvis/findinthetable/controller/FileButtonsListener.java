package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import by.bsuir.iit.abramov.ppvis.findinthetable.util.IllegalParametrs;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.MenuItem;

public class FileButtonsListener implements ActionListener {

	private final MenuItem				menuItem;
	private final ButtonActionListener	listener;

	public FileButtonsListener(final MenuItem menuItem) throws IllegalParametrs {

		this.menuItem = menuItem;

		switch (menuItem) {
			case OPEN:
				listener = new OpenButtonActionListener();
			break;
			case SAVE:
				listener = new SaveButtonActionListener();
			break;
			case CLOSE:
				listener = new CloseButtonActionListener();
			break;
			case EXIT:
				listener = new ExitButtonActionListener();
			break;

			default:
				throw new IllegalParametrs();
		}
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		listener.action(e);
	}
}
