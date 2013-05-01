package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import by.bsuir.iit.abramov.ppvis.findinthetable.util.MenuContent;

public class EditButtonsListener implements ActionListener {

	private final String				name;
	private final ButtonActionListener	listener;

	public EditButtonsListener(final String name) {

		this.name = name;

		switch (MenuContent.Edit.getItemIndex(name)) {
			case 0:
				listener = new AddButtonActionListener();
			break;
			case 1:
				listener = new DeleteButtonActionListener();
			break;
			case 2:
				listener = new FindButtonActionListener();
			break;
			default:
				listener = new FindButtonActionListener();
		}
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		listener.action(e);
	}
}
