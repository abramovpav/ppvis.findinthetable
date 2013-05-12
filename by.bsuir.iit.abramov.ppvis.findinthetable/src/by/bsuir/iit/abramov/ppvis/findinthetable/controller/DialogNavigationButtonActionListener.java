package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Model;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Desktop;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.FindDialog;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Window;

public class DialogNavigationButtonActionListener implements ActionListener {
	private final Model			model;
	private final FindDialog	findDialog;

	public DialogNavigationButtonActionListener(final Model model,
			final FindDialog findDialog) {

		this.model = model;
		this.findDialog = findDialog;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		final JButton button = (JButton) e.getSource();
		if (Window.geti18nString(Desktop.BUTTON_NEXT).equalsIgnoreCase(button.getText())) {
			findDialog.showNextPageOfStudents();
		} else if (Window.geti18nString(Desktop.BUTTON_PREV).equalsIgnoreCase(
				button.getText())) {
			findDialog.showPrevPageOfStudents();
		}
	}

}
