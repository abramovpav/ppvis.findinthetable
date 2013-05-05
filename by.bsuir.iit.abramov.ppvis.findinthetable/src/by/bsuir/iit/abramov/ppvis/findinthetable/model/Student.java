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

	public double getAverageMark() {

		double result = 0;
		int counter = 0;
		for (int i = 0; i < exams.length; ++i) {
			if (exams[i].getMark() != null) {
				result += exams[i].getMark();
				counter++;
			}
		}
		result = result / counter;
		return result;
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

	public double isExam(final String examName) {

		double result = -1;
		double counter = 0;
		for (int i = 0; i < exams.length; ++i) {
			if (exams[i].getName() != null) {
				if (exams[i].getName().indexOf(examName) != -1) {
					result += exams[i].getMark() != null ? exams[i].getMark() : 0;
					counter++;
				}
			}
		}
		if (counter > 0) {
			result += 1;
			result = result / counter;
		}
		return result;
	}
}
