package by.bsuir.iit.abramov.ppvis.findinthetable.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import by.bsuir.iit.abramov.ppvis.findinthetable.controller.DialogAddButtonActionListener;
import by.bsuir.iit.abramov.ppvis.findinthetable.controller.DialogCancelButtonActionListener;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.Exam;
import by.bsuir.iit.abramov.ppvis.findinthetable.model.Student;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.Couple;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.Util;

public class ADialog extends JDialog {

	private static final String						STUDENT_NAME_OR_GROUP_ISN_T_CORRECT	= "student_name_or_group_isnt_correct";
	private static final String						TITLE								= "Add student";
	private static final String						LABEL_STUDENT_GROUP					= "group";
	private static final String						LABEL_STUDENT_NAME					= "name";
	private static final String						BUTTON_CANCEL						= "cancel";
	private static final String						BUTTON_ADD							= "add";
	private static final String						ALL_FIELDS_OF_EXAMS_EMPTY			= "all_fields_of_exams_empty";
	private static final String						EXAM_N								= "exam_n";
	public static final String						MARK_SHOULD_BE_0_10					= "mark_should_be_0_10";
	public static final String						NAME_OR_MARK_ISN_T_CORRECT			= "name_or_mark_isn_t_correct";
	public static final String						EXAM_LABEL							= "exam";
	public static final String						EXAM_MARK_DEFAULT					= "-1";
	public static final String						EXAM_NAME_DEFAULT					= "name";

	private final JPanel							contentPanel						= new JPanel();
	private final JTextField						studentNameField;
	private final JTextField						studentGroupField;
	private final Couple<JComboBox, JTextField>[]	exams;
	private final int								examsCount							= 5;
	private Student									student;

	public ADialog() {

		setTitle(ADialog.TITLE);
		setBounds(100, 100, 450, 333);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		final Couple<JTextField, JTextField> studentFields = initStudentPanel();
		studentNameField = studentFields.getField1();
		studentGroupField = studentFields.getField2();
		exams = new Couple[examsCount];

		initExamPanel();
		initButtonPanel();

	}

