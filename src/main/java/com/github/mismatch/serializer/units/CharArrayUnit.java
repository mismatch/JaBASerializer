package com.github.mismatch.serializer.units;

import java.nio.ByteBuffer;

public class CharArrayUnit extends PrimitiveArrayUnit implements Comparable<CharArrayUnit> {
	private final char[] chars;

	CharArrayUnit(char[] chars) {
		this.chars = chars;
	}

	@Override
	protected void writePayloadTo(ByteBuffer byteBuffer) {
		byteBuffer.asCharBuffer().put(chars);
	}

	@Override
	protected int getPayloadLength() {
		return chars.length * Character.BYTES;
	}

	@Override
	protected byte getComponentTypeCode() {
		return TypeCode.CHAR;
	}

	@Override
	public int compareTo(CharArrayUnit o) {
		return new String(chars).compareTo(new String(o.chars));
	}
}
