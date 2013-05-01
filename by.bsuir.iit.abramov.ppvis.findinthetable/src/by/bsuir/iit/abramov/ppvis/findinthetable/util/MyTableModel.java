package by.bsuir.iit.abramov.ppvis.findinthetable.util;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {

	public MyTableModel() {

		super();
		final String[] columnNames = { "", "", "", "", "", "" };
		setColumnIdentifiers(columnNames);
	}

	public MyTableModel(final int rowCount, final int columnCount) {

		super(rowCount, columnCount);
		final String[] columnNames = { "", "", "", "", "", "" };
		setColumnIdentifiers(columnNames);
	}
	/*
	 * @Override public int getRowCount() {
	 * 
	 * // TODO Auto-generated method stub return 2; }
	 * 
	 * @Override public int getColumnCount() {
	 * 
	 * // TODO Auto-generated method stub return 2; }
	 * 
	 * @Override public Object getValueAt(int rowIndex, int columnIndex) {
	 * 
	 * // TODO Auto-generated method stub
	 * 
	 * return null; }
	 */

}
