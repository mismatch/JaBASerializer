package com.github.mismatch.serializer.units;

import java.nio.ByteBuffer;

public class IntArrayUnit extends PrimitiveArrayUnit {

	private final int[] ints;

	IntArrayUnit(int[] ints) {
		this.ints = ints;
	}

	@Override
	protected void writePayloadTo(ByteBuffer byteBuffer) {
		byteBuffer.asIntBuffer().put(ints);
	}

	@Override
	protected int getPayloadLength() {
		return ints.length * Integer.BYTES;
	}

	@Override
	protected byte getComponentTypeCode() {
		return TypeCode.INT;
	}
}
