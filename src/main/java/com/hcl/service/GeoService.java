package com.hcl.service;

import java.util.List;

import com.hcl.model.LatitudeLongitude;
import com.hcl.model.Shop;

/**
 * @author Shreyas Mehta
 *
 */
public interface GeoService {

	LatitudeLongitude getLatitudeLongitudeFromAddress(String apiKey, Shop shop);

	String getAddressFromLatitudeLongitude(String apiKey, double latitude, double longitude);

	void getDistances(String apiKey, String origin, String[] destinations, List<Shop> shops);
}
