package com.github.mismatch.serializer.dto;

public class Volume {
	private final double length;
	private final double width;
	private final double height;
	private final DimensionUnit unit;

	public Volume(double length, double width, double height, DimensionUnit unit) {
		this.length = length;
		this.width = width;
		this.height = height;
		this.unit = unit;
	}

	public double getLength() {
		return length;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public DimensionUnit getUnit() {
		return unit;
	}
}
