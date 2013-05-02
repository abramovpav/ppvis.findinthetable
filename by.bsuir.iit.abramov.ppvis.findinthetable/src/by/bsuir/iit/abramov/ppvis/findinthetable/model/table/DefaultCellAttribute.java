/*
 * (swing1.1beta3)
 * 
 */

package by.bsuir.iit.abramov.ppvis.findinthetable.model.table;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

/**
 * @version 1.0 11/22/98
 */

public class DefaultCellAttribute
// implements CellAttribute ,CellSpan {
		implements CellAttribute, CellSpan, ColoredCell, CellFont {

	//
	// !!!! CAUTION !!!!!
	// these values must be synchronized to Table data
	//
	protected int		rowSize;
	protected int		columnSize;
	protected int[][][]	span;		// CellSpan
	protected Color[][]	foreground; // ColoredCell
	protected Color[][]	background; //
	protected Font[][]	font;		// CellFont

	public DefaultCellAttribute() {

		this(1, 1);
	}

	public DefaultCellAttribute(final int numRows, final int numColumns) {

		setSize(new Dimension(numColumns, numRows));
	}

	//
	// CellAttribute
	//
	@Override
	public void addColumn() {

		final int[][][] oldSpan = span;
		final int numRows = oldSpan.length;
		final int numColumns = oldSpan[0].length;
		span = new int[numRows][numColumns + 1][2];
		System.arraycopy(oldSpan, 0, span, 0, numRows);
		for (int i = 0; i < numRows; i++) {
			span[i][numColumns][CellSpan.COLUMN] = 1;
			span[i][numColumns][CellSpan.ROW] = 1;
		}
	}

	@Override
	public void addRow() {

		final int[][][] oldSpan = span;
		final int numRows = oldSpan.length;
		final int numColumns = oldSpan[0].length;
		span = new int[numRows + 1][numColumns][2];
		System.arraycopy(oldSpan, 0, span, 0, numRows);
		for (int i = 0; i < numColumns; i++) {
			span[numRows][i][CellSpan.COLUMN] = 1;
			span[numRows][i][CellSpan.ROW] = 1;
		}
	}

	@Override
	public void combine(final int[] rows, final int[] columns) {

		if (isOutOfBounds(rows, columns)) {
			return;
		}
		final int rowSpan = rows.length;
		final int columnSpan = columns.length;
		final int startRow = rows[0];
		final int startColumn = columns[0];
		for (int i = 0; i < rowSpan; i++) {
			for (int j = 0; j < columnSpan; j++) {
				if ((span[startRow + i][startColumn + j][CellSpan.COLUMN] != 1)
						|| (span[startRow + i][startColumn + j][CellSpan.ROW] != 1)) {
					// System.out.println("can't combine");
					return;
				}
			}
		}
		for (int i = 0, ii = 0; i < rowSpan; i++, ii--) {
			for (int j = 0, jj = 0; j < columnSpan; j++, jj--) {
				span[startRow + i][startColumn + j][CellSpan.COLUMN] = jj;
				span[startRow + i][startColumn + j][CellSpan.ROW] = ii;
				// System.out.println("r " +ii +"  c " +jj);
			}
		}
		span[startRow][startColumn][CellSpan.COLUMN] = columnSpan;
		span[startRow][startColumn][CellSpan.ROW] = rowSpan;

	}

	@Override
	public Color getBackground(final int row, final int column) {

		if (isOutOfBounds(row, column)) {
			return null;
		}
		return background[row][column];
	}

	//
	// CellFont
	//
	@Override
	public Font getFont(final int row, final int column) {

		if (isOutOfBounds(row, column)) {
			return null;
		}
		return font[row][column];
	}

	//
	// ColoredCell
	//
	@Override
	public Color getForeground(final int row, final int column) {

		if (isOutOfBounds(row, column)) {
			return null;
		}
		return foreground[row][column];
	}

	@Override
	public Dimension getSize() {

		return new Dimension(rowSize, columnSize);
	}

	//
	// CellSpan
	//
	@Override
	public int[] getSpan(final int row, final int column) {

		if (isOutOfBounds(row, column)) {
			final int[] ret_code = { 1, 1 };
			return ret_code;
		}
		return span[row][column];
	}

	protected void initValue() {

		for (int i = 0; i < span.length; i++) {
			for (int j = 0; j < span[i].length; j++) {
				span[i][j][CellSpan.COLUMN] = 1;
				span[i][j][CellSpan.ROW] = 1;
			}
		}
	}

	@Override
	public void insertRow(final int row) {

		final int[][][] oldSpan = span;
		final int numRows = oldSpan.length;
		final int numColumns = oldSpan[0].length;
		span = new int[numRows + 1][numColumns][2];
		if (0 < row) {
			System.arraycopy(oldSpan, 0, span, 0, row - 1);
		}
		System.arraycopy(oldSpan, 0, span, row, numRows - row);
		for (int i = 0; i < numColumns; i++) {
			span[row][i][CellSpan.COLUMN] = 1;
			span[row][i][CellSpan.ROW] = 1;
		}
	}

	protected boolean isOutOfBounds(final int row, final int column) {

		if ((row < 0) || (rowSize <= row) || (column < 0) || (columnSize <= column)) {
			return true;
		}
		return false;
	}

	protected boolean isOutOfBounds(final int[] rows, final int[] columns) {

		for (int i = 0; i < rows.length; i++) {
			if ((rows[i] < 0) || (rowSize <= rows[i])) {
				return true;
			}
		}
		for (int i = 0; i < columns.length; i++) {
			if ((columns[i] < 0) || (columnSize <= columns[i])) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isVisible(final int row, final int column) {

		if (isOutOfBounds(row, column)) {
			return false;
		}
		if ((span[row][column][CellSpan.COLUMN] < 1)
				|| (span[row][column][CellSpan.ROW] < 1)) {
			return false;
		}
		return true;
	}

	@Override
	public void setBackground(final Color color, final int row, final int column) {

		if (isOutOfBounds(row, column)) {
			return;
		}
		background[row][column] = color;
	}

	@Override
	public void setBackground(final Color color, final int[] rows, final int[] columns) {

		if (isOutOfBounds(rows, columns)) {
			return;
		}
		setValues(background, color, rows, columns);
	}

	//

	@Override
	public void setFont(final Font font, final int row, final int column) {

		if (isOutOfBounds(row, column)) {
			return;
		}
		this.font[row][column] = font;
	}

	@Override
	public void setFont(final Font font, final int[] rows, final int[] columns) {

		if (isOutOfBounds(rows, columns)) {
			return;
		}
		setValues(this.font, font, rows, columns);
	}

	//

	@Override
	public void setForeground(final Color color, final int row, final int column) {

		if (isOutOfBounds(row, column)) {
			return;
		}
		foreground[row][column] = color;
	}

	@Override
	public void setForeground(final Color color, final int[] rows, final int[] columns) {

		if (isOutOfBounds(rows, columns)) {
			return;
		}
		setValues(foreground, color, rows, columns);
	}

	@Override
	public void setSize(final Dimension size) {

		columnSize = size.width;
		rowSize = size.height;
		span = new int[rowSize][columnSize][2]; // 2: COLUMN,ROW
		foreground = new Color[rowSize][columnSize];
		background = new Color[rowSize][columnSize];
		font = new Font[rowSize][columnSize];
		initValue();
	}

	/*
	 * public void changeAttribute(int row, int column, Object command) { }
	 * 
	 * public void changeAttribute(int[] rows, int[] columns, Object command) {
	 * }
	 */

	@Override
	public void setSpan(final int[] span, final int row, final int column) {

		if (isOutOfBounds(row, column)) {
			return;
		}
		this.span[row][column] = span;
	}

	protected void setValues(final Object[][] target, final Object value,
			final int[] rows, final int[] columns) {

		for (int i = 0; i < rows.length; i++) {
			final int row = rows[i];
			for (int j = 0; j < columns.length; j++) {
				final int column = columns[j];
				target[row][column] = value;
			}
		}
	}

	@Override
	public void split(final int row, final int column) {

		if (isOutOfBounds(row, column)) {
			return;
		}
		final int columnSpan = span[row][column][CellSpan.COLUMN];
		final int rowSpan = span[row][column][CellSpan.ROW];
		for (int i = 0; i < rowSpan; i++) {
			for (int j = 0; j < columnSpan; j++) {
				span[row + i][column + j][CellSpan.COLUMN] = 1;
				span[row + i][column + j][CellSpan.ROW] = 1;
			}
		}
	}
}
