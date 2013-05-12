package by.bsuir.iit.abramov.ppvis.findinthetable.model.table;

/*
 * (swing1.1beta3)
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 * @version 1.0 11/22/98
 */
public class MixedExample extends JFrame {

	class ColorPanel extends JPanel {
		JTable		table;
		ColoredCell	cellAtt;

		ColorPanel(final JTable table, final ColoredCell cellAtt) {

			this.table = table;
			this.cellAtt = cellAtt;
			setLayout(new GridLayout(2, 1));
			setBorder(BorderFactory.createTitledBorder("Color"));
			final JButton b_fore = new JButton("Foreground");
			b_fore.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {

					changeColor(true);
				}
			});
			final JButton b_back = new JButton("Background");
			b_back.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {

					changeColor(false);
				}
			});
			final JPanel p_buttons = new JPanel();
			add(b_fore);
			add(b_back);

		}

		private final void changeColor(final boolean isForeground) {

			final int[] columns = table.getSelectedColumns();
			final int[] rows = table.getSelectedRows();
			if ((rows == null) || (columns == null)) {
				return;
			}
			if ((rows.length < 1) || (columns.length < 1)) {
				return;
			}
			Color target = cellAtt.getForeground(rows[0], columns[0]);
			Color reference = cellAtt.getBackground(rows[0], columns[0]);
			for (int i = 0; i < rows.length; i++) {
				final int row = rows[i];
				for (int j = 0; j < columns.length; j++) {
					final int column = columns[j];
					target = (target != cellAtt.getForeground(row, column)) ? null
							: target;
					reference = (reference != cellAtt.getBackground(row, column)) ? null
							: reference;
				}
			}
			String title;
			if (isForeground) {
				target = (target != null) ? target : table.getForeground();
				reference = (reference != null) ? reference : table.getBackground();
				title = "Foreground Color";
			} else {
				target = (reference != null) ? reference : table.getBackground();
				reference = (target != null) ? target : table.getForeground();
				title = "Foreground Color";
			}
			final TextColorChooser chooser = new TextColorChooser(target, reference,
					isForeground);
			final Color color = chooser.showDialog(MixedExample.this, title);
			if (color != null) {
				if (isForeground) {
					cellAtt.setForeground(color, rows, columns);
				} else {
					cellAtt.setBackground(color, rows, columns);
				}
				table.clearSelection();
				table.revalidate();
				table.repaint();
			}
		}
	}

	class FontPanel extends JPanel {
		String[]	str_size	= { "10", "12", "14", "16", "20" };
		String[]	str_style	= { "PLAIN", "BOLD", "ITALIC" };
		JComboBox	name, style, size;

		FontPanel(final JTable table, final CellFont cellAtt) {

			setLayout(new BorderLayout());
			setBorder(BorderFactory.createTitledBorder("Font"));
			final Box box = new Box(BoxLayout.X_AXIS);
			final JPanel p2 = new JPanel(new GridLayout(3, 1));
			final JPanel p3 = new JPanel(new GridLayout(3, 1));
			final JPanel p4 = new JPanel(new BorderLayout());
			p2.add(new JLabel("Name:"));
			p2.add(new JLabel("Style:"));
			p2.add(new JLabel("Size:"));
			final Toolkit toolkit = Toolkit.getDefaultToolkit();
			name = new JComboBox(toolkit.getFontList());
			style = new JComboBox(str_style);
			size = new JComboBox(str_size);
			size.setEditable(true);
			final JButton b_apply = new JButton("Apply");
			b_apply.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {

					final int[] columns = table.getSelectedColumns();
					final int[] rows = table.getSelectedRows();
					if ((rows == null) || (columns == null)) {
						return;
					}
					if ((rows.length < 1) || (columns.length < 1)) {
						return;
					}
					final Font font = new Font((String) name.getSelectedItem(), style
							.getSelectedIndex(), Integer.parseInt((String) size
							.getSelectedItem()));
					cellAtt.setFont(font, rows, columns);
					table.clearSelection();
					table.revalidate();
					table.repaint();
				}
			});
			p3.add(name);
			p3.add(style);
			p3.add(size);
			p4.add(BorderLayout.CENTER, b_apply);
			box.add(p2);
			box.add(p3);
			add(BorderLayout.CENTER, box);
			add(BorderLayout.SOUTH, p4);
		}
	}

	class SpanPanel extends JPanel {
		JTable		table;
		CellSpan	cellAtt;

		SpanPanel(final JTable table, final CellSpan cellAtt) {

			this.table = table;
			this.cellAtt = cellAtt;
			setLayout(new GridLayout(2, 1));
			setBorder(BorderFactory.createTitledBorder("Span"));
			final JButton b_one = new JButton("Combine");
			b_one.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {

					final int[] columns = table.getSelectedColumns();
					final int[] rows = table.getSelectedRows();
					cellAtt.combine(rows, columns);
					table.clearSelection();
					table.revalidate();
					table.repaint();
				}
			});
			final JButton b_split = new JButton("Split");
			b_split.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(final ActionEvent e) {

					final int column = table.getSelectedColumn();
					final int row = table.getSelectedRow();
					cellAtt.split(row, column);
					table.clearSelection();
					table.revalidate();
					table.repaint();
				}
			});
			add(b_one);
			add(b_split);
		}
	}

	public static void main(final String[] args) {

		final MixedExample frame = new MixedExample();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {

				System.exit(0);
			}
		});
		frame.setVisible(true);
	}

	public MixedExample() {

		/*
		 * super("Mixed Example"); final Student[] students = { new
		 * Student("Abramov", new Integer(9), new Exam( "Math", 9)) }; final
		 * AttributiveCellTableModel ml = new AttributiveCellTableModel(5,
		 * students); final CellAttribute cellAtt = ml.getCellAttribute(); final
		 * MultiSpanCellTable table = new MultiSpanCellTable(ml);
		 * table.setCellSelectionEnabled(true);
		 * table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		 * table.setDefaultRenderer(Object.class, new
		 * AttributiveCellRenderer()); final JScrollPane scroll = new
		 * JScrollPane(table);
		 * 
		 * final ColorPanel colorPanel = new ColorPanel(table, (ColoredCell)
		 * cellAtt); final FontPanel fontPanel = new FontPanel(table, (CellFont)
		 * cellAtt); final SpanPanel spanPanel = new SpanPanel(table, (CellSpan)
		 * cellAtt); final Box boxAtt = new Box(BoxLayout.Y_AXIS);
		 * boxAtt.add(colorPanel); boxAtt.add(fontPanel); boxAtt.add(spanPanel);
		 * 
		 * final Box box = new Box(BoxLayout.X_AXIS); box.add(scroll);
		 * box.add(new JSeparator(SwingConstants.HORIZONTAL)); box.add(boxAtt);
		 * getContentPane().add(box); setSize(400, 300); setVisible(true);
		 */
	}
}
