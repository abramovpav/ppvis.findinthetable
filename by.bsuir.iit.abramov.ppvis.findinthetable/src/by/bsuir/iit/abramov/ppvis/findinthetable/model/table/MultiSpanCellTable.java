/*
 * (swing1.1beta3)
 * 
 */

package by.bsuir.iit.abramov.ppvis.findinthetable.model.table;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Enumeration;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * @version 1.0 11/26/98
 */

public class MultiSpanCellTable extends JTable {

	public MultiSpanCellTable(final TableModel model) {

		super(model);
		setUI(new MultiSpanCellTableUI());
		getTableHeader().setReorderingAllowed(false);
		setCellSelectionEnabled(true);
		setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	}

	@Override
	public int columnAtPoint(final Point point) {

		return rowColumnAtPoint(point)[CellSpan.COLUMN];
	}

	@Override
	public void columnSelectionChanged(final ListSelectionEvent e) {

		repaint();
	}

	@Override
	public Rectangle getCellRect(int row, int column, final boolean includeSpacing) {

		final Rectangle sRect = super.getCellRect(row, column, includeSpacing);
		if ((row < 0) || (column < 0) || (getRowCount() <= row)
				|| (getColumnCount() <= column)) {
			return sRect;
		}
		final CellSpan cellAtt = (CellSpan) ((AttributiveCellTableModel) getModel())
				.getCellAttribute();
		if (!cellAtt.isVisible(row, column)) {
			final int temp_row = row;
			final int temp_column = column;
			row += cellAtt.getSpan(temp_row, temp_column)[CellSpan.ROW];
			column += cellAtt.getSpan(temp_row, temp_column)[CellSpan.COLUMN];
		}
		final int[] n = cellAtt.getSpan(row, column);

		int index = 0;
		final int columnMargin = getColumnModel().getColumnMargin();
		final Rectangle cellFrame = new Rectangle();
		final int aCellHeight = rowHeight + rowMargin;
		cellFrame.y = row * aCellHeight;
		cellFrame.height = n[CellSpan.ROW] * aCellHeight;

		final Enumeration enumeration = getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			final TableColumn aColumn = (TableColumn) enumeration.nextElement();
			cellFrame.width = aColumn.getWidth() + columnMargin;
			if (index == column) {
				break;
			}
			cellFrame.x += cellFrame.width;
			index++;
		}
		for (int i = 0; i < n[CellSpan.COLUMN] - 1; i++) {
			final TableColumn aColumn = (TableColumn) enumeration.nextElement();
			cellFrame.width += aColumn.getWidth() + columnMargin;
		}

		if (!includeSpacing) {
			final Dimension spacing = getIntercellSpacing();
			cellFrame.setBounds(cellFrame.x + spacing.width / 2, cellFrame.y
					+ spacing.height / 2, cellFrame.width - spacing.width,
					cellFrame.height - spacing.height);
		}
		return cellFrame;
	}

	@Override
	public int rowAtPoint(final Point point) {

		return rowColumnAtPoint(point)[CellSpan.ROW];
	}

	private int[] rowColumnAtPoint(final Point point) {

		final int[] retValue = { -1, -1 };
		final int row = point.y / (rowHeight + rowMargin);
		if ((row < 0) || (getRowCount() <= row)) {
			return retValue;
		}
		final int column = getColumnModel().getColumnIndexAtX(point.x);

		final CellSpan cellAtt = (CellSpan) ((AttributiveCellTableModel) getModel())
				.getCellAttribute();

		if (cellAtt.isVisible(row, column)) {
			retValue[CellSpan.COLUMN] = column;
			retValue[CellSpan.ROW] = row;
			return retValue;
		}
		retValue[CellSpan.COLUMN] = column
				+ cellAtt.getSpan(row, column)[CellSpan.COLUMN];
		retValue[CellSpan.ROW] = row + cellAtt.getSpan(row, column)[CellSpan.ROW];
		return retValue;
	}

	@Override
	public void valueChanged(final ListSelectionEvent e) {

		final int firstIndex = e.getFirstIndex();
		final int lastIndex = e.getLastIndex();
		if (firstIndex == -1 && lastIndex == -1) { // Selection cleared.
			repaint();
		}
		final Rectangle dirtyRegion = getCellRect(firstIndex, 0, false);
		final int numCoumns = getColumnCount();
		int index = firstIndex;
		for (int i = 0; i < numCoumns; i++) {
			dirtyRegion.add(getCellRect(index, i, false));
		}
		index = lastIndex;
		for (int i = 0; i < numCoumns; i++) {
			dirtyRegion.add(getCellRect(index, i, false));
		}
		repaint(dirtyRegion.x, dirtyRegion.y, dirtyRegion.width, dirtyRegion.height);
	}

}
