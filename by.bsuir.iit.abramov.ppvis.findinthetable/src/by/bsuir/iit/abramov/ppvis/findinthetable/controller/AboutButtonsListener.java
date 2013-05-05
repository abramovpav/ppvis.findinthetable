package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import by.bsuir.iit.abramov.ppvis.findinthetable.util.MenuContent;

public class AboutButtonsListener implements ActionListener {

	private final String				name;
	private final ButtonActionListener	listener;

	public AboutButtonsListener(final String name) {

		this.name = name;

		switch (MenuContent.File.getItemIndex(name)) {
			case 0:
				listener = new AboutButtonActionListener();
			break;
			default:
				System.out.println(name);
				listener = new AboutButtonActionListener();
		}
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		listener.action(e);
	}
}
