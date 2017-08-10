package com.hcl.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Shreyas Mehta
 *
 */
@Entity
public class Shop {

	/**
	 * Name of the shop.
	 */
	@Id
	@Column(nullable = false, unique = true, length = 30)
	private String shopName;

	@Embedded
	private ShopAddress shopAddress;

	@Embedded
	private LatitudeLongitude latLng;
	
	/**
	 * Specifies the distance of the shop from the customer.
	 */
	private String distance;

	protected Shop() {
	}
	
	public Shop(Shop shop) {
		this.shopName = shop.shopName;
		this.shopAddress = new ShopAddress(shop.getShopAddress());
		this.latLng = new LatitudeLongitude(shop.getLatLng());
	}

	public Shop(String shopName, ShopAddress shopAddress) {
		this.shopName = shopName;
		this.shopAddress = shopAddress;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public ShopAddress getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(ShopAddress shopAddress) {
		this.shopAddress = shopAddress;
	}

	public LatitudeLongitude getLatLng() {
		return latLng;
	}

	public void setLatLng(LatitudeLongitude latLng) {
		this.latLng = latLng;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Shop [shopName=");
		builder.append(shopName);
		builder.append(", shopAddress=");
		builder.append(shopAddress);
		if (latLng != null) {
			builder.append(", latLng=");
			builder.append(latLng);
		}
		if (distance != null && !distance.isEmpty()) {
			builder.append(", distance=");
			builder.append(distance);
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((shopAddress == null) ? 0 : shopAddress.hashCode());
		result = prime * result + ((shopName == null) ? 0 : shopName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shop other = (Shop) obj;
		if (shopAddress == null) {
			if (other.shopAddress != null)
				return false;
		} else if (!shopAddress.equals(other.shopAddress))
			return false;
		if (shopName == null) {
			if (other.shopName != null)
				return false;
		} else if (!shopName.equals(other.shopName))
			return false;
		return true;
	}

}
