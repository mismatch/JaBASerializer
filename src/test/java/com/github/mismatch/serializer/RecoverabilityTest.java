package com.github.mismatch.serializer;

import com.github.mismatch.serializer.aux.Deserializer;
import com.github.mismatch.serializer.aux.SimpleTestClass;
import com.github.mismatch.serializer.converters.Converters;
import com.github.mismatch.serializer.units.UnitFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class RecoverabilityTest {

	private Serializer serializer;
	private Deserializer deserializer;

	@BeforeEach
	public void init() {
		Converters converters = new Converters();
		converters.addDefault();
		serializer = new Serializer(new UnitFactory(converters));
		deserializer = new Deserializer();
	}

	@Test
	public void test() {
		Object[] objects = new SimpleTestClass[] {
				new SimpleTestClass(1009, (byte) 69), null, new SimpleTestClass(897, (byte) 110)};
		assertArrayEquals(objects, (Object[]) deserializer.deserialize(
				serializer.serialize(objects), SimpleTestClass[].class));
	}
}
