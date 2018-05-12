package com.github.mismatch.serializer.converters;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

public class Converters {

	private final Map<Class, Function<Object, ?>> registry = new HashMap<>();
	private final Map<Class, Function<Object, ?>> hierarchyRegistry = new HashMap<>();

	@SuppressWarnings("unchecked")
	public <O, T> Function<Object, T> getConverter(Class<O> klass) {
		Function<Object, ?> converter = registry.get(klass);
		return null == converter ? getHierarchyConverter(klass) : (Function<Object, T>) converter;
	}

	@SuppressWarnings("unchecked")
	private <O, T> Function<Object, T> getHierarchyConverter(Class<O> klass) {
		Class currentClass = klass;
		Function<Object, ?> converter = null;
		while (!Object.class.equals(currentClass) && null == converter) {
			converter = hierarchyRegistry.get(currentClass);
			currentClass = currentClass.getSuperclass();
		}

		return (Function<Object, T>) converter;
	}

	public void addDefault() {
		addConverter(Boolean.class, b -> (byte) (Boolean.TRUE.equals(b) ? 1 : 0));
		addConverter(String.class, s -> ((String) s).getBytes(StandardCharsets.UTF_16));
		addConverter(Date.class, d -> ((Date) d).getTime());

		addHierarchyConverter(AbstractCollection.class, c -> ((AbstractCollection) c).toArray());
		addHierarchyConverter(AbstractMap.class, m -> toArray((AbstractMap<?, ?>) m));
	}

	public <O, T> void addConverter(Class<O> klass, Function<Object, T> converter) {
		registry.put(klass, converter);
	}

	public <O, T> void addHierarchyConverter(Class<O> klass, Function<Object, T> converter) {
		hierarchyRegistry.put(klass, converter);
	}

	private <K, V> Object[] toArray(AbstractMap<K, V> map) {
		return map.entrySet().stream().map(e -> new Object[] {e.getKey(), e.getValue()}).toArray();
	}
}
