package by.bsuir.iit.abramov.ppvis.findinthetable.model;

import java.util.ArrayList;
import java.util.List;

public class Student {
	private final String		name;
	private final Integer		group;
	private final List<Exam>	exams;

	public Student(final String name, final Integer group, final Exam... exams) {

		this.name = name;
		this.group = group;
		this.exams = new ArrayList<Exam>();
		for (final Exam exam : exams) {
			this.exams.add(exam);
		}
	}

	public double getAverageMark() {

		double result = 0;
		int counter = 0;
		for (final Exam exam : exams) {
			if (exam.getMark() != null) {
				result += exam.getMark();
				counter++;
			}
		}
		result = result / counter;
		return result;
	}

	public final List<Exam> getExams() {

		return exams;
	}

	public final Exam getExams(final int num) {

		return num < exams.size() && num >= 0 ? exams.get(num) : null;
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
		for (final Exam exam : exams) {
			if (exam.getName() != null) {
				if (exam.getName().indexOf(examName) != -1) {
					result += exam.getMark() != null ? exam.getMark() : 0;
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
