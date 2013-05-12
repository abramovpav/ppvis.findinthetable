package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Model;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.Student;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Desktop;

public class DesktopDecrementButtonActionListener implements ActionListener {
	private final Desktop	desktop;
	private final Model		model;

	public DesktopDecrementButtonActionListener(final Model model, final Desktop desktop) {

		this.model = model;
		this.desktop = desktop;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		model.setViewSize(model.getViewSize() - 1);
		final List<Student> pageOfStudents = model.getCurrPageOfStudent();
		desktop.setStudents(desktop.getTableModel(), pageOfStudents);
	}

}
