package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import by.bsuir.iit.abramov.ppvis.findinthetable.view.ADialog;

public class AddButtonActionListener implements ActionListener, ButtonActionListener {

	@Override
	public void action(final ActionEvent e) {

		actionPerformed(e);

	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		// TODO Auto-generated method stub
		System.out.println("AddButtonActionListener");
		final ADialog dialog = new ADialog();
		dialog.setModal(true);
		dialog.setVisible(true);

	}

}
