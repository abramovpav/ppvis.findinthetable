/*
 * (swing1.1beta3)
 * 
 */

package by.bsuir.iit.abramov.ppvis.findinthetable.model.table;

import java.awt.Dimension;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Student;

/**
 * @version 1.0 11/22/98
 */

public class AttributiveCellTableModel extends DefaultTableModel {

	private static Vector nonNullVector(final Vector v) {

		return (v != null) ? v : new Vector();
	}

	protected CellAttribute	cellAtt;
	private Student[]		students;

	public AttributiveCellTableModel() {

		this((Vector) null, 0);
	}

	public AttributiveCellTableModel(final int numColumns, final Student[] students) {

		this.students = students;
		final Vector names = new Vector(numColumns);
		names.setSize(numColumns);
		for (int i = 0; i < names.size(); ++i) {
			names.set(i, new String(""));
		}
		setColumnIdentifiers(names);
		dataVector = new Vector();
		final int numRows = students.length * 2 + 2;
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

		if (row == 0 && col == 0) {
			return "»м€";
		} else if (row == 0 && col == 1) {
			return "√руппа";
		} else if (row == 0 && col == 2) {
			return "Ёкзамены";
		} else if (row == 1 && col >= 2 && col % 2 == 0) {
			return col / 2;
		} else if (row >= 2 && col >= 2 && col % 2 == 0 && row % 2 == 0) {
			return "наим.";
		} else if (row >= 2 && col >= 2 && col % 2 != 0 && row % 2 == 0) {
			return "балл";
		} else {
			final int indexStudent = row / 2 - 1;
			if (row >= 2 && col == 0 && row % 2 == 0) {
				return students[indexStudent].getName();
			} else if (row >= 2 && col == 1 && row % 2 == 0) {
				return students[indexStudent].getGroup();
			} else {
				final int indexExam = col / 2 - 1;
				if (row >= 2 && col >= 2 && col % 2 == 0) {
					if (indexExam < students[indexStudent].getExams().length) {
						return students[indexStudent].getExams()[indexExam].getName();
					}
				} else if (row >= 2 && col >= 2 && col % 2 != 0) {
					if (indexExam < students[indexStudent].getExams().length) {
						return students[indexStudent].getExams()[indexExam].getMark();
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

	public void setStudentsList(final Student[] students) {

		this.students = students;
		final int numRows = students.length * 2 + 2;
		setNumRows(numRows);
		cellAtt = new DefaultCellAttribute(numRows, getColumnCount());
	}

	/*
	 * public void changeCellAttribute(int row, int column, Object command) {
	 * cellAtt.changeAttribute(row, column, command); }
	 * 
	 * public void changeCellAttribute(int[] rows, int[] columns, Object
	 * command) { cellAtt.changeAttribute(rows, columns, command); }
	 */

}
