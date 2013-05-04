package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Student;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.ADialog;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.ExtJButton;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.ToolPanel;

public class AddButtonActionListener implements ActionListener, ButtonActionListener {

	@Override
	public void action(final ActionEvent e) {

		actionPerformed(e);

	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		final ExtJButton button = (ExtJButton) e.getSource();
		System.out.println("AddButtonActionListener");
		final ADialog dialog = new ADialog();
		dialog.setModal(true);
		dialog.setVisible(true);
		final Student student = dialog.getStudent();
		((ToolPanel) button.getContainer()).addStudent(student);

	}

}
