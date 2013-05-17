package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Model;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Desktop;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.FindDialog;

public class DialogViewSizeButtonListener implements ActionListener {
	private int					modifier;
	private final FindDialog	findDialog;
	private final Model			model;

	public DialogViewSizeButtonListener(final Model model, final FindDialog findDialog,
			final String caption) {

		this.model = model;
		this.findDialog = findDialog;
		if (Desktop.INCREMENT.equals(caption)) {
			modifier = 1;
		} else {
			modifier = -1;
		}
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		model.setViewSize(model.getViewSize() + modifier);
		findDialog.tableUpdate();
	}

}
