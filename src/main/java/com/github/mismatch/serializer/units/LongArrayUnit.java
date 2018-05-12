package com.github.mismatch.serializer.units;

import java.nio.ByteBuffer;

public class LongArrayUnit extends PrimitiveArrayUnit {
	private final long[] longs;

	LongArrayUnit(long[] longs) {
		this.longs = longs;
	}

	@Override
	protected void writePayloadTo(ByteBuffer byteBuffer) {
		byteBuffer.asLongBuffer().put(longs);
	}

	@Override
	protected int getPayloadLength() {
		return longs.length * Long.BYTES;
	}

	@Override
	protected byte getComponentTypeCode() {
		return TypeCode.LONG;
	}
}
