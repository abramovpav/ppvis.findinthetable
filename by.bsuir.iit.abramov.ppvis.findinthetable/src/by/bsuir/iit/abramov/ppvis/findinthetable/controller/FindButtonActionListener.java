package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import by.bsuir.iit.abramov.ppvis.findinthetable.view.ContentPane;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.ExtJButton;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.FindDialog;

public class FindButtonActionListener implements ActionListener, ButtonActionListener {

	@Override
	public void action(final ActionEvent e) {

		actionPerformed(e);

	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		System.out.println("FindButtonActionListener");
		final ExtJButton button = (ExtJButton) e.getSource();
		System.out.println("AddButtonActionListener");
		final FindDialog dialog = new FindDialog((ContentPane) button.getContainer());
		dialog.setModal(true);
		dialog.setVisible(true);
		// final Student student = dialog.getStudent();
		// ((ToolPanel) button.getContainer()).addStudent(student);
	}

}
