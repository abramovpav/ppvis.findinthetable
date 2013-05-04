package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import by.bsuir.iit.abramov.ppvis.findinthetable.util.MenuContent;

public class FileButtonsListener implements ActionListener {

	private final String				name;
	private final ButtonActionListener	listener;

	public FileButtonsListener(final String name) {

		this.name = name;

		switch (MenuContent.File.getItemIndex(name)) {/*
													 * case 0: listener = new
													 * NewButtonActionListener
													 * (); break;
													 */
			case 1:
				listener = new OpenButtonActionListener();
			break;
			case 2:
				listener = new SaveButtonActionListener();
			break;/*
				 * case 3: listener = new CloseButtonActionListener(); break;
				 * case 4: listener = new ExitButtonActionListener(); break;
				 */
			default:
				System.out.println(name);
				listener = new FindButtonActionListener();
		}
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		listener.action(e);
	}
}
