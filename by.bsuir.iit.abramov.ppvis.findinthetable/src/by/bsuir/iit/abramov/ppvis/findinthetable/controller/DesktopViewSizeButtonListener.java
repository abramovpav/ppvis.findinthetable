package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Model;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.Student;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Desktop;

public class DesktopViewSizeButtonListener implements ActionListener {
	private final Model		model;
	private final Desktop	desktop;
	private int				modifier;

	public DesktopViewSizeButtonListener(final Model model, final Desktop desktop,
			final String caption) {

		this.model = model;
		this.desktop = desktop;
		if (Desktop.INCREMENT.equals(caption)) {
			modifier = 1;
		} else {
			modifier = -1;
		}
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		// listener.actionPerformed(e);
		model.setViewSize(model.getViewSize() + modifier);
		final List<Student> pageOfStudents = model.getCurrPageOfStudent();
		desktop.setStudents(desktop.getTableModel(), pageOfStudents);
	}

}
