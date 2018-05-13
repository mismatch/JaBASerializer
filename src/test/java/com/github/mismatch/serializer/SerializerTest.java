package com.github.mismatch.serializer;

import com.github.mismatch.serializer.aux.SimpleTestClass;
import com.github.mismatch.serializer.converters.Converters;
import com.github.mismatch.serializer.units.UnitFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class SerializerTest {

	private Serializer serializer;

	@BeforeEach
	public void init() {
		Converters converters = new Converters();
		converters.addDefault();
		serializer = new Serializer(new UnitFactory(converters));
	}

	@Test
	public void testNullSerialization() {
		assertArrayEquals(new byte[] {0}, serializer.serialize(null));
	}

	@Test
	public void testPrimitiveSerialization() {
		assertArrayEquals(new byte[] {1, -1}, serializer.serialize((byte) -1));
		assertArrayEquals(new byte[] {2, 0, 12}, serializer.serialize((short) 12));
		assertArrayEquals(new byte[] {3, 0, 0, 1, 95}, serializer.serialize(0x015F));
		assertArrayEquals(new byte[] {4, 0, 0, 0, 33, 1, 0, 0, -1}, serializer.serialize(0x00000021010000FFL));
		assertArrayEquals(new byte[] {7, 0, 97}, serializer.serialize('a'));
	}

	@Test
	public void testPrimitiveArraySerialization() {
		assertArrayEquals(new byte[] {11, 0, 0, 0, 3, 15, 17, 19}, serializer.serialize(new byte[] {15, 17, 19}));
		assertArrayEquals(new byte[] {12, 0, 0, 0, 4, 0, 45, 1, 16}, serializer.serialize(new short[] {45, 272}));
		assertArrayEquals(new byte[] {13, 0, 0, 0, 8, 0, 0, 0, 1, 0, 0, 0, 2}, serializer.serialize(new int[] {1, 2}));
		assertArrayEquals(new byte[] {14, 0, 0, 0, 8, 0, 0, 0, 0, 0, 0, 5, 45}, serializer.serialize(new long[] {1325L}));
	}

	@Test
	public void testArraySerialization() {
		assertArrayEquals(new byte[] {18, 0, 0, 0, 12, 1, 1, 4, 0, 0, 0, 0, 0, 0, 0, 125, 0},
				serializer.serialize(new Number[] {(byte) 1, 125L, null}));
	}

	@Test
	public void testEnumSerialization() {
		assertArrayEquals(new byte[] {3, 0, 0, 0, 2}, serializer.serialize(TestEnum.IGNORED));
	}

	@Test
	public void testBooleanSerialization() {
		assertArrayEquals(new byte[] {1, 1}, serializer.serialize(true));
	}

	@Test
	public void testStringSerialization() {
		assertArrayEquals(new byte[] {11, 0, 0, 0, 6, -2, -1, 0, 97, 0, 48}, serializer.serialize("a0"));
	}

	@Test
	public void testSimpleObjectSerialization() {
		SimpleTestClass testObj = new SimpleTestClass(123, (byte) 10);
		assertArrayEquals(
				new byte[] {8, 0, 0, 0, 23,
						17, 0, 0, 0, 2, 0, 97, 3, 0, 0, 0, 123,
						17, 0, 0, 0, 4, 0, 98, 0, 98, 1, 10},
				serializer.serialize(testObj));
	}

	@Test
	public void testCollectionSerialization() {
		assertArrayEquals(new byte[] {18, 0, 0, 0, 10, 3, 0, 0, 0, 85, 3, 0, 0, 0, 19},
				serializer.serialize(Arrays.asList(85, 19)));
	}

	@Test
	public void testMapSerialization() {
		assertArrayEquals(new byte[] {18, 0, 0, 0, 10, 18, 0, 0, 0, 5, 1, 8, 7, 0, 104},
				serializer.serialize(Collections.singletonMap((byte) 8, 'h')));
	}

	@Test
	public void testSerializationConsistency() {
		CompositeTestClass ct = new CompositeTestClass(
				"Composite", new int[] {5757, 8980, 1223}, new char[] {'a', 'p', 'z'},
				new SimpleTestClass(-100, (byte) 69), new SimpleTestClass(8082, (byte) -33));

		assertArrayEquals(serializer.serialize(ct), serializer.serialize(ct));
	}

	static class CompositeTestClass {
		private final String name;
		private final int[] ids;
		private final char[] codes;
		private final SimpleTestClass simple1;
		private final SimpleTestClass simple2;

		CompositeTestClass(String name, int[] ids, char[] codes, SimpleTestClass simple1, SimpleTestClass simple2) {
			this.name = name;
			this.ids = ids;
			this.codes = codes;
			this.simple1 = simple1;
			this.simple2 = simple2;
		}
	}

	enum TestEnum {
		PASSED, FAILED, IGNORED
	}
}
