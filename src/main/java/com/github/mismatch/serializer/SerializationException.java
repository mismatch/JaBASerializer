package com.github.mismatch.serializer;

public class SerializationException extends RuntimeException {
	public SerializationException(Throwable e) {
		super(e);
	}

	public SerializationException(String s) {
		super(s);
	}
}
