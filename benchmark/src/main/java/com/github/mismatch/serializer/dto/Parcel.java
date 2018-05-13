package com.github.mismatch.serializer.dto;

public class Parcel {
	private final int id;
	private final String description;
	private final Address destinationAddress;
	private final Volume volume;
	private final Weight grossWeight;

	public Parcel(int id, String description, Address destinationAddress, Volume volume, Weight grossWeight) {
		this.id = id;
		this.description = description;
		this.destinationAddress = destinationAddress;
		this.volume = volume;
		this.grossWeight = grossWeight;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public Address getDestinationAddress() {
		return destinationAddress;
	}

	public Volume getVolume() {
		return volume;
	}

	public Weight getGrossWeight() {
		return grossWeight;
	}
}
