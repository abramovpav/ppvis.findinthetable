package by.bsuir.iit.abramov.ppvis.findinthetable.controller;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Model;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Desktop;

public class Controller {
	private final Model		model;
	private final Desktop	desktop;

	public Controller(final Model model, final Desktop desktop) {

		this.model = model;
		this.desktop = desktop;
	}

	public void update() {

		desktop.refresh();
	}
}
