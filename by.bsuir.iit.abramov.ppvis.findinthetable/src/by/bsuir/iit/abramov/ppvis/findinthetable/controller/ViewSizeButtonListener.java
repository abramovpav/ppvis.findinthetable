package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Model;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Desktop;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.FindDialog;

public class ViewSizeButtonListener implements ActionListener {
		
		
		private ActionListener listener;
		public ViewSizeButtonListener(final Model model, final Desktop desktop) {
			
			listener = new DesktopViewSizeButtonListener(model, desktop);
		}
		public ViewSizeButtonListener(final Model model, final FindDialog findDialog) {
			listener = new DialogViewSizeButtonListener(model, findDialog);
		}
		@Override
		public void actionPerformed(final ActionEvent e) {

			listener.actionPerformed(e);
		}
	}