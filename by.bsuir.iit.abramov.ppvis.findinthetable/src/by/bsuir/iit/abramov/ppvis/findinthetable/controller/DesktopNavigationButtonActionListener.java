package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Model;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.Student;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Desktop;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Window;

public class DesktopNavigationButtonActionListener implements ActionListener {
	private final Model		model;
	private final Desktop	desktop;

	public DesktopNavigationButtonActionListener(final Model model, final Desktop desktop) {

		this.model = model;
		this.desktop = desktop;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		final JButton button = (JButton) e.getSource();
		List<Student> pageOfStudents = null;
		if (Window.geti18nString(Desktop.BUTTON_NEXT).equalsIgnoreCase(button.getText())) {
			pageOfStudents = model.getNextPageOfStudents();
		} else if (Window.geti18nString(Desktop.BUTTON_PREV).equalsIgnoreCase(
				button.getText())) {
			pageOfStudents = model.getPrevPageOfStudents();
		}
		desktop.setStudents(desktop.getTableModel(), pageOfStudents);

	}

}
