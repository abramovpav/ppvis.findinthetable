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
	private static final String	ABOUT_AUTHOR	= "about_author";
	private static final String	ABOUT_TITLE		= "about_title";
	private ToolPanel			toolBar;
	private Desktop				desktop;
	private final JFrame		parent;

	public ContentPane(final JFrame parent) {

		this.parent = parent;
		initialize();
	}

	public void about() {

		JOptionPane.showMessageDialog(null,
				Window.geti18nString(ContentPane.ABOUT_AUTHOR),
				Window.geti18nString(ContentPane.ABOUT_TITLE),
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void addStudent(final Student student) {

		desktop.addStudent(student);
	}

	public void close() {

		desktop.close();
	}

	public void deleteStudents(final List<Student> students) {

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

	public List<Student> search(final List<CoupleExt<String, JTextField>> list,
			final int num) {

		return desktop.search(list, num);
	}

	public void setEnLocale() {

		((Window) parent).setEnLocale();
	}

	public void setRuLocale() {

		((Window) parent).setRuLocale();
	}

	public void updateInteface() {

		toolBar.updateInterface();
		desktop.updateInterface();
	}
}
