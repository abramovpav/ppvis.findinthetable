package by.bsuir.iit.abramov.ppvis.findinthetable.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class AddDialog extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {

		try {
			final AddDialog dialog = new AddDialog();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private final JPanel		contentPanel	= new JPanel();
	private final JTextField	textField;
	private final JTextField	fromField;
	private final JTextField	toField;

	private final JTable		table;
	public static final int		defaultX		= 100;
	public static final int		defaultY		= 100;
	public static final int		defaultWidth	= 450;
	public static final int		defaultHeight	= 270;

	/**
	 * Create the dialog.
	 */
	public AddDialog() {

		setBounds(AddDialog.defaultX, AddDialog.defaultY, AddDialog.defaultWidth,
				AddDialog.defaultHeight);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));

		final JPanel panel = new JPanel();
		contentPanel.add(panel);

		final JLabel label;
		createLabel(panel, "First Name:");

		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);

		final JPanel panel2 = new JPanel();
		panel.add(panel2);

		createLabel(panel2, "from");

		fromField = new JTextField();
		panel2.add(fromField);
		fromField.setColumns(2);

		createLabel(panel2, "to");

		toField = new JTextField();
		panel2.add(toField);
		toField.setColumns(2);

		final JScrollPane scrollPane = new JScrollPane();
		contentPanel.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "FIO",
				"\u0421\u0440. \u0431\u0430\u043B\u043B" }));

		TableColumn column = table.getColumnModel().getColumn(0);
		column.setPreferredWidth(101);
		column.setMinWidth(101);
		column = table.getColumnModel().getColumn(1);
		column.setResizable(false);
		column.setPreferredWidth(61);
		column.setMinWidth(61);
		column.setMaxWidth(61);
		scrollPane.setViewportView(table);

		final JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		final JButton okButton = new JButton("Search");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		final JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

	}

	private void createLabel(final JPanel panel, final String text) {

		final JLabel label = new JLabel(text);
		panel.add(label);
	}

}
