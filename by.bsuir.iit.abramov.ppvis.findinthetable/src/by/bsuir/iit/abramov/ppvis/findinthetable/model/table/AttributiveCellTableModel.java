/*
 * (swing1.1beta3)
 * 
 */

package by.bsuir.iit.abramov.ppvis.findinthetable.model.table;

import java.awt.Dimension;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Model;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.Student;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Window;

/**
 * @version 1.0 11/22/98
 */

public class AttributiveCellTableModel extends DefaultTableModel {

	private static Vector nonNullVector(final Vector v) {

		return (v != null) ? v : new Vector();
	}

	protected CellAttribute	cellAtt;
	private List<Student>	students;
	private ResourceBundle	resourceBundle;

	public AttributiveCellTableModel() {

		this((Vector) null, 0);
	}

	public AttributiveCellTableModel(final int numColumns, final List<Student> students) {

		this.students = students;
		final Vector names = new Vector(numColumns);
		names.setSize(numColumns);
		for (int i = 0; i < names.size(); ++i) {
			names.set(i, new String(""));
		}
		setColumnIdentifiers(names);
		dataVector = new Vector();
		final int numRows = students.size() * 2 + 2;
		setNumRows(numRows);
		cellAtt = new DefaultCellAttribute(numRows, numColumns);
	}

	public AttributiveCellTableModel(final Object[] columnNames, final int numRows) {

		this(DefaultTableModel.convertToVector(columnNames), numRows);
	}

	public AttributiveCellTableModel(final Object[][] data, final Object[] columnNames) {

		setDataVector(data, columnNames);
	}

	public AttributiveCellTableModel(final Vector columnNames, final int numRows) {

		setColumnIdentifiers(columnNames);
		dataVector = new Vector();
		setNumRows(numRows);
		cellAtt = new DefaultCellAttribute(numRows, columnNames.size());
	}

	public AttributiveCellTableModel(final Vector data, final Vector columnNames) {

		setDataVector(data, columnNames);
	}

	@Override
	public void addColumn(final Object columnName, final Vector columnData) {

		if (columnName == null) {
			throw new IllegalArgumentException("addColumn() - null parameter");
		}
		columnIdentifiers.addElement(columnName);
		int index = 0;
		final Enumeration enumeration = dataVector.elements();
		while (enumeration.hasMoreElements()) {
			Object value;
			if ((columnData != null) && (index < columnData.size())) {
				value = columnData.elementAt(index);
			} else {
				value = null;
			}
			((Vector) enumeration.nextElement()).addElement(value);
			index++;
		}

		//
		cellAtt.addColumn();

		fireTableStructureChanged();
	}

