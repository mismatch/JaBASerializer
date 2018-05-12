package com.github.mismatch.serializer.units;

import java.nio.ByteBuffer;

public class ShortArrayUnit extends PrimitiveArrayUnit  {

	private final short[] shorts;

	ShortArrayUnit(short[] shorts) {
		this.shorts = shorts;
	}

	@Override
	protected void writePayloadTo(ByteBuffer byteBuffer) {
		byteBuffer.asShortBuffer().put(shorts);
	}

	@Override
	protected int getPayloadLength() {
		return shorts.length * Short.BYTES;
	}

	@Override
	protected byte getComponentTypeCode() {
		return TypeCode.SHORT;
	}
}
