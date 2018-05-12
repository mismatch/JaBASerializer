package com.github.mismatch.serializer.units;

import java.nio.ByteBuffer;

public class CharUnit implements Unit {
	private final char value;

	CharUnit(char value) {
		this.value = value;
	}

	@Override
	public void writeTo(ByteBuffer buffer) {
		buffer.put(TypeCode.CHAR);
		buffer.putChar(value);
	}

	@Override
	public int length() {
		return Character.BYTES + 1;
	}
}
