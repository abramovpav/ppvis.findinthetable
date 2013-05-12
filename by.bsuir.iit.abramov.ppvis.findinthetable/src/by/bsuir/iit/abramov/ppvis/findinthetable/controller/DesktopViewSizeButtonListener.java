package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Model;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Desktop;

public class DesktopViewSizeButtonListener implements ActionListener {
	private final Model				model;
	private final Desktop			desktop;
	private final ActionListener	listener;

	public DesktopViewSizeButtonListener(final Model model, final Desktop desktop,
			final String caption) {

		this.model = model;
		this.desktop = desktop;
		if (Desktop.INCREMENT.equals(caption)) {
			listener = new DesktopIncrementButtonActionListener(model, desktop);
		} else {
			listener = new DesktopDecrementButtonActionListener(model, desktop);
		}
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		listener.actionPerformed(e);
	}

}
