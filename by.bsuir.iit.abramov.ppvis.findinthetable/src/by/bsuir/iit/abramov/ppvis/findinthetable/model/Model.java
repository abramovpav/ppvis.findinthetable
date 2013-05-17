package by.bsuir.iit.abramov.ppvis.findinthetable.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JTextField;

import by.bsuir.iit.abramov.ppvis.findinthetable.controller.Controller;
import by.bsuir.iit.abramov.ppvis.findinthetable.util.Util;

public class Model {

	public static final String		PROBLEM_PARSING_THE_FILE			= "problem_parsing_the_file";
	public static final String		ERROR_ERROR_IN_CREATING_DOC			= "error_in_creating_doc";
	public static final String		ERROR_INCORRECT_QUANTITY_IN_EXAMS	= "error_incorrect_quantity";
	public final static String		FIELD_MARK							= "mark";
	public static final String		FIELD_STUDENTS						= "students";
	public static final String		FIELD_STUDENT						= "student";
	public static final String		FIELD_EXAM							= "exam";
	public static final String		FIELD_EXAMS							= "exams";
	public static final String		FIELD_GROUP							= "group";
	public static final String		FIELD_NAME							= "name";
	private static final String		ERROR_FILE_INCORRECT				= "error_file_incorrect";
	private static Logger			LOG									= Logger.getLogger(Model.class
																				.getName());
	private final List<Student>		students;
	private JTextField				observer;
	private JTextField				maxObserver;
	private final List<Controller>	observers;
	public static final int			DEFAULT_VIEWSIZE					= 10;
	private Integer					viewSize							= Model.DEFAULT_VIEWSIZE;
	private int						currPage							= -1;

	public Model() {

		students = new Vector<Student>();
		observers = new ArrayList<Controller>();
	}

	public void addObserver(final Controller observer) {

		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	public void addStudent(final Student student) {

		if (student != null) {
			students.add(student);
		}
		notifyMaxObserver();
	}

	public void deleteStudents(final List<Student> delStudents) {

		for (final Student student : delStudents) {
			if (students.contains(student)) {
				students.remove(student);
			}
		}
		notifyMaxObserver();
		update();
	}

	private final int getCurrPage() {

		return currPage;
	}

	public final List<Student> getCurrPageOfStudent() {

		if (getCurrPage() < 0) {
			resetCurrPage();
		}
		return getPageOfStudents();
	}

	private int getMaxPage() {

		final double max = (double) students.size() / (double) viewSize;
		int result = students.size() / viewSize;
		if (isNeedRounding(max)) {
			result += 1;
		}
		return result;
	}

	public List<Student> getNextPageOfStudents() {

		leafNext();
		return getPageOfStudents();
	}

	private List<Student> getPageOfStudents() {

		final List<Student> pageStudents = new Vector<Student>();
		if (students.size() == 0 || currPage < 0 || currPage >= getMaxPage()) {
			return pageStudents;
		}
		int size = 0;
		if (students.size() - viewSize * currPage < viewSize) {
			size = students.size() - viewSize * currPage;
		} else {
			size = viewSize;
		}
		if (size == 0) {
			return pageStudents;
		}
		for (int i = 0; i < size; ++i) {
			pageStudents.add(students.get(i + viewSize * currPage));
		}
		return pageStudents;
	}

	public List<Student> getPrevPageOfStudents() {

		leafPrev();
		return getPageOfStudents();
	}

	public int getStudentsCount() {

		return students.size();
	}

	public final Integer getViewSize() {

		return viewSize;
	}

	private boolean isNeedRounding(final double max) {

		return max % 2 != 0 && max % 2 != 1;
	}

	public void leafNext() {

		if (currPage < getMaxPage() - 1) {
			currPage++;
		}
	}

	public void leafPrev() {

		if (currPage > 0) {
			currPage--;
		}
	}

	private void notifyMaxObserver() {

		if (maxObserver != null) {
			maxObserver.setText(Integer.toString(students.size()));
		}
	}

	private void notifyObserver() {

		if (observer != null) {
			observer.setText(Integer.toString(viewSize));
		}

	}

	public void openXML(final File file) {

		final XMLReader reader = new XMLReader();
		reader.openXML(file, this);

	}

	public void removeObserver() {

		observer = null;
	}

	public void removeObserver(final Controller observer) {

		if (observers.contains(observer)) {
			observers.remove(observer);
		}
	}

	private void resetCurrPage() {

		currPage = 0;
	}

	public void saveXML(final File file) {

		final XMLWriter xmlWriter = new XMLWriter();
		xmlWriter.saveXML(file, students);
	}

	public Vector<Student> search(final String name, final Integer group) {

		final Vector<Student> studentsVector = new Vector<Student>();
		for (final Student student : students) {
			if (student.getName().indexOf(name) != -1) {
				if (group != null) {
					if (student.getGroup().toString().indexOf(group.toString()) != -1) {
						studentsVector.add(student);
					}
				} else {
					studentsVector.add(student);
				}
			}
		}
		return studentsVector;
	}

	public Vector<Student> search(final String name, final String botStr,
			final String topStr) {

		final Vector<Student> studentsVector = new Vector<Student>();
		final int bot = Util.isNumeric(botStr) ? Integer.parseInt(botStr) : 0;
		final int top = Util.isNumeric(topStr) ? Integer.parseInt(topStr) : 10;
		for (final Student student : students) {
			if (student.getAverageMark() >= bot && student.getAverageMark() <= top) {
				if (student.getName().indexOf(name) != -1) {
					studentsVector.add(student);
				}
			}
		}
		return studentsVector;
	}

	public Vector<Student> search(final String name, final String examStr,
			final String botStr, final String topStr) {

		final Vector<Student> studentsVector = new Vector<Student>();
		final int bot = Util.isNumeric(botStr) ? Integer.parseInt(botStr) : 0;
		final int top = Util.isNumeric(topStr) ? Integer.parseInt(topStr) : 10;
		for (final Student student : students) {
			if (student.isExam(examStr) >= bot && student.isExam(examStr) <= top) {
				if (student.getName().indexOf(name) != -1) {
					studentsVector.add(student);
				}
			}
		}
		return studentsVector;
	}

	public void setMaxObserver(final JTextField observer) {

		maxObserver = observer;
	}

	public void setObserver(final JTextField observer) {

		this.observer = observer;
	}

	public void setStudents(final List<Student> students) {

		this.students.clear();
		this.students.addAll(students);
		notifyMaxObserver();
	}

	public void setViewSize(final Integer viewSize) {

		if (viewSize != null) {
			if (viewSize > 0) {
				this.viewSize = viewSize;
				notifyObserver();
			}
		}
	}

	public void update() {

		final Iterator<Controller> iterator = observers.iterator();
		while (iterator.hasNext()) {
			iterator.next().update();
		}

	}
}
