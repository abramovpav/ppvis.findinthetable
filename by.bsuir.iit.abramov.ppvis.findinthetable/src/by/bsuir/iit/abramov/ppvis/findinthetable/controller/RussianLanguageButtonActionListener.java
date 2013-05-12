package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import by.bsuir.iit.abramov.ppvis.findinthetable.util.ActionButton;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.ContentPane;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Window;

public class RussianLanguageButtonActionListener implements ButtonActionListener {


	@Override
	public void action(ActionEvent e) {

		ActionButton button = (ActionButton)e.getSource();
		ContentPane contentPane  = (ContentPane)button.getContainer();
		contentPane.setRuLocale();
	}

}
