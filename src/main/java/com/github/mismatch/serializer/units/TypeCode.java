package com.github.mismatch.serializer.units;

public class TypeCode {
	public static final byte NULL = 0;
	public static final byte BYTE = 1;
	public static final byte SHORT = 2;
	public static final byte INT = 3;
	public static final byte LONG = 4;
	public static final byte FLOAT = 5;
	public static final byte DOUBLE = 6;
	public static final byte CHAR = 7;
	public static final byte RECORD = 8;

	// reserve 10-20 for array types
	// array type code is ARRAY + one of mentioned above, e.g. typecode(char[]) = 10 + 7 = 17
	public static final byte ARRAY = 10;
}
