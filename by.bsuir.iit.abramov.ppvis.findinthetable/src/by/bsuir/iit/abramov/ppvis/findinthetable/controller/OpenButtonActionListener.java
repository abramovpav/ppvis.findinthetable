package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import by.bsuir.iit.abramov.ppvis.findinthetable.util.ActionButton;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.XMLFilter;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.ContentPane;

public class OpenButtonActionListener implements ActionListener, ButtonActionListener {

	@Override
	public void action(final ActionEvent e) {

		actionPerformed(e);

	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		final ActionButton button = (ActionButton) e.getSource();
		final JFileChooser fn = new JFileChooser();
		fn.setFileFilter(new XMLFilter());
		final int ret = fn.showOpenDialog(null);
		if (ret == JFileChooser.APPROVE_OPTION) {
			final File file = fn.getSelectedFile();
			final ContentPane cont = ((ContentPane) button.getContainer());
			cont.openXML(file);

		}

	}

}
