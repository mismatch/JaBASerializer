package com.github.mismatch.serializer.units;

import java.nio.ByteBuffer;
import java.util.Collection;

public class ArrayUnit implements Unit {

	private final Collection<Unit> arrayUnits;
	private int payloadLength;

	ArrayUnit(Collection<Unit> arrayUnits) {
		this.arrayUnits = arrayUnits;
	}

	@Override
	public void writeTo(ByteBuffer buffer) {
		buffer.put((byte) (TypeCode.ARRAY + TypeCode.RECORD));
		buffer.putInt(payloadLength);
		arrayUnits.forEach(u -> u.writeTo(buffer));
	}

	@Override
	public int length() {
		payloadLength = arrayUnits.stream().mapToInt(Unit::length).sum();
		return payloadLength + Integer.BYTES + 1;
	}
}
