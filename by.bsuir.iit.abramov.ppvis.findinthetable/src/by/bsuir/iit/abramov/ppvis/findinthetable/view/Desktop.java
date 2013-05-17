package by.bsuir.iit.abramov.ppvis.findinthetable.view;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import by.bsuir.iit.abramov.ppvis.findinthetable.controller.Controller;
import by.bsuir.iit.abramov.ppvis.findinthetable.controller.NavigationButtonActionListener;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.Model;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.Student;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.table.AttributiveCellRenderer;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.table.AttributiveCellTableModel;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.table.CellAttribute;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.table.MultiSpanCellTable;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.CoupleExt;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.Util;

public class Desktop extends JPanel {
	public static final String			MAX					= "max";

	private static final int			EXAM_COLUMNS_COUNT	= 2;
	private static final int			INFO_COLUMNS_COUNT	= 2;
	public static final int				EXAMS_COUNT			= 5;
	public static final int				COLUMNS_COUNT		= Desktop.INFO_COLUMNS_COUNT
																	+ Desktop.EXAMS_COUNT
																	* Desktop.EXAM_COLUMNS_COUNT;
	private static Logger				LOG					= Logger.getLogger(Desktop.class
																	.getName());
	public static final String			DECREMENT			= "-";
	public static final String			INCREMENT			= "+";
	public static final String			BUTTON_NEXT			= "next";
	public static final String			BUTTON_PREV			= "prev";
	private final ContentPane			contentPane;
	private CellAttribute				cellAtt;
	private MultiSpanCellTable			table;
	private List<Controller>			observers;
	private AttributiveCellTableModel	tableModel;
	private Model						model;
	private ButtonPanel					buttonPanel;

	public Desktop(final ContentPane contentPane) {

		this.contentPane = contentPane;
		initialize();
	}

	public void addObserver(final Controller observer) {

		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	public void addStudent(final Student student) {

		if (student != null) {
			model.addStudent(student);
			final List<Student> pageOfStudents = model.getCurrPageOfStudent();
			setStudents(tableModel, pageOfStudents);
		}
	}

	public void close() {

		final List<Student> pageOfStudents = new Vector<Student>();
		setStudents(tableModel, pageOfStudents);

	}

	private AttributiveCellTableModel createTable(final List<Student> studentsInput) {

		final List<Student> students = studentsInput;
		final AttributiveCellTableModel tableModel = new AttributiveCellTableModel(
				Desktop.COLUMNS_COUNT, students);
		cellAtt = tableModel.getCellAttribute();
		table = new MultiSpanCellTable(tableModel);
		table.setCellSelectionEnabled(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setDefaultRenderer(Object.class, new AttributiveCellRenderer());
		final JScrollPane scroll = new JScrollPane(table);

		add(new JScrollPane(scroll), BorderLayout.CENTER);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		return tableModel;
	}

	public void deleteStudents(final List<Student> students) {

		model.deleteStudents(students);
	}

	public final AttributiveCellTableModel getTableModel() {

		return tableModel;
	}

	public void initialize() {

		setLayout(new BorderLayout(0, 0));
		observers = new ArrayList<Controller>();

		model = new Model();

		final Controller controller = new Controller(model, this);
		model.addObserver(controller);
		addObserver(controller);

		final List<Student> students = model.getNextPageOfStudents();

		tableModel = createTable(students);
		prepareTable();

		buttonPanel = Util.createButtonPanel(model, this,
				new NavigationButtonActionListener(model, this));
		openXML(new File("c:\\students.xml"));
	}

	public void openXML(final File file) {

		model.openXML(file);
	}

	private void prepareTable() {

		Util.combine2FirstColumns(table, cellAtt);
		int i = 2;
		while (i + 2 <= table.getColumnCount()) {
			Util.combineNCellsInRow(table, cellAtt, 1, i, i + 1);
			i += 2;
		}
		Util.combineCellInExamCaption(table, cellAtt);
	}

	public void refresh() {

		final List<Student> pageOfStudents = model.getCurrPageOfStudent();
		setStudents(tableModel, pageOfStudents);
	}

	public void removeObserver(final Controller observer) {

		if (observers.contains(observer)) {
			observers.remove(observer);
		}
	}

	public void saveXML(final File file) {

		model.saveXML(file);
	}

	public List<Student> search(final List<CoupleExt<String, JTextField>> list,
			final int num) {

		List<Student> studentsVector = new Vector<Student>();
		String name, groupStr, topStr, botStr, examStr;
		switch (num) {
			case 1:
				topStr = "";
				botStr = "";
				name = "";
				for (int i = 0; i < list.size(); ++i) {
					final CoupleExt<String, JTextField> item = list.get(i);
					if (item.getField1() == Window.geti18nString(FindDialog.NAME)) {
						name = item.getField2().getText();
					} else if (item.getField1() == Window.geti18nString(FindDialog.FROM)) {
						botStr = item.getField2().getText();
					} else if (item.getField1() == Window.geti18nString(FindDialog.TO)) {
						topStr = item.getField2().getText();
					}
				}
				studentsVector = search(name, botStr, topStr);

			break;
			case 2:
				name = "";
				groupStr = "";
				for (int i = 0; i < list.size(); ++i) {
					final CoupleExt<String, JTextField> item = list.get(i);
					if (item.getField1() == Window.geti18nString(FindDialog.NAME)) {
						name = item.getField2().getText();
					} else if (item.getField1() == Window.geti18nString(FindDialog.GROUP)) {
						groupStr = item.getField2().getText();
					}
				}
				final Integer group = Util.isNumeric(groupStr) ? Integer
						.parseInt(groupStr) : null;
				studentsVector = search(name, group);
			break;
			case 3:
				name = "";
				examStr = "";
				topStr = "";
				botStr = "";
				for (int i = 0; i < list.size(); ++i) {
					final CoupleExt<String, JTextField> item = list.get(i);
					if (item.getField1() == Window.geti18nString(FindDialog.NAME)) {
						name = item.getField2().getText();
					} else if (item.getField1() == Window.geti18nString(FindDialog.EXAM)) {
						examStr = item.getField2().getText();
					} else if (item.getField1() == Window.geti18nString(FindDialog.FROM)) {
						botStr = item.getField2().getText();
					} else if (item.getField1() == Window.geti18nString(FindDialog.TO)) {
						topStr = item.getField2().getText();
					}
				}

				studentsVector = search(name, examStr, botStr, topStr);
			break;
		}
		return studentsVector;
	}

	public Vector<Student> search(final String name, final Integer group) {

		return model.search(name, group);
	}

	public Vector<Student> search(final String name, final String botStr,
			final String topStr) {

		return model.search(name, botStr, topStr);
	}

	public Vector<Student> search(final String name, final String examStr,
			final String botStr, final String topStr) {

		return model.search(name, examStr, botStr, topStr);
	}

	public void setStudents(final AttributiveCellTableModel tableModel,
			final List<Student> inputPageOfStudents) {

		final List<Student> pageOfStudents = inputPageOfStudents;
		if (pageOfStudents.size() == 0) {
			Desktop.LOG.info("List of students is empty");
		}
		tableModel.setStudentsList(pageOfStudents);
		cellAtt = tableModel.getCellAttribute();
		prepareTable();
	}

	public void updateInterface() {

		buttonPanel.updateInterface();
	}
}
