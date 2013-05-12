package by.bsuir.iit.abramov.ppvis.findinthetable.view;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import by.bsuir.iit.abramov.ppvis.findinthetable.controller.EditButtonsListener;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.IllegalParametrs;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.MenuContent;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.MenuItem;

public class ToolPanel extends JPanel {
	private final ContentPane			contentPane;
	private Map<MenuItem, ExtJButton>	buttons;
	private static Logger				LOG	= Logger.getLogger(ToolPanel.class.getName());
	private JToolBar					toolBar;

	public ToolPanel(final ContentPane contentPane) {

		this.contentPane = contentPane;
		initialize();
		createComponents();
	}

	private void createComponents() {

		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setOrientation(SwingConstants.VERTICAL);
		add(toolBar);
		for (final MenuItem menuItem : MenuContent.EDIT.getItems()) {
			final ExtJButton button = new ExtJButton(Window.geti18nString(menuItem
					.getName()), contentPane);
			buttons.put(menuItem, button);
			toolBar.add(button);
			try {
				button.addActionListener(new EditButtonsListener(menuItem));
			} catch (final IllegalParametrs e) {
				// e.printStackTrace();
				ToolPanel.LOG.log(Level.SEVERE, "IllegalParametrs", e);
			}
		}
	}

	private void initialize() {

		buttons = new HashMap<MenuItem, ExtJButton>();
	}

	public void updateInterface() {

		for (final MenuItem menuItem : MenuContent.EDIT.getItems()) {
			toolBar.remove(buttons.get(menuItem));
		}
		buttons.clear();
		for (final MenuItem menuItem : MenuContent.EDIT.getItems()) {
			final ExtJButton button = new ExtJButton(Window.geti18nString(menuItem
					.getName()), contentPane);
			buttons.put(menuItem, button);
			toolBar.add(button);
			try {
				button.addActionListener(new EditButtonsListener(menuItem));
			} catch (final IllegalParametrs e) {
				// e.printStackTrace();
				ToolPanel.LOG.log(Level.SEVERE, "IllegalParametrs", e);
			}
		}
	}

}
