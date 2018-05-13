package com.github.mismatch.serializer;

import com.github.mismatch.serializer.aux.Deserializer;
import com.github.mismatch.serializer.aux.SimpleTestClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeserializerTest {

	private Deserializer deserializer;

	@BeforeEach
	public void setUp() {
		deserializer = new Deserializer();
	}

	@Test
	public void testDeserializationToObject() {
		SimpleTestClass testObj = new SimpleTestClass(123, (byte) 10);
		assertEquals(testObj, deserializer.deserialize(
				new byte[] {8, 0, 0, 0, 23,
					17, 0, 0, 0, 2, 0, 97, 3, 0, 0, 0, 123,
					17, 0, 0, 0, 4, 0, 98, 0, 98, 1, 10},
				SimpleTestClass.class));
	}

}
