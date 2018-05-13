package com.github.mismatch.serializer.aux;

import java.util.Objects;

public class SimpleTestClass {
	private int a;
	private byte bb;

	public SimpleTestClass() {

	}

	public SimpleTestClass(int a, byte bb) {
		this.a = a;
		this.bb = bb;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SimpleTestClass that = (SimpleTestClass) o;
		return a == that.a && bb == that.bb;
	}

	@Override
	public int hashCode() {
		return Objects.hash(a, bb);
	}
}
