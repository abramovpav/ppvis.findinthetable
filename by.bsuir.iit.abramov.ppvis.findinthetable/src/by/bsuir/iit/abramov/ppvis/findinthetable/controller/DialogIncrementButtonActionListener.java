package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Model;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.FindDialog;

public class DialogIncrementButtonActionListener implements ActionListener {
	private final FindDialog	findDialog;
	private final Model			model;

	public DialogIncrementButtonActionListener(final Model model,
			final FindDialog findDialog) {

		this.model = model;
		this.findDialog = findDialog;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		model.setViewSize(model.getViewSize() + 1);
		findDialog.tableUpdate();
	}

}
