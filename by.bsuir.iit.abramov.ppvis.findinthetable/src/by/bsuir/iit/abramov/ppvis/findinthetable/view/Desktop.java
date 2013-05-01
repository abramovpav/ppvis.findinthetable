package by.bsuir.iit.abramov.ppvis.findinthetable.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import by.bsuir.iit.abramov.ppvis.findinthetable.table.AttributiveCellRenderer;
import by.bsuir.iit.abramov.ppvis.findinthetable.table.AttributiveCellTableModel;
import by.bsuir.iit.abramov.ppvis.findinthetable.table.CellAttribute;
import by.bsuir.iit.abramov.ppvis.findinthetable.table.CellSpan;
import by.bsuir.iit.abramov.ppvis.findinthetable.table.MultiSpanCellTable;

public class Desktop extends JPanel {
	private final ContentPane	contentPane;
	private CellAttribute		cellAtt;
	private MultiSpanCellTable	table;

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

	public void initialize() {

		setLayout(new BorderLayout(0, 0));
		final AttributiveCellTableModel model = new AttributiveCellTableModel(10, 6);
		cellAtt = model.getCellAttribute();
		table = new MultiSpanCellTable(model);
		table.setCellSelectionEnabled(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setDefaultRenderer(Object.class, new AttributiveCellRenderer());
		final JScrollPane scroll = new JScrollPane(table);
		add(new JScrollPane(scroll), BorderLayout.CENTER);
		combine2FirstColumns();
		int i = 2;
		while (i + 2 <= table.getColumnCount()) {
			combineNCellsInRow(1, i, i + 1);
			i += 2;
		}
		combineCellInExamCaption();

	}

}
