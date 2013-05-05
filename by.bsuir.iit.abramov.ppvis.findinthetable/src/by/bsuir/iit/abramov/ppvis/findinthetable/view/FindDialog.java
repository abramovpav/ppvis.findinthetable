package by.bsuir.iit.abramov.ppvis.findinthetable.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

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

public class FindDialog extends JDialog {

	class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {

			if (e.getActionCommand().equals("Search")) {
				// search
				if (FindDialog.this.checkFields()) {

				}
			} else if (e.getActionCommand().equals("Cancel")) {
				isOK = false;
				setVisible(false);
				dispose();
			}

		}

	}

	private final JPanel										contentPanel	= new JPanel();
	public static final int										defaultX		= 100;
	public static final int										defaultY		= 100;
	public static final int										defaultWidth	= 461;
	public static final int										defaultHeight	= 378;
	private CellAttribute										cellAtt;
	private MultiSpanCellTable									table;
	private List<Controller>									observers;
	private AttributiveCellTableModel							tableModel;
	private Model												model;
	private Map<Integer, List<CoupleExt<String, JTextField>>>	groups;
	private boolean												isOK;
	private ButtonGroup											buttonGroup;

	private final ContentPane									contP;

	/**
	 * Create the dialog.
	 */
	public FindDialog(final ContentPane contP) {

		this.contP = contP;
		setBounds(FindDialog.defaultX, FindDialog.defaultY, 769, 378);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));

		final JPanel panel_lvl1 = new JPanel();
		contentPanel.add(panel_lvl1);

		initialization(panel_lvl1);

	}

	public boolean checkFields() {

		if (buttonGroup.getSelection() == null) {
			return false;
		} else {
			final Enumeration<AbstractButton> elements = buttonGroup.getElements();
			Integer num = null;
			while (elements.hasMoreElements()) {
				final JRadioButton button = (JRadioButton) elements.nextElement();
				if (button != null) {
					if (button.isSelected()) {
						num = Integer.parseInt(button.getText().substring(
								button.getText().lastIndexOf('#') + 1));
					}
				}
			}
			if (num != null) {
				if (checkFields(num)) {
					search(num);
				} else {
					JOptionPane.showMessageDialog(null,
							"***ERROR***: Incorrect search options");
				}
			}
		}
		return false;
	}

	private boolean checkFields(final int num) {

		final List<CoupleExt<String, JTextField>> list = groups.get(num);
		if (list == null) {
			System.out.println("***ERROR***: list is empty");
			return false;
		}
		for (int i = 0; i < list.size(); ++i) {
			final CoupleExt<String, JTextField> item = list.get(i);
			String text;
			switch (item.getField1().toCharArray()[0]) {
				case 'n':
					text = item.getField2().getText();
					if (text == null) {
						return false;
					}
				break;
				case 'g':
					text = item.getField2().getText();
					if (text.length() != 0) {
						if (!Util.isNumeric(text)) {
							return false;
						}
					}
				break;
				case 'e':
					text = item.getField2().getText();
					if (text == null) {
						return false;
					}
				break;
				case 'f':
					text = item.getField2().getText();
					if (text.length() != 0) {
						if (!Util.isNumeric(text)) {
							return false;
						}
					}
				break;
				case 't':
					text = item.getField2().getText();
					if (text.length() != 0) {
						if (!Util.isNumeric(text)) {
							return false;
						}
					}
				break;
			}
		}

		return true;
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

	private JTextField createLabel_JTextField(final JPanel panel, final String text,
			final int columns) {

		final JLabel label = new JLabel(text);
		panel.add(label);

		final JTextField textField = new JTextField();
		panel.add(textField);
		textField.setColumns(columns);
		return textField;
	}

	private JPanel createPanelLvl3(final JPanel panel_lvl2) {

		JPanel panel_lvl3;
		panel_lvl3 = new JPanel();
		panel_lvl3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_lvl2.add(panel_lvl3);
		panel_lvl3.setLayout(new BoxLayout(panel_lvl3, BoxLayout.Y_AXIS));
		return panel_lvl3;
	}

	private void createSearchPanel(final JPanel panel_lvl2, final int num,
			final Map<Integer, List<CoupleExt<String, JTextField>>> groups) {

		JPanel panel_lvl3;
		JRadioButton radioButton;
		panel_lvl3 = createPanelLvl3(panel_lvl2);
		radioButton = new JRadioButton("Search #" + Integer.toString(num));
		buttonGroup.add(radioButton);
		panel_lvl3.add(radioButton);
		List<CoupleExt<String, JTextField>> list;
		switch (num) {
			case 1:
				list = new ArrayList<CoupleExt<String, JTextField>>();
				search1(panel_lvl3, list);
				groups.put(1, list);
			break;
			case 2:
				list = new ArrayList<CoupleExt<String, JTextField>>();
				search2(panel_lvl3, list);
				groups.put(2, list);
			break;
			case 3:
				list = new ArrayList<CoupleExt<String, JTextField>>();
				search3(panel_lvl3, list);
				groups.put(3, list);
			break;
		}
	}

	private AttributiveCellTableModel createTable(final Student[] studentsInput,
			final JScrollPane scrollPane) {

		Student[] students = {};
		if (studentsInput != null) {
			students = studentsInput;
		}
		final AttributiveCellTableModel tableModel = new AttributiveCellTableModel(
				Desktop.COLUMNS_COUNT, students);
		cellAtt = tableModel.getCellAttribute();
		table = new MultiSpanCellTable(tableModel);
		scrollPane.setViewportView(table);
		table.setCellSelectionEnabled(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setDefaultRenderer(Object.class, new AttributiveCellRenderer());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		return tableModel;
	}

	private void initialization(final JPanel panel_lvl1) {

		groups = new HashMap<Integer, List<CoupleExt<String, JTextField>>>();

		panel_lvl1.setLayout(new BoxLayout(panel_lvl1, BoxLayout.Y_AXIS));

		final JPanel panel_lvl2 = new JPanel();
		panel_lvl1.add(panel_lvl2);
		panel_lvl2.setLayout(new BoxLayout(panel_lvl2, BoxLayout.Y_AXIS));

		buttonGroup = new ButtonGroup();

		for (int i = 1; i <= 3; ++i) {
			createSearchPanel(panel_lvl2, i, groups);
		}

		final JScrollPane scrollPane = new JScrollPane();
		contentPanel.add(scrollPane);

		final JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton button = new JButton("Search");
		button.setActionCommand("Search");
		button.addActionListener(new ButtonActionListener());
		buttonPane.add(button);
		getRootPane().setDefaultButton(button);

		button = new JButton("Cancel");
		button.setActionCommand("Cancel");
		button.addActionListener(new ButtonActionListener());
		buttonPane.add(button);

		observers = new ArrayList<Controller>();

		model = new Model();

		// model.addObserver(controller);
		// addObserver(controller);

		final Student[] students = model.getNextPageOfStudents();

		tableModel = createTable(students, scrollPane);
		prepareTable();
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

	private void search(final int num) {

		final Student[] students = contP.search(groups.get(num), num);
		// final Student[] pageOfStudents = model.getCurrPageOfStudent();
		setStudents(tableModel, students);
	}

	private void search1(final JPanel panel_lvl3,
			final List<CoupleExt<String, JTextField>> list) {

		final JPanel panel_lvl4 = new JPanel();
		panel_lvl3.add(panel_lvl4);
		panel_lvl4.setLayout(new BoxLayout(panel_lvl4, BoxLayout.Y_AXIS));

		JPanel panel_lvl5 = new JPanel();
		panel_lvl4.add(panel_lvl5);
		panel_lvl5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		final JTextField textField = createLabel_JTextField(panel_lvl5, "FirstName", 10);
		list.add(new CoupleExt<String, JTextField>("name", textField));
		panel_lvl5 = new JPanel();
		panel_lvl4.add(panel_lvl5);
		panel_lvl5.setLayout(new BoxLayout(panel_lvl5, BoxLayout.X_AXIS));

		JPanel panelFromTo = new JPanel();
		panel_lvl5.add(panelFromTo);

		final JTextField textField_1 = createLabel_JTextField(panelFromTo, "from", 2);
		list.add(new CoupleExt<String, JTextField>("from", textField_1));

		panelFromTo = new JPanel();
		panel_lvl5.add(panelFromTo);
		final JTextField textField_2 = createLabel_JTextField(panelFromTo, "to", 2);
		list.add(new CoupleExt<String, JTextField>("to", textField_2));
	}

	private void search2(final JPanel panel_lvl3,
			final List<CoupleExt<String, JTextField>> list) {

		JPanel panel_lvl4;
		JPanel panel_lvl5;
		panel_lvl4 = new JPanel();
		panel_lvl3.add(panel_lvl4);

		panel_lvl5 = new JPanel();
		panel_lvl4.add(panel_lvl5);
		panel_lvl5.setLayout(new BoxLayout(panel_lvl5, BoxLayout.Y_AXIS));

		final JLabel lblGroup = new JLabel("FirstName");
		panel_lvl5.add(lblGroup);

		final JLabel lblGroup_1 = new JLabel("Group");
		panel_lvl5.add(lblGroup_1);

		panel_lvl5 = new JPanel();
		panel_lvl4.add(panel_lvl5);
		panel_lvl5.setLayout(new BoxLayout(panel_lvl5, BoxLayout.Y_AXIS));

		final JTextField textField_3 = new JTextField();
		panel_lvl5.add(textField_3);
		textField_3.setColumns(10);

		list.add(new CoupleExt<String, JTextField>("name", textField_3));

		final JTextField textField_4 = new JTextField();
		panel_lvl5.add(textField_4);
		textField_4.setColumns(10);
		list.add(new CoupleExt<String, JTextField>("group", textField_4));
	}

	private void search3(final JPanel panel_lvl3,
			final List<CoupleExt<String, JTextField>> list) {

		JPanel panel_lvl4;
		JPanel panel_lvl5;
		JPanel panelFromTo;
		panel_lvl4 = new JPanel();
		panel_lvl3.add(panel_lvl4);
		panel_lvl4.setLayout(new BoxLayout(panel_lvl4, BoxLayout.Y_AXIS));

		panel_lvl5 = new JPanel();
		panel_lvl4.add(panel_lvl5);
		panel_lvl5.setLayout(new BoxLayout(panel_lvl5, BoxLayout.X_AXIS));

		panel_lvl5 = new JPanel();
		panel_lvl4.add(panel_lvl5);
		panel_lvl5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panel_lvl6 = new JPanel();
		panel_lvl5.add(panel_lvl6);
		panel_lvl6.setLayout(new BoxLayout(panel_lvl6, BoxLayout.Y_AXIS));

		final JLabel lblFirstname = new JLabel("FirstName");
		panel_lvl6.add(lblFirstname);

		final JLabel lblExam = new JLabel("Exam");
		panel_lvl6.add(lblExam);

		panel_lvl6 = new JPanel();
		panel_lvl5.add(panel_lvl6);
		panel_lvl6.setLayout(new BoxLayout(panel_lvl6, BoxLayout.Y_AXIS));

		final JTextField textField_5 = new JTextField();
		panel_lvl6.add(textField_5);
		textField_5.setColumns(10);
		list.add(new CoupleExt<String, JTextField>("name", textField_5));

		final JTextField textField_8 = new JTextField();
		panel_lvl6.add(textField_8);
		textField_8.setColumns(10);
		list.add(new CoupleExt<String, JTextField>("exam", textField_8));

		panel_lvl5 = new JPanel();
		panel_lvl4.add(panel_lvl5);
		panel_lvl5.setLayout(new BoxLayout(panel_lvl5, BoxLayout.X_AXIS));

		panelFromTo = new JPanel();
		panel_lvl5.add(panelFromTo);

		final JTextField textField_6 = createLabel_JTextField(panelFromTo, "from", 2);
		list.add(new CoupleExt<String, JTextField>("from", textField_6));

		panelFromTo = new JPanel();
		panel_lvl5.add(panelFromTo);

		final JTextField textField_7 = createLabel_JTextField(panelFromTo, "to", 2);
		list.add(new CoupleExt<String, JTextField>("to", textField_7));

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
