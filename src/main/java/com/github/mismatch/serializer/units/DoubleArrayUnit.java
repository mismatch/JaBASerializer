package com.github.mismatch.serializer.units;

import java.nio.ByteBuffer;

public class DoubleArrayUnit extends PrimitiveArrayUnit {
	private final double[] doubles;

	DoubleArrayUnit(double[] doubles) {
		this.doubles = doubles;
	}

	@Override
	protected void writePayloadTo(ByteBuffer byteBuffer) {
		byteBuffer.asDoubleBuffer().put(doubles);
	}

	@Override
	protected int getPayloadLength() {
		return doubles.length * Double.BYTES;
	}

	@Override
	protected byte getComponentTypeCode() {
		return TypeCode.DOUBLE;
	}
}
