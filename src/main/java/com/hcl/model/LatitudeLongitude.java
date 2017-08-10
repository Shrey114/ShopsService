package com.hcl.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Embeddable
public class LatitudeLongitude {

	/**
	 * latitude of the shop
	 */
	@Column(nullable = false)
	private double latitude;
	
	/**
	 * longitude of the shop
	 */
	@Column(nullable = false)
	private double longitude;
	
	protected LatitudeLongitude() {}
	
	public LatitudeLongitude(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public LatitudeLongitude(LatitudeLongitude latLng) {
		this.latitude = latLng.latitude;
		this.longitude = latLng.longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	@JsonIgnore
	public String getLatitudeLongitudeString() {
		return latitude + "," + longitude;
	}

	@Override
	public String toString() {
		return "LatitudeLongitude [latitude=" + latitude + ", longitude=" + longitude + "]";
	}
}
