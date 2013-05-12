package by.bsuir.iit.abramov.ppvis.findinthetable.model;

public class Exam {
	private final String	name;
	private final Integer	mark;

	public Exam(final String name, final Integer mark) {

		this.name = name;
		this.mark = mark;
	}

	public Integer getMark() {

		return mark;
	}

	public String getName() {

		return name;
	}

	public boolean isEmpty() {

		return (name == null && mark == null) || "" == name;
	}

}
