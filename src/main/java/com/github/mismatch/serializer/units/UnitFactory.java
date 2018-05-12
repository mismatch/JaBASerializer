package com.github.mismatch.serializer.units;

import com.github.mismatch.serializer.SerializationException;
import com.github.mismatch.serializer.converters.Converters;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class UnitFactory {
	private static final Map<Class, Function<Object, Unit>> BASIC_TYPE_UNIT_SUPPLIERS = new HashMap<>();
	private static final Map<Class, Function<Object, Unit>> BASIC_ARRAY_TYPE_UNIT_SUPPLIERS = new HashMap<>();

	static {
		BASIC_TYPE_UNIT_SUPPLIERS.put(Byte.class, o -> new ByteUnit((Byte) o));
		BASIC_TYPE_UNIT_SUPPLIERS.put(Short.class, o -> new ShortUnit((Short) o));
		BASIC_TYPE_UNIT_SUPPLIERS.put(Integer.class, o -> new IntUnit((Integer) o));
		BASIC_TYPE_UNIT_SUPPLIERS.put(Long.class, o -> new LongUnit((Long) o));
		BASIC_TYPE_UNIT_SUPPLIERS.put(Float.class, o -> new FloatUnit((Float) o));
		BASIC_TYPE_UNIT_SUPPLIERS.put(Double.class, o -> new DoubleUnit((Double) o));
		BASIC_TYPE_UNIT_SUPPLIERS.put(Character.class, o -> new CharUnit((Character) o));

		BASIC_ARRAY_TYPE_UNIT_SUPPLIERS.put(byte.class, o -> new ByteArrayUnit((byte[]) o));
		BASIC_ARRAY_TYPE_UNIT_SUPPLIERS.put(short.class, o -> new ShortArrayUnit((short[]) o));
		BASIC_ARRAY_TYPE_UNIT_SUPPLIERS.put(int.class, o -> new IntArrayUnit((int[]) o));
		BASIC_ARRAY_TYPE_UNIT_SUPPLIERS.put(long.class, o -> new LongArrayUnit((long[]) o));
		BASIC_ARRAY_TYPE_UNIT_SUPPLIERS.put(float.class, o -> new FloatArrayUnit((float[]) o));
		BASIC_ARRAY_TYPE_UNIT_SUPPLIERS.put(double.class, o -> new DoubleArrayUnit((double[]) o));
		BASIC_ARRAY_TYPE_UNIT_SUPPLIERS.put(char.class, o -> new CharArrayUnit((char[]) o));
	}

	private final Converters converters;

	public UnitFactory(Converters converters) {
		this.converters = converters;
	}

	public Unit get(Object object) {
		if (null == object) {
			return NullUnit.INSTANCE;
		}

		Class<?> klass = object.getClass();

		Function<Object, Unit> supplier = BASIC_TYPE_UNIT_SUPPLIERS.get(klass);
		if (null != supplier) {
			return supplier.apply(object);
		}

		if (klass.isArray()) {
			return getArrayUnit(object);
		}

		if (klass.isEnum()) {
			Enum<?> enumObject = (Enum<?>) object;
			return new IntUnit(Enum.valueOf(enumObject.getClass(), enumObject.name()).ordinal());
		}

		Function converter = converters.getConverter(klass);
		return null == converter ? getRecordUnit(object) : get(converter.apply(object));
	}

	private Unit getArrayUnit(Object object) {
		Function<Object, Unit> supplier = BASIC_ARRAY_TYPE_UNIT_SUPPLIERS.get(object.getClass().getComponentType());
		if (null == supplier) {
			return Arrays.stream((Object[]) object).map(this::get)
					.collect(collectingAndThen(toList(), ArrayUnit::new));
		}
		return supplier.apply(object);
	}

	private Unit getFromField(Field f, Object object) {
		try {
			f.setAccessible(true);
			return get(f.get(object));
		} catch (IllegalAccessException e) {
			throw new SerializationException(e);
		}
	}

	private RecordUnit getRecordUnit(Object object) {
		return Arrays.stream(object.getClass().getDeclaredFields())
				.filter(UnitFactory::isSerializableField)
				.collect(collectingAndThen(toMap(f -> new CharArrayUnit(toCharArray(f.getName())),
						f -> getFromField(f, object), (u1, u2) -> u1, TreeMap::new),
				fieldUnits -> new RecordUnit(fieldUnits)));
	}

	private static boolean isSerializableField(Field field) {
		return !(field.isSynthetic() || isStaticField(field) || isTransientField(field));
	}

	private static boolean isTransientField(Field field) {
		return (field.getModifiers() & Modifier.TRANSIENT) == Modifier.TRANSIENT;
	}

	private static boolean isStaticField(Field field) {
		return (field.getModifiers() & Modifier.STATIC) == Modifier.STATIC;
	}

	private char[] toCharArray(String s) {
		if (null == s) {
			return null;
		}

		if (s.isEmpty()) {
			return new char[0];
		}

		char[] res = new char[s.length()];
		s.getChars(0, s.length(), res, 0);
		return res;
	}
}
