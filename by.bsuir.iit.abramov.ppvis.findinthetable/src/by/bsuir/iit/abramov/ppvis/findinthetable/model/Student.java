package by.bsuir.iit.abramov.ppvis.findinthetable.model;

public class Student {
	private final String	name;
	private final Integer	group;
	private final Exam[]	exams;

	public Student(final String name, final Integer group, final Exam... exams) {

		this.name = name;
		this.group = group;
		this.exams = exams;
	}

	public final Exam[] getExams() {

		return exams;
	}

	public final Exam getExams(final int num) {

		return exams[num];
	}

	public final Integer getGroup() {

		return group;
	}

	public final String getName() {

		return name;
	}
}
