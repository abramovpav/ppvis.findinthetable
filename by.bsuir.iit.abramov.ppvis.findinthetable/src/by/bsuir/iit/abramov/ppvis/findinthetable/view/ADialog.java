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
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Exam;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.Student;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.Couple;

public class ADialog extends JDialog {

	private static final String	TITLE						= "Add student";
	private static final String	LABEL_STUDENT_GROUP			= "Group";
	private static final String	LABEL_STUDENT_NAME			= "Name";
	private static final String	BUTTON_CANCEL				= "Cancel";
	private static final String	BUTTON_ADD					= "ADD";
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

	private final JPanel				contentPanel	= new JPanel();
	private final JTextField			studentNameField;
	private final JTextField			studentGroupField;
	private final Couple<JTextField>[]	exams;
	private final int					examsCount		= 5;
	private Student						student;

	public ADialog() {

		setTitle(ADialog.TITLE);

		setBounds(100, 100, 450, 333);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		final Couple<JTextField> studentFields = initStudentPanel();
		studentNameField = studentFields.getField1();
		studentGroupField = studentFields.getField2();
		exams = new Couple[examsCount];

		initExamPanel();
		initButtonPanel();

	}

	public void generateStudent() {

		final Exam[] exams = new Exam[examsCount];
		for (int i = 0; i < examsCount; ++i) {
			final Couple<JTextField> exam = this.exams[i];
			final String name = exam.getField1().getText();
			final String mark = exam.getField2().getText();
			if (mark.equalsIgnoreCase(ADialog.EXAM_MARK_DEFAULT) || name.length() == 0
					|| mark.length() == 0) {
				exams[i] = new Exam(null, null);
			} else {
				exams[i] = new Exam(name, Integer.parseInt(mark));
			}
		}
		student = new Student(studentNameField.getText(),
				Integer.parseInt(studentGroupField.getText()), exams);
	}

	public Student getStudent() {

		return student;
	}

	private void initButtonPanel() {

		final JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton button = new JButton(ADialog.BUTTON_ADD);
		buttonPane.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {

				if (verifyStudentFields() && verifyExamsFields()) {
					generateStudent();
					ADialog.this.setVisible(false);
					ADialog.this.dispose();

				}
			}
		});

		button = new JButton(ADialog.BUTTON_CANCEL);
		buttonPane.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {

				student = null;
				ADialog.this.setVisible(false);
				ADialog.this.dispose();
			}
		});
	}

	private void initExamPanel() {

		final JPanel examPanel = new JPanel();
		contentPanel.add(examPanel);
		examPanel.setLayout(new BoxLayout(examPanel, BoxLayout.Y_AXIS));

		initExams(examPanel);
	}

	private void initExams(final JPanel examPanel) {

		JLabel lbl;
		JPanel subExamPanel;

		for (int i = 0; i < exams.length; ++i) {
			exams[i] = new Couple<JTextField>(new JTextField(), new JTextField(), i + 1);
			subExamPanel = new JPanel();
			subExamPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
			examPanel.add(subExamPanel);

			lbl = new JLabel(ADialog.EXAM_LABEL + (i + 1));
			subExamPanel.add(lbl);

			JTextField field = exams[i].getField1();
			field.setText(ADialog.EXAM_NAME_DEFAULT);
			subExamPanel.add(field);
			field.setColumns(10);

			field = exams[i].getField2();
			field.setText(ADialog.EXAM_MARK_DEFAULT);
			subExamPanel.add(field);
			field.setColumns(10);
		}
	}

	private Couple<JTextField> initStudentPanel() {

		final JPanel studentPanel = new JPanel();
		contentPanel.add(studentPanel);

		JLabel lbl = new JLabel(ADialog.LABEL_STUDENT_NAME);
		studentPanel.add(lbl);

		final JTextField studentNameField = new JTextField();
		studentPanel.add(studentNameField);
		studentNameField.setColumns(10);

		lbl = new JLabel(ADialog.LABEL_STUDENT_GROUP);
		studentPanel.add(lbl);

		final JTextField studentGroupField = new JTextField();
		studentPanel.add(studentGroupField);
		studentGroupField.setColumns(10);
		return new Couple<JTextField>(studentNameField, studentGroupField, 0);
	}

	private boolean isExamFieldsIncorrect(final JTextField examNameField,
			final JTextField examMarkField) {

		return examNameField.getText().length() == 0
				|| !ADialog.isNumeric(examMarkField.getText());
	}

	private boolean isExamFiledsEmpty(final JTextField examNameField,
			final JTextField examMarkField) {

		return (examNameField.getText().length() == 0 && examMarkField.getText().length() == 0)
				|| (examNameField.getText().equalsIgnoreCase(ADialog.EXAM_NAME_DEFAULT) && examMarkField
						.getText().equalsIgnoreCase(ADialog.EXAM_MARK_DEFAULT));
	}

	private boolean isMarkIncorrect(final int mark) {

		return mark < 0 || mark > 10;
	}

	private Couple<Boolean> verifyExamFields(final int num, Boolean isEmpty) {

		if (num < 0 || num >= examsCount || exams[num] == null) {
			return new Couple<Boolean>(false, isEmpty, 0);
		}

		final JTextField examNameField = exams[num].getField1();
		final JTextField examMarkField = exams[num].getField2();
		if (examMarkField == null || examNameField == null) {
			return new Couple<Boolean>(false, isEmpty, 0);
		}
		if (isExamFiledsEmpty(examNameField, examMarkField)) {
			final Boolean b = true;
			isEmpty = b;
			return new Couple<Boolean>(true, true, 0);
		} else if (isExamFieldsIncorrect(examNameField, examMarkField)) {
			JOptionPane.showMessageDialog(null, ADialog.EXAM + (num + 1)
					+ ADialog.NAME_OR_MARK_ISN_T_CORRECT);
			return new Couple<Boolean>(false, isEmpty, 0);
		} else if (ADialog.isNumeric(examMarkField.getText())) {
			final int mark = Integer.parseInt(examMarkField.getText());
			if (isMarkIncorrect(mark)) {
				JOptionPane.showMessageDialog(null, ADialog.EXAM + (num + 1)
						+ ADialog.MARK_SHOULD_BE_0_10);
				return new Couple<Boolean>(false, isEmpty, 0);
			}
		}

		return new Couple<Boolean>(true, isEmpty, 0);
	}

	private boolean verifyExamsFields() {

		boolean isAllFieldsEmpty = true;
		boolean result = true;
		for (int i = 0; i < exams.length; ++i) {

			final Couple<Boolean> verifyExamFields = verifyExamFields(i, false);
			if (!verifyExamFields.getField1()) {
				result = false;
			}
			if (!verifyExamFields.getField2()) {
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
