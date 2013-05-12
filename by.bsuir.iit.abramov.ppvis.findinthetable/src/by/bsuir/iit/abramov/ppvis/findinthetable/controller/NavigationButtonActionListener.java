package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Model;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.Student;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.table.AttributiveCellTableModel;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Desktop;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.FindDialog;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Window;

public class NavigationButtonActionListener implements ActionListener {
	
	private ActionListener listener;
	
	public NavigationButtonActionListener(final Model model, final Desktop desktop) {
		listener = new DesktopNavigationButtonActionListener(model, desktop);
	}
	
	public NavigationButtonActionListener(final Model model, final FindDialog findDialog) {
		listener = new DialogNavigationButtonActionListener(model, findDialog);
	}
	
	@Override
	public void actionPerformed(final ActionEvent e) {

		listener.actionPerformed(e);
	}
}