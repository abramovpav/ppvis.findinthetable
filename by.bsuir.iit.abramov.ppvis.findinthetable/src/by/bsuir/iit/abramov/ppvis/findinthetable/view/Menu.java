package by.bsuir.iit.abramov.ppvis.findinthetable.view;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import by.bsuir.iit.abramov.ppvis.findinthetable.controller.AboutButtonsListener;
import by.bsuir.iit.abramov.ppvis.findinthetable.controller.EditButtonsListener;
import by.bsuir.iit.abramov.ppvis.findinthetable.controller.FileButtonsListener;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.ExtJMenuItem;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.MenuContent;

public class Menu extends JMenuBar {
	private Map<MenuContent, JMenu>	mnButtons;
	private Map<String, JMenuItem>	mnItems;
	private final JPanel			parent;

	public Menu(final JPanel parent) {

		super();
		this.parent = parent;
		System.out.println("Menu()");
		initialize();
	}

	private void initialize() {

		mnButtons = new HashMap<MenuContent, JMenu>();
		mnItems = new HashMap<String, JMenuItem>();
		for (final MenuContent menu : MenuContent.values()) {
			final JMenu mnButton = new JMenu(menu.getSection());
			mnButtons.put(menu, mnButton);
			add(mnButton);
			for (int j = 0; j < menu.getItems().length; ++j) {
				final JMenuItem mnItem = new ExtJMenuItem(menu.getItems()[j], parent);
				mnItems.put(menu.getItems()[j], mnItem);
				mnButton.add(mnItem);
				if (menu.getSection() == "Edit") {
					mnItem.addActionListener(new EditButtonsListener(mnItem.getText()));
				}
				if (menu.getSection() == "File") {
					mnItem.addActionListener(new FileButtonsListener(mnItem.getText()));
				}
				if (menu.getSection() == "About") {
					mnItem.addActionListener(new AboutButtonsListener(mnItem.getText()));
				}
			}
		}
	}

	public void setActionListener(final String name, final ActionListener listener) {

		final JMenuItem mnItem = mnItems.get(name);
		if (mnItem != null) {
			mnItem.addActionListener(listener);
		}
	}
}
