package by.bsuir.iit.abramov.ppvis.findinthetable.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Model;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.Student;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.table.AttributiveCellRenderer;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.table.AttributiveCellTableModel;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.table.CellAttribute;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.table.CellSpan;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.table.MultiSpanCellTable;

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

	private static final String			DECREMENT	= "-";
	private static final String			INCREMENT	= "+";
	private static final String			BUTTON_NEXT	= "next";
	private static final String			BUTTON_PREV	= "prev";
	private final ContentPane			contentPane;
	private CellAttribute				cellAtt;
	private MultiSpanCellTable			table;

	private AttributiveCellTableModel	tableModel;

	private Model						model;

	public Desktop(final ContentPane contentPane) {

		this.contentPane = contentPane;
		initialize();
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
		final AttributiveCellTableModel tableModel = new AttributiveCellTableModel(12,
				students);
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

	public void initialize() {

		setLayout(new BorderLayout(0, 0));

		model = new Model();

		/*
		 * model.addStudent(new Student("Abramov", new Integer(121702), new
		 * Exam("Math", 8), new Exam("English", 10))); model.addStudent(new
		 * Student("Petrov1", new Integer(121702), new Exam("Math", 8), new
		 * Exam("English", 10))); model.addStudent(new Student("Petrov2", new
		 * Integer(121702), new Exam("Math", 8), new Exam("English", 10)));
		 * model.addStudent(new Student("Petrov3", new Integer(121702), new
		 * Exam("Math", 8), new Exam("English", 10))); model.addStudent(new
		 * Student("Petrov4", new Integer(121702), new Exam("Math", 8), new
		 * Exam("English", 10))); model.addStudent(new Student("Petrov5", new
		 * Integer(121702), new Exam("Math", 8), new Exam("English", 10)));
		 * model.addStudent(new Student("Petrov6", new Integer(121702), new
		 * Exam("Math", 8), new Exam("English", 10))); model.addStudent(new
		 * Student("Petrov7", new Integer(121702), new Exam("Math", 8), new
		 * Exam("English", 10))); model.addStudent(new Student("Petrov8", new
		 * Integer(121702), new Exam("Math", 8), new Exam("English", 10)));
		 * model.addStudent(new Student("Petrov9", new Integer(121702), new
		 * Exam("Math", 8), new Exam("English", 10))); model.addStudent(new
		 * Student("Petrov10", new Integer(121702), new Exam("Math", 8), new
		 * Exam("English", 10))); model.addStudent(new Student("Petrov11", new
		 * Integer(121702), new Exam(null, null), new Exam("English", 10)));
		 * model.addStudent(new Student("Petrov12", new Integer(121702), new
		 * Exam(null, null), new Exam("English", 10))); model.addStudent(new
		 * Student("Petrov13", new Integer(121702), new Exam(null, null), new
		 * Exam("English", 10))); model.addStudent(new Student("Petrov14", new
		 * Integer(121702), new Exam(null, null), new Exam("English", 10)));
		 * model.addStudent(new Student("Petrov15", new Integer(121702), new
		 * Exam(null, null), new Exam("English", 10))); model.addStudent(new
		 * Student("Petrov16", new Integer(121702), new Exam(null, null), new
		 * Exam("English", 10))); model.addStudent(new Student("Petrov17", new
		 * Integer(121702), new Exam(null, null), new Exam("English", 10)));
		 * model.addStudent(new Student("Petrov18", new Integer(121702), new
		 * Exam(null, null), new Exam("English", 10)));
		 */
		final Student[] students = model.getNextPageOfStudents();

		tableModel = createTable(students);
		prepareTable();

		createButtonPanel(model, tableModel);

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

	private void setStudents(final AttributiveCellTableModel tableModel,
			final Student[] pageOfStudents) {

		if (pageOfStudents == null) {
			System.out.println("null");
		} else {
			for (int i = 0; i < pageOfStudents.length; ++i) {
				System.out.println(pageOfStudents[i].getName());
			}
			tableModel.setStudentsList(pageOfStudents);
			cellAtt = tableModel.getCellAttribute();
			prepareTable();
		}
	}
}
