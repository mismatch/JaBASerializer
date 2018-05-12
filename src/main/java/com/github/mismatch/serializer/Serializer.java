package com.github.mismatch.serializer;

import com.github.mismatch.serializer.units.Unit;
import com.github.mismatch.serializer.units.UnitFactory;

import java.nio.ByteBuffer;

public class Serializer {
	private final UnitFactory unitFactory;

	public Serializer(UnitFactory unitFactory) {
		this.unitFactory = unitFactory;
	}

	public byte[] serialize(Object object) {
		Unit unit = unitFactory.get(object);
		if (null == unit) {
			throw new IllegalArgumentException("Unable to serialize " + object);
		}
		ByteBuffer buffer = ByteBuffer.allocate(unit.length());
		unit.writeTo(buffer);
		return buffer.array();
	}
}
