package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;

import by.bsuir.iit.abramov.ppvis.findinthetable.util.ActionButton;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.ContentPane;

public class EnglishLanguageButtonActionListener implements ButtonActionListener {

	@Override
	public void action(final ActionEvent e) {

		final ActionButton button = (ActionButton) e.getSource();
		final ContentPane contentPane = (ContentPane) button.getContainer();
		contentPane.setEnLocale();
	}

}
