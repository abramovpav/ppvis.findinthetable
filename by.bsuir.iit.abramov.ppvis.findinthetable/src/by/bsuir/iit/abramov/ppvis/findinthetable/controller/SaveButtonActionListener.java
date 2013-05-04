package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import by.bsuir.iit.abramov.ppvis.findinthetable.util.ExtJMenuItem;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.XMLFilter;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Window;

public class SaveButtonActionListener implements ActionListener, ButtonActionListener {

	@Override
	public void action(final ActionEvent e) {

		actionPerformed(e);

	}

	@Override
	public void actionPerformed(final ActionEvent e) {

		final ExtJMenuItem item = (ExtJMenuItem) e.getSource();
		final JFileChooser fn = new JFileChooser();
		fn.setFileFilter(new XMLFilter());
		final int ret = fn.showSaveDialog(null);
		if (ret == JFileChooser.APPROVE_OPTION) {
			final File file = fn.getSelectedFile();
			((Window) item.getContainer()).saveXML(file);
		}

	}

}
