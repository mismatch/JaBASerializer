package com.github.mismatch.serializer.units;

import java.nio.ByteBuffer;

public class ShortUnit implements Unit {

	private final short value;

	ShortUnit(short value) {
		this.value = value;
	}

	@Override
	public void writeTo(ByteBuffer buffer) {
		buffer.put(TypeCode.SHORT);
		buffer.putShort(value);
	}

	@Override
	public int length() {
		return Short.BYTES + 1;
	}
}
