package com.github.mismatch.serializer.aux;

public class DeserializationException extends RuntimeException {
	public DeserializationException(String s) {
		super(s);
	}

	public DeserializationException(Throwable e) {
		super(e);
	}
}
