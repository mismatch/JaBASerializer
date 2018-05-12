package com.github.mismatch.serializer.units;

import java.nio.ByteBuffer;

public class NullUnit implements Unit {
	static final NullUnit INSTANCE = new NullUnit();

	private NullUnit() {}

	@Override
	public void writeTo(ByteBuffer buffer) {
		buffer.put(TypeCode.NULL);
	}

	@Override
	public int length() {
		return 1;
	}
}
