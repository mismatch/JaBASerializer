package com.github.mismatch.serializer.aux;

import com.github.mismatch.serializer.units.TypeCode;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// Incomplete implementation. For tests only
public class Deserializer {

	public Object deserialize(byte[] bytes, Class klass) {
		return readValue(bytes, 0, klass).object;
	}

	private DeserializationUnit readRecord(byte[] bytes, int offset, Class klass)
			throws NoSuchFieldException, IllegalAccessException, InstantiationException {
		Object instance = klass.newInstance();
		int recordSize = ByteBuffer.wrap(bytes, offset, Integer.BYTES).getInt();
		int recordStartPos = offset + Integer.BYTES;
		int currentPos = recordStartPos;
		while (currentPos < recordStartPos + recordSize) {
			DeserializationUnit nameObject = readValue(bytes, currentPos, char[].class);
			String fieldName = new String((char[]) nameObject.object);
			currentPos = nameObject.nextOffset;

			Field field = klass.getDeclaredField(fieldName);
			field.setAccessible(true);
			DeserializationUnit valueObject = readValue(bytes, currentPos, field.getType());
			if (null != valueObject.object) {
				field.set(instance, valueObject.object);
			}
			currentPos = valueObject.nextOffset;
		}

		return new DeserializationUnit(currentPos, instance);
	}

	private DeserializationUnit readCharArrayUnit(byte[] bytes, int offset) {
		int arrayLength = ByteBuffer.wrap(bytes, offset, Integer.BYTES).getInt();
		int nextOffset = offset + Integer.BYTES;

		byte[] arrayBytes = new byte[arrayLength];
		System.arraycopy(bytes, nextOffset, arrayBytes, 0, arrayLength);
		nextOffset += arrayLength;

		char[] chars = StandardCharsets.UTF_16.decode(ByteBuffer.wrap(arrayBytes)).array();
		return new DeserializationUnit(nextOffset, chars);
	}

	private DeserializationUnit readRecordArrayUnit(byte[] bytes, int offset, Class componentType) {
		int arrayLength = ByteBuffer.wrap(bytes, offset, Integer.BYTES).getInt();
		int arrayStartPos = offset + Integer.BYTES;
		int currentPos = arrayStartPos;
		List<Object> objects = new ArrayList<>();
		while (currentPos < arrayStartPos + arrayLength) {
			DeserializationUnit unit = readValue(bytes, currentPos, componentType);
			currentPos = unit.nextOffset;
			objects.add(unit.object);
		}
		return new DeserializationUnit(currentPos, objects.toArray());
	}

	private DeserializationUnit readValue(byte[] bytes, int offset, Class klass) {
		byte typeCode = bytes[offset];
		int nextOffset = offset + 1;
		switch (typeCode) {
			case TypeCode.NULL:
				return new DeserializationUnit(nextOffset, null);
			case TypeCode.BYTE:
				byte b = bytes[nextOffset];
				nextOffset += 1;
				return new DeserializationUnit(nextOffset, b);
			case TypeCode.SHORT:
				short s = ByteBuffer.wrap(bytes, nextOffset, Short.BYTES).getShort();
				nextOffset += Short.BYTES;
				return new DeserializationUnit(nextOffset, s);
			case TypeCode.INT:
				int i = ByteBuffer.wrap(bytes, nextOffset, Integer.BYTES).getInt();
				nextOffset += Integer.BYTES;
				return new DeserializationUnit(nextOffset, i);
			case TypeCode.LONG:
				long l = ByteBuffer.wrap(bytes, nextOffset, Long.BYTES).getLong();
				nextOffset += Long.BYTES;
				return new DeserializationUnit(nextOffset, l);
			case TypeCode.ARRAY + TypeCode.CHAR:
				return readCharArrayUnit(bytes, nextOffset);
			case TypeCode.ARRAY + TypeCode.RECORD:
				return readRecordArrayUnit(bytes, nextOffset, klass.getComponentType());
			case TypeCode.RECORD:
				try {
					return readRecord(bytes, nextOffset, klass);
				} catch (NoSuchFieldException | IllegalAccessException | InstantiationException e) {
					throw new DeserializationException(e);
				}
			default:
				throw new DeserializationException("Unable to deserialize bytes to object of class " + klass);
		}
	}


	static class DeserializationUnit {
		private final int nextOffset;
		private final Object object;

		public DeserializationUnit(int nextOffset, Object object) {
			this.nextOffset = nextOffset;
			this.object = object;
		}
	}
}
