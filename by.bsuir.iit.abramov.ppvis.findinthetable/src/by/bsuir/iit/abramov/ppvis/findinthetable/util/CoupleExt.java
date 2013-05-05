package by.bsuir.iit.abramov.ppvis.findinthetable.util;

public class CoupleExt<T, D> {
	private T	field1;
	private D	field2;

	public CoupleExt(final T field1, final D field2) {

		this.field1 = field1;
		this.field2 = field2;
	}

	public T getField1() {

		return field1;
	}

	public D getField2() {

		return field2;
	}

	public void setField1(final T field1) {

		this.field1 = field1;
	}

	public void setField2(final D field2) {

		this.field2 = field2;
	}
}
