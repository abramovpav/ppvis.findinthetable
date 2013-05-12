package by.bsuir.iit.abramov.ppvis.findinthetable.view;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import by.bsuir.iit.abramov.ppvis.findinthetable.controller.AboutButtonActionListener;
import by.bsuir.iit.abramov.ppvis.findinthetable.controller.EditButtonsListener;
import by.bsuir.iit.abramov.ppvis.findinthetable.controller.FileButtonsListener;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.ExtJMenuItem;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.IllegalParametrs;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.MenuContent;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.MenuItem;

public class Menu extends JMenuBar {
	private Map<MenuContent, JMenu>		mnButtons;
	private Map<MenuItem, JMenuItem>	mnItems;
	private final JPanel				parent;

	public Menu(final JPanel parent) {

		super();
		this.parent = parent;
		initialize();
	}

	private void initialize() {

		mnButtons = new HashMap<MenuContent, JMenu>();
		mnItems = new HashMap<MenuItem, JMenuItem>();
		for (final MenuContent menu : MenuContent.values()) {
			final JMenu mnButton = new JMenu(Window.geti18nString(menu.getSection()));
			mnButtons.put(menu, mnButton);
			add(mnButton);
			final List<MenuItem> items = menu.getItems();
			for (final MenuItem item : items) {
				final JMenuItem mnItem = new ExtJMenuItem(Window.geti18nString(item
						.getName()), parent);
				mnItems.put(item, mnItem);
				mnButton.add(mnItem);
				try {
					switch (menu) {
						case FILE:
							mnItem.addActionListener(new FileButtonsListener(item));
						break;
						case EDIT:
							mnItem.addActionListener(new EditButtonsListener(item));
						break;
						case ABOUT:
							mnItem.addActionListener(new AboutButtonActionListener());
						break;
					}
				} catch (final IllegalParametrs e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
