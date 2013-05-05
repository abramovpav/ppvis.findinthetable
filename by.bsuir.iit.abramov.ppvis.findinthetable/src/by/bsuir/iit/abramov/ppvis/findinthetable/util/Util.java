package by.bsuir.iit.abramov.ppvis.findinthetable.util;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Vector;

import by.bsuir.iit.abramov.ppvis.findinthetable.model.Student;

public class Util {
	public static Student[] getStudents(final Vector<Student> vector) {

		final Student[] students = new Student[vector.size()];
		for (int i = 0; i < vector.size(); ++i) {
			students[i] = vector.get(i);
		}
		return students;
	}

	public static boolean isNumeric(final String str) {

		if (str.length() == 0) {
			return false;
		}
		final NumberFormat formatter = NumberFormat.getInstance();
		final ParsePosition pos = new ParsePosition(0);
		formatter.parse(str, pos);
		return str.length() == pos.getIndex();
	}
}
