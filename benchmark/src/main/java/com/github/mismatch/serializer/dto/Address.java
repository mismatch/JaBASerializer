package com.github.mismatch.serializer.dto;

public class Address {
	private final String country;
	private final String town;
	private final String postcode;

	public Address(String country, String town, String postcode) {
		this.country = country;
		this.town = town;
		this.postcode = postcode;
	}

	public String getCountry() {
		return country;
	}

	public String getTown() {
		return town;
	}

	public String getPostcode() {
		return postcode;
	}
}
