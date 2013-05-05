package by.bsuir.iit.abramov.ppvis.findinthetable.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import by.bsuir.iit.abramov.ppvis.findinthetable.controller.Controller;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.Model;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.Student;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.table.AttributiveCellRenderer;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.table.AttributiveCellTableModel;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.table.CellAttribute;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.table.CellSpan;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.table.MultiSpanCellTable;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.CoupleExt;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.Util;

public class Desktop extends JPanel {
	class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {

			final JButton button = (JButton) e.getSource();
			Student[] pageOfStudents = null;

			if (button.getText().equalsIgnoreCase(Desktop.BUTTON_NEXT)) {
				pageOfStudents = model.getNextPageOfStudents();
			} else if (button.getText().equalsIgnoreCase(Desktop.BUTTON_PREV)) {
				pageOfStudents = model.getPrevPageOfStudents();
			}
			setStudents(tableModel, pageOfStudents);

		}
	}

	class ViewSizeButtonListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {

			final JButton button = (JButton) e.getSource();

			if (button.getText().equalsIgnoreCase(Desktop.DECREMENT)) {
				model.setViewSize(model.getViewSize() - 1);
			} else if (button.getText().equalsIgnoreCase(Desktop.INCREMENT)) {
				model.setViewSize(model.getViewSize() + 1);
			}

			final Student[] pageOfStudents = model.getCurrPageOfStudent();
			setStudents(tableModel, pageOfStudents);
		}
	}

	private static final int			EXAM_COLUMNS_COUNT	= 2;
	private static final int			INFO_COLUMNS_COUNT	= 2;
	public static final int				EXAMS_COUNT			= 5;
	public static final int				COLUMNS_COUNT		= Desktop.INFO_COLUMNS_COUNT
																	+ Desktop.EXAMS_COUNT
																	* Desktop.EXAM_COLUMNS_COUNT;
	private static final String			DECREMENT			= "-";
	private static final String			INCREMENT			= "+";
	private static final String			BUTTON_NEXT			= "next";
	private static final String			BUTTON_PREV			= "prev";
	private final ContentPane			contentPane;
	private CellAttribute				cellAtt;
	private MultiSpanCellTable			table;
	private List<Controller>			observers;
	private AttributiveCellTableModel	tableModel;
	private Model						model;

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

		model.addStudent(student);
		final Student[] pageOfStudents = model.getCurrPageOfStudent();
		setStudents(tableModel, pageOfStudents);
	}

	public void close() {

		final Student[] pageOfStudents = {};
		setStudents(tableModel, pageOfStudents);

	}

	private void combine(final CellAttribute cellAtt, final MultiSpanCellTable table,
			final int[] columns, final int[] rows) {

		((CellSpan) cellAtt).combine(rows, columns);
		table.clearSelection();
		table.revalidate();
		table.repaint();
	}

	private void combine2FirstColumns() {

		int i = 0;
		while (i + 2 <= table.getRowCount()) {
			combineNCellsInColumn(0, i, i + 1);
			combineNCellsInColumn(1, i, i + 1);
			i += 2;
		}
	}

	private void combineCellInExamCaption() {

		final int[] rows = { 0 };
		final int[] columns = new int[table.getColumnCount() - 2];
		for (int i = 0; i < columns.length; ++i) {
			columns[i] = 2 + i;
		}
		combine(cellAtt, table, columns, rows);
	}

	private void combineNCellsInColumn(final int i, final int... rows) {

		final int[] columns = { i };
		combine(cellAtt, table, columns, rows);
	}

	private void combineNCellsInRow(final int i, final int... columns) {

		final int[] rows = { i };
		combine(cellAtt, table, columns, rows);
	}

	private void createButtonPanel(final Model model,
			final AttributiveCellTableModel tableModel) {

		final JPanel buttonPanel = new JPanel();
		add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new BorderLayout(0, 0));

		JButton button = new JButton(Desktop.BUTTON_PREV);
		buttonPanel.add(button, BorderLayout.WEST);
		button.addActionListener(new ButtonActionListener());

		button = new JButton(Desktop.BUTTON_NEXT);
		button.addActionListener(new ButtonActionListener());
		buttonPanel.add(button, BorderLayout.EAST);

		createViewSizePanel(buttonPanel);
	}

	private AttributiveCellTableModel createTable(final Student[] studentsInput) {

		Student[] students = {};
		if (studentsInput != null) {
			students = studentsInput;
		}
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

	private void createViewSizePanel(final JPanel buttonPanel) {

		JButton button;
		final JPanel viewSizePanel = new JPanel();
		buttonPanel.add(viewSizePanel, BorderLayout.CENTER);
		viewSizePanel.setLayout(new BorderLayout(0, 0));
		final JTextField text = new JTextField(Integer.toString(model.getViewSize()));
		viewSizePanel.add(text, BorderLayout.CENTER);
		text.setEditable(false);
		model.setObserver(text);
		button = new JButton(Desktop.DECREMENT);
		viewSizePanel.add(button, BorderLayout.WEST);
		button.addActionListener(new ViewSizeButtonListener());
		button = new JButton(Desktop.INCREMENT);
		button.addActionListener(new ViewSizeButtonListener());
		viewSizePanel.add(button, BorderLayout.EAST);
	}

	public void deleteStudents(final Student[] students) {

		model.deleteStudents(students);
	}

	public void initialize() {

		setLayout(new BorderLayout(0, 0));
		observers = new ArrayList<Controller>();

		model = new Model();

		final Controller controller = new Controller(model, this);
		model.addObserver(controller);
		addObserver(controller);

		final Student[] students = model.getNextPageOfStudents();

		tableModel = createTable(students);
		prepareTable();

		createButtonPanel(model, tableModel);
		openXML(new File("c:\\students.xml"));
	}

	public void openXML(final File file) {

		model.openXML(file);
	}

	private void prepareTable() {

		combine2FirstColumns();
		int i = 2;
		while (i + 2 <= table.getColumnCount()) {
			combineNCellsInRow(1, i, i + 1);
			i += 2;
		}
		combineCellInExamCaption();
	}

	public void refresh() {

		final Student[] pageOfStudents = model.getCurrPageOfStudent();
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

	public Student[] search(final List<CoupleExt<String, JTextField>> list, final int num) {

		Vector<Student> studentsVector = new Vector<Student>();
		String name, groupStr, topStr, botStr, examStr;
		switch (num) {
			case 1:
				System.out.println("search #1");
				topStr = "";
				botStr = "";
				name = "";
				for (int i = 0; i < list.size(); ++i) {
					final CoupleExt<String, JTextField> item = list.get(i);
					if (item.getField1() == FindDialog.NAME) {
						name = item.getField2().getText();
					} else if (item.getField1() == FindDialog.FROM) {
						botStr = item.getField2().getText();
					} else if (item.getField1() == FindDialog.TO) {
						topStr = item.getField2().getText();
					}
				}
				studentsVector = search(name, botStr, topStr);

			break;
			case 2:
				System.out.println("search #2");
				name = "";
				groupStr = "";
				for (int i = 0; i < list.size(); ++i) {
					final CoupleExt<String, JTextField> item = list.get(i);
					if (item.getField1() == FindDialog.NAME) {
						name = item.getField2().getText();
					} else if (item.getField1() == FindDialog.GROUP) {
						groupStr = item.getField2().getText();
					}
				}
				final Integer group = Util.isNumeric(groupStr) ? Integer
						.parseInt(groupStr) : null;
				studentsVector = search(name, group);
			break;
			case 3:
				System.out.println("search #3");
				name = "";
				examStr = "";
				topStr = "";
				botStr = "";
				for (int i = 0; i < list.size(); ++i) {
					final CoupleExt<String, JTextField> item = list.get(i);
					if (item.getField1() == FindDialog.NAME) {
						name = item.getField2().getText();
					} else if (item.getField1() == FindDialog.EXAM) {
						examStr = item.getField2().getText();
					} else if (item.getField1() == FindDialog.FROM) {
						botStr = item.getField2().getText();
					} else if (item.getField1() == FindDialog.TO) {
						topStr = item.getField2().getText();
					}
				}

				studentsVector = search(name, examStr, botStr, topStr);
			break;
		}
		return Util.getStudents(studentsVector);
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

	private void setStudents(final AttributiveCellTableModel tableModel,
			final Student[] inputPageOfStudents) {

		Student[] pageOfStudents = inputPageOfStudents;
		if (pageOfStudents == null) {
			System.out.println("List of students is empty");
			pageOfStudents = new Student[0];
		}
		{
			tableModel.setStudentsList(pageOfStudents);
			cellAtt = tableModel.getCellAttribute();
			prepareTable();
		}
	}
}
