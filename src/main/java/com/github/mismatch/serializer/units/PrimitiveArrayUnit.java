package com.github.mismatch.serializer.units;

import java.nio.ByteBuffer;

public abstract class PrimitiveArrayUnit implements Unit {
	@Override
	public void writeTo(ByteBuffer buffer) {
		int payloadLength = getPayloadLength();

		buffer.put((byte) (TypeCode.ARRAY + getComponentTypeCode()));
		buffer.putInt(payloadLength);

		if (payloadLength == 0) {
			return;
		}

		ByteBuffer byteBuffer = ByteBuffer.allocate(payloadLength);
		writePayloadTo(byteBuffer);
		buffer.put(byteBuffer.array());
	}

	@Override
	public int length() {
		return getPayloadLength() + Integer.BYTES + 1;
	}

	protected abstract void writePayloadTo(ByteBuffer byteBuffer);
	protected abstract int getPayloadLength();
	protected abstract byte getComponentTypeCode();
}
