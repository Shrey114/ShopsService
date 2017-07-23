package com.hcl.model;

public class ShopResponse {
	
	private Shop currentAddress;
	
	private Shop previousAddress;

	public Shop getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(Shop currentAddress) {
		this.currentAddress = currentAddress;
	}

	public Shop getPreviousAddress() {
		return previousAddress;
	}

	public void setPreviousAddress(Shop previousAddress) {
		this.previousAddress = previousAddress;
	}
	
}
