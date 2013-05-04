package by.bsuir.iit.abramov.ppvis.findinthetable.model;

import java.util.Vector;

import javax.swing.JTextField;

public class Model {
	private final Vector<Student>	students;
	private JTextField				observer;
	public static final int			DEFAULT_VIEWSIZE	= 10;
	private Integer					viewSize			= Model.DEFAULT_VIEWSIZE;
	private int						currPage			= -1;

	public Model() {

		students = new Vector<Student>();
	}

	public void addStudent(final Student student) {

		if (student != null) {
			students.add(student);
		}
	}

	public final int getCurrPage() {

		return currPage;
	}

	public final Student[] getCurrPageOfStudent() {

		return getPageOfStudents();
	}

	private int getMaxPage() {

		final double max = (double) students.size() / (double) viewSize;
		int result = result = students.size() / viewSize;
		if (max % 2 != 0 && max % 2 != 1) {
			result += 1;
		}
		return result;
	}

	public Student[] getNextPageOfStudents() {

		System.out.println(currPage);
		if (currPage < getMaxPage() - 1) {
			currPage++;
		}
		return getPageOfStudents();
	}

	private Student[] getPageOfStudents() {

		if (students.size() == 0) {
			return null;
		}
		System.out.println(getMaxPage());
		if (currPage < 0 || currPage >= getMaxPage()) {
			return null;
		}
		int size = 0;
		if (students.size() - viewSize * currPage < viewSize) {
			size = students.size() - viewSize * currPage;
		} else {
			size = viewSize;
		}
		if (size == 0) {
			return null;
		}
		System.out.println("size = " + size);
		final Student[] pageStudents = new Student[size];
		for (int i = 0; i < size; ++i) {
			pageStudents[i] = students.get(i + viewSize * currPage);
		}
		return pageStudents;
	}

	public Student[] getPrevPageOfStudents() {

		System.out.println(currPage);
		if (currPage > 0) {
			currPage--;
		}
		return getPageOfStudents();
	}

	public final Integer getViewSize() {

		return viewSize;
	}

	public void notifyObserver() {

		if (observer != null) {
			observer.setText(Integer.toString(viewSize));
		}
	}

	public void removeObserver() {

		observer = null;
	}

	public void resetCurrPage() {

		currPage = 0;
	}

	public void setObserver(final JTextField observer) {

		this.observer = observer;
	}

	public void setViewSize(final Integer viewSize) {

		if (viewSize != null) {
			if (viewSize > 0) {
				this.viewSize = viewSize;
				notifyObserver();
			}
		}
	}

}
