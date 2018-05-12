package com.github.mismatch.serializer.units;

import java.nio.ByteBuffer;

public class DoubleUnit implements Unit {
	private final double value;

	DoubleUnit(double value) {
		this.value = value;
	}

	@Override
	public void writeTo(ByteBuffer buffer) {
		buffer.put(TypeCode.DOUBLE);
		buffer.putDouble(value);
	}

	@Override
	public int length() {
		return Double.BYTES + 1;
	}
}
