package by.bsuir.iit.abramov.ppvis.findinthetable.util;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParsePosition;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import by.bsuir.iit.abramov.ppvis.findinthetable.controller.ViewSizeButtonListener;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.Model;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.table.CellAttribute;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.table.CellSpan;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.table.MultiSpanCellTable;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.ButtonPanel;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Desktop;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.FindDialog;
import by.bsuir.iit.abramov.ppvis.findinthetable.view.Window;

public class Util {

	public static void combine(final CellAttribute cellAtt,
			final MultiSpanCellTable table, final int[] columns, final int[] rows) {

		((CellSpan) cellAtt).combine(rows, columns);
		table.clearSelection();
		table.revalidate();
		table.repaint();
	}

	public static void combine2FirstColumns(final MultiSpanCellTable table,
			final CellAttribute cellAtt) {

		int i = 0;
		while (i + 2 <= table.getRowCount()) {
			Util.combineNCellsInColumn(table, cellAtt, 0, i, i + 1);
			Util.combineNCellsInColumn(table, cellAtt, 1, i, i + 1);
			i += 2;
		}
	}

	public static void combineCellInExamCaption(final MultiSpanCellTable table,
			final CellAttribute cellAtt) {

		final int[] rows = { 0 };
		final int[] columns = new int[table.getColumnCount() - 2];
		for (int i = 0; i < columns.length; ++i) {
			columns[i] = 2 + i;
		}
		Util.combine(cellAtt, table, columns, rows);
	}

	public static void combineNCellsInColumn(final MultiSpanCellTable table,
			final CellAttribute cellAtt, final int i, final int... rows) {

		final int[] columns = { i };
		Util.combine(cellAtt, table, columns, rows);
	}

	public static void combineNCellsInRow(final MultiSpanCellTable table,
			final CellAttribute cellAtt, final int i, final int... columns) {

		final int[] rows = { i };
		Util.combine(cellAtt, table, columns, rows);
	}

	public static ButtonPanel createButtonPanel(final Model model, final Desktop desktop,
			final ActionListener listener) {

		final ButtonPanel buttonPanel = Util.createButtons(desktop, listener);
		Util.createViewSizePanel(buttonPanel, model, desktop);
		return buttonPanel;
	}

	public static void createButtonPanel(final Model model, final FindDialog findDialog,
			final JPanel mainPanel, final ActionListener listener) {

		final ButtonPanel buttonPanel = Util.createButtons(mainPanel, listener);
		Util.createViewSizePanel(buttonPanel, model, findDialog);
	}

	private static ButtonPanel createButtons(final JPanel mainPanel,
			final ActionListener listener) {

		final ButtonPanel buttonPanel = new ButtonPanel();
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new BorderLayout(0, 0));

		JButton button = new JButton(Window.geti18nString(Desktop.BUTTON_PREV));
		buttonPanel.add(button, BorderLayout.WEST);
		button.addActionListener(listener);

		buttonPanel.addButton(Desktop.BUTTON_PREV, button);

		button = new JButton(Window.geti18nString(Desktop.BUTTON_NEXT));
		button.addActionListener(listener);
		buttonPanel.add(button, BorderLayout.EAST);
		buttonPanel.addButton(Desktop.BUTTON_NEXT, button);
		return buttonPanel;
	}

	private static JPanel createViewPanel(final ButtonPanel buttonPanel, final Model model) {

		final JPanel viewSizePanel = new JPanel();
		buttonPanel.add(viewSizePanel, BorderLayout.CENTER);
		viewSizePanel.setLayout(new BorderLayout(0, 0));
		final JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JTextField text = new JTextField(Integer.toString(model.getViewSize()));
		panel.add(text);
		viewSizePanel.add(panel, BorderLayout.CENTER);
		text.setEditable(false);
		model.setObserver(text);

		final JLabel label = new JLabel(Window.geti18nString(Desktop.MAX));
		buttonPanel.setLabel(label);
		panel.add(label);
		text = new JTextField(Integer.toString(model.getStudentsCount()));
		text.setEditable(false);
		panel.add(text);
		model.setMaxObserver(text);
		return viewSizePanel;
	}

	public static void createViewSizePanel(final ButtonPanel buttonPanel,
			final Model model, final Desktop desktop) {

		JButton button;
		final JPanel viewSizePanel = Util.createViewPanel(buttonPanel, model);

		button = new JButton(Desktop.DECREMENT);
		viewSizePanel.add(button, BorderLayout.WEST);
		button.addActionListener(new ViewSizeButtonListener(model, desktop,
				Desktop.DECREMENT));
		button = new JButton(Desktop.INCREMENT);
		button.addActionListener(new ViewSizeButtonListener(model, desktop,
				Desktop.INCREMENT));
		viewSizePanel.add(button, BorderLayout.EAST);
	}

	public static void createViewSizePanel(final ButtonPanel buttonPanel,
			final Model model, final FindDialog findDialog) {

		JButton button;
		final JPanel viewSizePanel = Util.createViewPanel(buttonPanel, model);

		button = new JButton(Desktop.DECREMENT);
		viewSizePanel.add(button, BorderLayout.WEST);
		button.addActionListener(new ViewSizeButtonListener(model, findDialog,
				Desktop.DECREMENT));
		button = new JButton(Desktop.INCREMENT);
		button.addActionListener(new ViewSizeButtonListener(model, findDialog,
				Desktop.INCREMENT));
		viewSizePanel.add(button, BorderLayout.EAST);
	}

	public static boolean isNumeric(final String str) {

		if (str.length() == 0) {
			return false;
		}
		final NumberFormat formatter = NumberFormat.getInstance();
		final ParsePosition pos = new ParsePosition(0);
		formatter.parse(str, pos);
		return str.length() == pos.getIndex();
	}
}
