package com.github.mismatch.serializer.dto;

public class Weight {

	private final double value;
	private final WeightUnit unit;

	public Weight(double value, WeightUnit unit) {
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public WeightUnit getUnit() {
		return unit;
	}
}
