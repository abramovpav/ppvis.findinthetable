package by.bsuir.iit.abramov.ppvis.findinthetable.util;

public enum Fields {
	NAME("name"), GROUP("group"), EXAM("exam"), MARK("mark"), TO("to"), FROM("from");
	private final String	name;

	Fields(final String name) {

		this.name = name;
	}

	public final String getName() {

		return name;
	}
}