	@Override
	public void addRow(final Vector rowData) {

		Vector newData = null;
		if (rowData == null) {
			newData = new Vector(getColumnCount());
		} else {
			rowData.setSize(getColumnCount());
		}
		dataVector.addElement(newData);

		//
		cellAtt.addRow();

		newRowsAdded(new TableModelEvent(this, getRowCount() - 1, getRowCount() - 1,
				TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
	}

	public CellAttribute getCellAttribute() {

		return cellAtt;
	}

	@Override
	public Object getValueAt(final int row, final int col) {

		if (isNameCaptionCell(row, col)) {
			return Window.geti18nString(Model.FIELD_NAME);
		} else if (isGroupCaptionCell(row, col)) {
			return Window.geti18nString(Model.FIELD_GROUP);
		} else if (isExamsCaptionCell(row, col)) {
			return Window.geti18nString(Model.FIELD_EXAMS);
		} else if (isNumCaptionCell(row, col)) {
			return col / 2;
		} else if (isMarkCaptionCell(row, col)) {
			return Window.geti18nString(Model.FIELD_MARK);
		} else {
			final int indexStudent = row / 2 - 1;
			final Student student = students.get(indexStudent);
			if (isNameCell(row, col)) {
				return student.getName();
			} else if (isGroupCell(row, col)) {
				return student.getGroup();
			} else {
				final int indexExam = col / 2 - 1;
				if (isExamCell(row, col)) {
					if (isIndexOutOfExams(student, indexExam)) {
						return student.getExams().get(indexExam).getName();
					}
				} else if (isMarkCell(row, col)) {
					if (isIndexOutOfExams(student, indexExam)) {
						return student.getExams().get(indexExam).getMark();
					}
				}
			}
		}
		return "null";
	}

	@Override
	public void insertRow(final int row, Vector rowData) {

		if (rowData == null) {
			rowData = new Vector(getColumnCount());
		} else {
			rowData.setSize(getColumnCount());
		}

		dataVector.insertElementAt(rowData, row);

		//
		cellAtt.insertRow(row);

		newRowsAdded(new TableModelEvent(this, row, row, TableModelEvent.ALL_COLUMNS,
				TableModelEvent.INSERT));
	}

	private boolean isExamCell(final int row, final int col) {

		return row >= 2 && col >= 2 && col % 2 == 0;
	}

	private boolean isExamsCaptionCell(final int row, final int col) {

		return row == 0 && col == 2;
	}

	private boolean isGroupCaptionCell(final int row, final int col) {

		return row == 0 && col == 1;
	}

	private boolean isGroupCell(final int row, final int col) {

		return row >= 2 && col == 1 && row % 2 == 0;
	}

	private boolean isIndexOutOfExams(final Student student, final int indexExam) {

		return indexExam < student.getExams().size();
	}

	private boolean isMarkCaptionCell(final int row, final int col) {

		return isMarkCell(row, col) && row % 2 == 0;
	}

	private boolean isMarkCell(final int row, final int col) {

		return row >= 2 && col >= 2 && col % 2 != 0;
	}

	private boolean isNameCaptionCell(final int row, final int col) {

		return (row == 0 && col == 0) || (isExamCell(row, col) && row % 2 == 0);
	}

	private boolean isNameCell(final int row, final int col) {

		return row >= 2 && col == 0 && row % 2 == 0;
	}

	private boolean isNumCaptionCell(final int row, final int col) {

		return row == 1 && col >= 2 && col % 2 == 0;
	}

	private void justifyRows(final int from, final int to) {

		// Sometimes the DefaultTableModel is subclassed
		// instead of the AbstractTableModel by mistake.
		// Set the number of rows for the case when getRowCount
		// is overridden.
		dataVector.setSize(getRowCount());

		for (int i = from; i < to; i++) {
			if (dataVector.elementAt(i) == null) {
				dataVector.setElementAt(new Vector(), i);
			}
			((Vector) dataVector.elementAt(i)).setSize(getColumnCount());
		}
	}

	public void setCellAttribute(final CellAttribute newCellAtt) {

		final int numColumns = getColumnCount();
		final int numRows = getRowCount();
		if ((newCellAtt.getSize().width != numColumns)
				|| (newCellAtt.getSize().height != numRows)) {
			newCellAtt.setSize(new Dimension(numRows, numColumns));
		}
		cellAtt = newCellAtt;
		fireTableDataChanged();
	}

	@Override
	public void setColumnIdentifiers(final Vector columnIdentifiers) {

		setOldDataVector(dataVector, columnIdentifiers);
	}

	@Override
	public void setDataVector(final Vector newData, final Vector columnNames) {

		if (newData == null) {
			throw new IllegalArgumentException("setDataVector() - Null parameter");
		}
		dataVector = new Vector(0);
		setColumnIdentifiers(columnNames);
		dataVector = newData;

		//
		cellAtt = new DefaultCellAttribute(dataVector.size(), columnIdentifiers.size());

		newRowsAdded(new TableModelEvent(this, 0, getRowCount() - 1,
				TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
	}

	public void setOldDataVector(final Vector dataVector, final Vector columnIdentifiers) {

		this.dataVector = AttributiveCellTableModel.nonNullVector(dataVector);
		this.columnIdentifiers = AttributiveCellTableModel
				.nonNullVector(columnIdentifiers);
		justifyRows(0, getRowCount());
		fireTableStructureChanged();
	}

	public void setStudentsList(final List<Student> students) {

		this.students.clear();
		this.students.addAll(students);
		final int numRows = students.size() * 2 + 2;
		setNumRows(numRows);
		cellAtt = new DefaultCellAttribute(numRows, getColumnCount());
	}

}
