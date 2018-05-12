package com.github.mismatch.serializer.units;

import java.nio.ByteBuffer;

public class FloatUnit implements Unit {
	private final float value;

	FloatUnit(float value) {
		this.value = value;
	}

	@Override
	public void writeTo(ByteBuffer buffer) {
		buffer.put(TypeCode.FLOAT);
		buffer.putFloat(value);
	}

	@Override
	public int length() {
		return Float.BYTES + 1;
	}
}
