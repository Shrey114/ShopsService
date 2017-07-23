package com.hcl.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Shreyas Mehta
 *
 */
@Embeddable
public class ShopAddress {
	
	/**
	 * Shop number or exact address details
	 */
	@Column(nullable = false)
	private String shopNumber;
	
	/**
	 * Shop post code
	 */
	@Column(nullable = false)
	private String postCode;
	
	protected ShopAddress() {}

    public ShopAddress(String shopNumber, String postCode) {
        this.shopNumber = shopNumber;
        this.postCode = postCode;
    }
	
	public String getShopNumber() {
		return shopNumber;
	}
	public void setShopNumber(String shopNumber) {
		this.shopNumber = shopNumber;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@Override
	public String toString() {
		return "ShopAddress [shopNumber=" + shopNumber + ", postCode=" + postCode + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((postCode == null) ? 0 : postCode.hashCode());
		result = prime * result + ((shopNumber == null) ? 0 : shopNumber.hashCode());
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
		ShopAddress other = (ShopAddress) obj;
		if (postCode == null) {
			if (other.postCode != null)
				return false;
		} else if (!postCode.equals(other.postCode))
			return false;
		if (shopNumber == null) {
			if (other.shopNumber != null)
				return false;
		} else if (!shopNumber.equals(other.shopNumber))
			return false;
		return true;
	}

	public String getCompleteAddress() {
		StringBuilder builder = new StringBuilder();
		builder.append(getShopNumber());
		builder.append(" ");
		builder.append(getPostCode());
		return builder.toString();
	}
}
