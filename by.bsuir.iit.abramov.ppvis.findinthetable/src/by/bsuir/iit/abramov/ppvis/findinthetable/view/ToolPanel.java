package by.bsuir.iit.abramov.ppvis.findinthetable.view;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import by.bsuir.iit.abramov.ppvis.findinthetable.controller.EditButtonsListener;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.Student;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.MenuContent;

public class ToolPanel extends JPanel {
	private final ContentPane		contentPane;
	private Map<String, ExtJButton>	buttons;

	public ToolPanel(final ContentPane contentPane) {

		this.contentPane = contentPane;
		initialize();
		createComponents();
	}

	public void addStudent(final Student student) {

		contentPane.addStudent(student);
	}

	private void createComponents() {

		final JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setOrientation(SwingConstants.VERTICAL);
		add(toolBar);
		// buttons
		for (final String name : MenuContent.Edit.getItems()) {
			final ExtJButton button = new ExtJButton(name, this);
			buttons.put(name, button);
			toolBar.add(button);
			button.addActionListener(new EditButtonsListener(name));
		}
	}

	private void initialize() {

		buttons = new HashMap<String, ExtJButton>();
	}

}
