package by.bsuir.iit.abramov.ppvis.findinthetable.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Student;

public class ContentPane extends JPanel {
	private final Menu	menu;
	private ToolPanel	toolBar;
	private Desktop		desktop;

	public ContentPane(final Menu menu) {

		this.menu = menu;
		initialize();
	}

	public void addStudent(final Student student) {

		desktop.addStudent(student);
	}

	private void initialize() {

		setLayout(new BorderLayout(0, 0));
		desktop = new Desktop(this);
		desktop.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(desktop, BorderLayout.CENTER);
		toolBar = new ToolPanel(this);
		add(toolBar, BorderLayout.EAST);
	}

	public void openXML(final File file) {

		desktop.openXML(file);
	}
}
