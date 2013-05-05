package by.bsuir.iit.abramov.ppvis.findinthetable.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Student;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.CoupleExt;

public class ContentPane extends JPanel {
	private ToolPanel		toolBar;
	private Desktop			desktop;
	private final JFrame	parent;

	public ContentPane(final JFrame parent) {

		this.parent = parent;
		initialize();
	}

	public void about() {

		JOptionPane.showMessageDialog(null,
				"Автор: Абрамов Павел. \ne-mail: abramovpav@gmail.com", "Об авторе",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void addStudent(final Student student) {

		desktop.addStudent(student);
	}

	public void close() {

		desktop.close();
	}

	public void deleteStudents(final Student[] students) {

		desktop.deleteStudents(students);
	}

	public void exit() {

		((Window) parent).exit();
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

	public void saveXML(final File file) {

		desktop.saveXML(file);
	}

	public Student[] search(final List<CoupleExt<String, JTextField>> list, final int num) {

		return desktop.search(list, num);
	}
}
