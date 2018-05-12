package com.github.mismatch.serializer.units;

import java.nio.ByteBuffer;

public class LongUnit implements Unit {
	private final long value;

	LongUnit(long value) {
		this.value = value;
	}

	@Override
	public void writeTo(ByteBuffer buffer) {
		buffer.put(TypeCode.LONG);
		buffer.putLong(value);
	}

	@Override
	public int length() {
		return Long.BYTES + 1;
	}
}
