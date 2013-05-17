package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import by.bsuir.iit.abramov.ppvis.findinthetable.view.FindDialog;

public class FindDialogButtonActionListener implements ActionListener {
	private final FindDialog	dialog;

	public FindDialogButtonActionListener(final FindDialog dialog) {

		this.dialog = dialog;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		if (FindDialog.BUTTON_OK.equals(e.getActionCommand())) {
			// search
			final Integer checkFields = dialog.checkFields();
			if (checkFields != null) {
				if (FindDialog.BUTTON_OK.equals(FindDialog.BUTTON_SEARCH)) {
					dialog.search(checkFields);
				} else {
					dialog.delete(checkFields);
				}
			}
		} else if (FindDialog.BUTTON_CANCEL.equals(e.getActionCommand())) {
			dialog.setVisible(false);
			dialog.dispose();
		}

	}

}
