package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Model;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Desktop;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.FindDialog;

public class DialogViewSizeButtonListener implements ActionListener {
	private final ActionListener	listener;

	public DialogViewSizeButtonListener(final Model model, final FindDialog findDialog,
			final String caption) {

		if (Desktop.DECREMENT.equals(caption)) {
			listener = new DialogDecrementButtonActionListener(model, findDialog);
		} else {
			listener = new DialogIncrementButtonActionListener(model, findDialog);
		}
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		listener.actionPerformed(e);
	}

}
