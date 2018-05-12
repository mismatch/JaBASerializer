package com.github.mismatch.serializer.units;

import java.nio.ByteBuffer;

public class FloatArrayUnit extends PrimitiveArrayUnit {
	private final float[] floats;

	FloatArrayUnit(float[] floats) {
		this.floats = floats;
	}

	@Override
	protected void writePayloadTo(ByteBuffer byteBuffer) {
		byteBuffer.asFloatBuffer().put(floats);
	}

	@Override
	protected int getPayloadLength() {
		return floats.length * Float.BYTES;
	}

	@Override
	protected byte getComponentTypeCode() {
		return TypeCode.FLOAT;
	}
}
