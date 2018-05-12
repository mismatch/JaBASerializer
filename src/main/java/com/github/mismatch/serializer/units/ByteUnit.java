package com.github.mismatch.serializer.units;

import java.nio.ByteBuffer;

public class ByteUnit implements Unit {
	private final byte value;

	ByteUnit(byte value) {
		this.value = value;
	}

	@Override
	public void writeTo(ByteBuffer buffer) {
		buffer.put(TypeCode.BYTE);
		buffer.put(value);
	}

	@Override
	public int length() {
		return 2;
	}
}
