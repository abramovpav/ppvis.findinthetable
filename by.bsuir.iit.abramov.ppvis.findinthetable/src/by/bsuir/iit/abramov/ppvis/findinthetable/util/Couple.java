package by.bsuir.iit.abramov.ppvis.findinthetable.util;

public class Couple<T, D> {
	private final T		field1;
	private final D		field2;
	private final int	num;

	public Couple(final T field1, final D field2, final int num) {

		this.field2 = field2;
		this.field1 = field1;
		this.num = num;
	}

	public final T getField1() {

		return field1;
	}

	public final D getField2() {

		return field2;
	}

	public final int getNum() {

		return num;
	}
}
