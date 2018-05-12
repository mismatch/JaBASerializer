package com.github.mismatch.serializer.units;

import java.nio.ByteBuffer;

public interface Unit {
	void writeTo(ByteBuffer buffer);

	int length();
}
