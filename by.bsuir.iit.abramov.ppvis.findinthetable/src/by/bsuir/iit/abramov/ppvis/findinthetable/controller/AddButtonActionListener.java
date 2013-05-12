package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Student;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.ActionButton;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.ADialog;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.ContentPane;

public class AddButtonActionListener implements ActionListener, ButtonActionListener {

	@Override
	public void action(final ActionEvent e) {

		actionPerformed(e);

	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		final ActionButton button = (ActionButton) e.getSource();
		final ADialog dialog = new ADialog();
		dialog.setModal(true);
		dialog.setVisible(true);
		final Student student = dialog.getStudent();
		((ContentPane) button.getContainer()).addStudent(student);

	}

}