	public void generateStudent() {

		final Exam[] exams = new Exam[examsCount];
		for (int i = 0; i < examsCount; ++i) {
			final Couple<JComboBox, JTextField> exam = this.exams[i];
			final String name = (String) exam.getField1().getSelectedItem();
			final String mark = exam.getField2().getText();
			if (ADialog.EXAM_MARK_DEFAULT.equalsIgnoreCase(mark) || name.length() == 0
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

		JButton button = new JButton(Window.geti18nString(ADialog.BUTTON_ADD));
		buttonPane.add(button);
		button.addActionListener(new DialogAddButtonActionListener(this));

		button = new JButton(Window.geti18nString(ADialog.BUTTON_CANCEL));
		buttonPane.add(button);
		button.addActionListener(new DialogCancelButtonActionListener(this));
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
			exams[i] = new Couple<JComboBox, JTextField>(new JComboBox(Window.getExams()
					.toArray()), new JTextField(), i + 1);
			exams[i].getField1().setEditable(true);
			exams[i].getField1().setSelectedIndex(-1);
			subExamPanel = new JPanel();
			subExamPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
			examPanel.add(subExamPanel);

			lbl = new JLabel(Window.geti18nString(ADialog.EXAM_LABEL) + (i + 1));
			subExamPanel.add(lbl);

			final JComboBox field = exams[i].getField1();
			subExamPanel.add(field);

			final JTextField field2 = exams[i].getField2();
			field2.setText(ADialog.EXAM_MARK_DEFAULT);
			subExamPanel.add(field2);
			field2.setColumns(10);
		}
	}

	private Couple<JTextField, JTextField> initStudentPanel() {

		final JPanel studentPanel = new JPanel();
		contentPanel.add(studentPanel);

		JLabel lbl = new JLabel(Window.geti18nString(ADialog.LABEL_STUDENT_NAME));
		studentPanel.add(lbl);

		final JTextField studentNameField = new JTextField();
		studentPanel.add(studentNameField);
		studentNameField.setColumns(10);

		lbl = new JLabel(Window.geti18nString(ADialog.LABEL_STUDENT_GROUP));
		studentPanel.add(lbl);

		final JTextField studentGroupField = new JTextField();
		studentPanel.add(studentGroupField);
		studentGroupField.setColumns(10);
		return new Couple<JTextField, JTextField>(studentNameField, studentGroupField, 0);
	}

	private boolean isExamFieldsIncorrect(final JComboBox examNameField,
			final JTextField examMarkField) {

		return ((String) examNameField.getSelectedItem()).length() == 0
				|| !Util.isNumeric(examMarkField.getText());
	}

	private boolean isExamFiledsEmpty(final JComboBox examNameField,
			final JTextField examMarkField) {

		if (examNameField.getSelectedIndex() == -1
				&& examNameField.getSelectedItem() == null) {
			return true;
		}
		return (((String) examNameField.getSelectedItem()).length() == 0 && examMarkField
				.getText().length() == 0)
				|| (ADialog.EXAM_NAME_DEFAULT.equalsIgnoreCase((String) examNameField
						.getSelectedItem()) && ADialog.EXAM_MARK_DEFAULT
						.equalsIgnoreCase(examMarkField.getText()));
	}

	private boolean isMarkIncorrect(final int mark) {

		return mark < 0 || mark > 10;
	}

	private Couple<Boolean, Boolean> verifyExamFields(final int num, Boolean isEmpty) {

		if (num < 0 || num >= examsCount || exams[num] == null) {
			return new Couple<Boolean, Boolean>(false, isEmpty, 0);
		}

		final JComboBox examNameField = exams[num].getField1();
		final JTextField examMarkField = exams[num].getField2();
		if (examMarkField == null || examNameField == null) {
			return new Couple<Boolean, Boolean>(false, isEmpty, 0);
		}
		if (isExamFiledsEmpty(examNameField, examMarkField)) {
			final Boolean b = true;
			isEmpty = b;
			return new Couple<Boolean, Boolean>(true, true, 0);
		} else if (isExamFieldsIncorrect(examNameField, examMarkField)) {
			JOptionPane.showMessageDialog(
					null,
					Window.geti18nString(ADialog.EXAM_N) + (num + 1) + " "
							+ Window.geti18nString(ADialog.NAME_OR_MARK_ISN_T_CORRECT));
			return new Couple<Boolean, Boolean>(false, isEmpty, 0);
		} else if (Util.isNumeric(examMarkField.getText())) {
			final int mark = Integer.parseInt(examMarkField.getText());
			if (isMarkIncorrect(mark)) {
				JOptionPane.showMessageDialog(
						null,
						Window.geti18nString(ADialog.EXAM_N) + (num + 1) + " "
								+ Window.geti18nString(ADialog.MARK_SHOULD_BE_0_10));
				return new Couple<Boolean, Boolean>(false, isEmpty, 0);
			}
		}

		return new Couple<Boolean, Boolean>(true, isEmpty, 0);
	}

	public boolean verifyExamsFields() {

		boolean isAllFieldsEmpty = true;
		boolean result = true;
		for (int i = 0; i < exams.length; ++i) {

			final Couple<Boolean, Boolean> verifyExamFields = verifyExamFields(i, false);
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
			JOptionPane.showMessageDialog(null,
					Window.geti18nString(ADialog.ALL_FIELDS_OF_EXAMS_EMPTY));
			return false;
		}
	}

	public boolean verifyStudentFields() {

		if (studentNameField.getText().length() == 0
				|| !Util.isNumeric(studentGroupField.getText())) {
			JOptionPane.showMessageDialog(null,
					Window.geti18nString(ADialog.STUDENT_NAME_OR_GROUP_ISN_T_CORRECT));
			return false;
		}
		return true;
	}

}
