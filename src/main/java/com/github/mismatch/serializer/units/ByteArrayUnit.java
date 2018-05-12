package com.github.mismatch.serializer.units;

import java.nio.ByteBuffer;

public class ByteArrayUnit extends PrimitiveArrayUnit {
	private final byte[] bytes;

	ByteArrayUnit(byte[] bytes) {
		this.bytes = bytes;
	}

	@Override
	protected void writePayloadTo(ByteBuffer byteBuffer) {
		byteBuffer.put(bytes);
	}

	@Override
	protected int getPayloadLength() {
		return bytes.length;
	}

	@Override
	protected byte getComponentTypeCode() {
		return TypeCode.BYTE;
	}
}
