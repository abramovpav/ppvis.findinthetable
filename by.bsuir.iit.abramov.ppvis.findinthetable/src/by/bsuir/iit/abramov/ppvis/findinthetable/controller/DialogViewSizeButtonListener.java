package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Model;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.Student;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Desktop;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.FindDialog;

public class DialogViewSizeButtonListener implements ActionListener {
	private final FindDialog findDialog;
	private final Model model;
	
	public DialogViewSizeButtonListener(final Model model, final FindDialog findDialog) {
		this.model = model;
		this.findDialog = findDialog;
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		final JButton button = (JButton) e.getSource();

		if (Desktop.DECREMENT.equalsIgnoreCase(button.getText())) {
			model.setViewSize(model.getViewSize() - 1);
		} else if (Desktop.INCREMENT.equalsIgnoreCase(button.getText())) {
			model.setViewSize(model.getViewSize() + 1);
		}

		//final List<Student> pageOfStudents = model.getCurrPageOfStudent();
		
		findDialog.tableUpdate();

	}

}
