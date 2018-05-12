package com.github.mismatch.serializer.units;

import java.nio.ByteBuffer;

public class IntUnit implements Unit {
	private final int value;

	IntUnit(int value) {
		this.value = value;
	}

	@Override
	public void writeTo(ByteBuffer buffer) {
		buffer.put(TypeCode.INT);
		buffer.putInt(value);
	}

	@Override
	public int length() {
		return Integer.BYTES + 1;
	}
}
