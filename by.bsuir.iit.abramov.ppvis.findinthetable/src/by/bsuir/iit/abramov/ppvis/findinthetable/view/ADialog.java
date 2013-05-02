package by.bsuir.iit.abramov.ppvis.findinthetable.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParsePosition;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class ADialog extends JDialog {

	class Couple {
		public final JTextField	examNameField;
		public final JTextField	examMarkField;
		public final int		num;

		public Couple(final JTextField field1, final JTextField field2, final int num) {

			examMarkField = field2;
			examNameField = field1;
			this.num = num;
		}

		public final JTextField getMarkField() {

			return examMarkField;
		}

		public final JTextField getNameField() {

			return examNameField;
		}

		public final int getNum() {

			return num;
		}
	}

	private static final String	ALL_FIELDS_OF_EXAMS_EMPTY	= "All fields of exams empty";

	private static final String	EXAM						= "Exam ¹";

	public static final String	MARK_SHOULD_BE_0_10			= " mark should be >= 0 && <= 10";

	public static final String	NAME_OR_MARK_ISN_T_CORRECT	= " Name or Mark isn't correct";

	public static final String	EXAM_LABEL					= "exam \u2116";

	public static final String	EXAM_MARK_DEFAULT			= "-1";
	public static final String	EXAM_NAME_DEFAULT			= "name";

	public static boolean isNumeric(final String str) {

		if (str.length() == 0) {
			return false;
		}
		final NumberFormat formatter = NumberFormat.getInstance();
		final ParsePosition pos = new ParsePosition(0);
		formatter.parse(str, pos);
		return str.length() == pos.getIndex();
	}

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {

		try {
			final ADialog dialog = new ADialog();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private final JPanel		contentPanel	= new JPanel();
	private final JTextField	studentNameField;
	private final JTextField	studentGroupField;
	/*
	 * private final JTextField examNameField1; private final JTextField
	 * examMarkField1; private final JTextField examNameField4; private final
	 * JTextField examMarkField4; private final JTextField examNameField5;
	 * private final JTextField examMarkField5; private final JTextField
	 * examNameField2; private final JTextField examMarkField2;
	 * 
	 * private final JTextField examNameField3; private final JTextField
	 * examMarkField3;
	 */
	private final Couple[]		exams;

	private final int			examsCount		= 5;

	/**
	 * Create the dialog.
	 */
	public ADialog() {

		setTitle("Add student");

		setBounds(100, 100, 450, 333);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		final JPanel studentPanel = new JPanel();
		contentPanel.add(studentPanel);

		JLabel lbl = new JLabel("Name");
		studentPanel.add(lbl);

		studentNameField = new JTextField();
		studentPanel.add(studentNameField);
		studentNameField.setColumns(10);

		lbl = new JLabel("Group");
		studentPanel.add(lbl);

		studentGroupField = new JTextField();
		studentPanel.add(studentGroupField);
		studentGroupField.setColumns(10);

		final JPanel examPanel = new JPanel();
		contentPanel.add(examPanel);
		examPanel.setLayout(new BoxLayout(examPanel, BoxLayout.Y_AXIS));

		exams = new Couple[examsCount];
		initExams(examPanel);

		final JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton button = new JButton("ADD");
		buttonPane.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {

				if (verifyStudentFields() && verifyExamsFields()) {
					return;
				}
			}
		});

		button = new JButton("Cancel");
		buttonPane.add(button);

	}

	private void initExams(final JPanel examPanel) {

		JLabel lbl;
		JPanel subExamPanel;

		for (int i = 0; i < exams.length; ++i) {
			exams[i] = new Couple(new JTextField(), new JTextField(), i + 1);
			subExamPanel = new JPanel();
			subExamPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
			examPanel.add(subExamPanel);

			lbl = new JLabel(ADialog.EXAM_LABEL + (i + 1));
			subExamPanel.add(lbl);

			JTextField field = exams[i].getNameField();
			field.setText(ADialog.EXAM_NAME_DEFAULT);
			subExamPanel.add(field);
			field.setColumns(10);

			field = exams[i].getMarkField();
			field.setText(ADialog.EXAM_MARK_DEFAULT);
			subExamPanel.add(field);
			field.setColumns(10);
		}
	}

	private boolean verifyExamFields(final int num, boolean isEmpty) {

		if (num < 0 || num >= examsCount || exams[num] == null) {
			return false;
		}

		final JTextField examNameField = exams[num].getNameField();
		final JTextField examMarkField = exams[num].getMarkField();
		if (examMarkField == null || examNameField == null) {
			return false;
		}
		if ((examNameField.getText().length() != 0 && examMarkField.getText().length() != 0)
				|| (examNameField.getText().equalsIgnoreCase(ADialog.EXAM_NAME_DEFAULT) && examMarkField
						.getText().equalsIgnoreCase(ADialog.EXAM_MARK_DEFAULT))) {
			isEmpty = true;
			return true;
		} else if (examNameField.getText().length() == 0
				|| !ADialog.isNumeric(examMarkField.getText())) {
			JOptionPane.showMessageDialog(null, ADialog.EXAM + (num + 1)
					+ ADialog.NAME_OR_MARK_ISN_T_CORRECT);
			return false;
		} else if (ADialog.isNumeric(examMarkField.getText())) {
			final int mark = Integer.parseInt(examMarkField.getText());
			if (mark < 0 || mark > 10) {
				JOptionPane.showMessageDialog(null, ADialog.EXAM + (num + 1)
						+ ADialog.MARK_SHOULD_BE_0_10);
				return false;
			}
		}

		return true;
	}

	private boolean verifyExamsFields() {

		boolean isEmpty = false;
		boolean isAllFieldsEmpty = true;
		boolean result = true;
		for (int i = 0; i < exams.length; ++i) {
			isEmpty = false;
			if (!verifyExamFields(i, isEmpty)) {
				result = false;
			}
			if (!isEmpty) {
				isAllFieldsEmpty = false;
			}
		}
		if (!isAllFieldsEmpty) {
			return result;
		} else {
			JOptionPane.showMessageDialog(null, ADialog.ALL_FIELDS_OF_EXAMS_EMPTY);
			return false;
		}
	}

	private boolean verifyStudentFields() {

		if (studentNameField.getText().length() == 0
				|| !ADialog.isNumeric(studentGroupField.getText())) {
			JOptionPane.showMessageDialog(null, "Student Name or Group isn't correct");
			return false;
		}
		return true;
	}

}
