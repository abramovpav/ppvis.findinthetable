package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Model;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Desktop;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.FindDialog;

public class ViewSizeButtonListener implements ActionListener {

	private final ActionListener	listener;

	public ViewSizeButtonListener(final Model model, final Desktop desktop,
			final String caption) {

		listener = new DesktopViewSizeButtonListener(model, desktop, caption);
	}

	public ViewSizeButtonListener(final Model model, final FindDialog findDialog,
			final String caption) {

		listener = new DialogViewSizeButtonListener(model, findDialog, caption);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		listener.actionPerformed(e);
	}
}
