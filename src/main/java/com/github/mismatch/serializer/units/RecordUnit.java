package com.github.mismatch.serializer.units;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.SortedMap;

public class RecordUnit implements Unit {
	private final Map<CharArrayUnit, Unit> fieldUnits;
	private int payloadLength;

	RecordUnit(SortedMap<CharArrayUnit, Unit> fieldUnits) {
		this.fieldUnits = fieldUnits;
	}

	@Override
	public void writeTo(ByteBuffer buffer) {
		buffer.put(TypeCode.RECORD);
		buffer.putInt(payloadLength);
		fieldUnits.forEach((name, data) -> { name.writeTo(buffer); data.writeTo(buffer); });
	}

	@Override
	public int length() {
		payloadLength = fieldUnits.entrySet().stream()
				.mapToInt(e -> e.getKey().length() + e.getValue().length())
				.sum();
		return payloadLength + Integer.BYTES + 1;
	}
}
