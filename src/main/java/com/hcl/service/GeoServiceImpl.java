package com.hcl.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixElementStatus;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.hcl.exception.ApplicationException;
import com.hcl.model.LatitudeLongitude;
import com.hcl.model.Shop;

/**
 * @author Shreyas Mehta
 *
 */
@Service
public class GeoServiceImpl implements GeoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeoServiceImpl.class);

	@Override
	public LatitudeLongitude getLatitudeLongitudeFromAddress(String apiKey, Shop shop) {
		LatitudeLongitude latitudeLongitude = null;

		GeoApiContext context = getGeoApiContext(apiKey);
		try {
			GeocodingApiRequest geocodingApiRequest = GeocodingApi.geocode(context,
					shop.getShopAddress().getCompleteAddress());
			GeocodingResult[] results = geocodingApiRequest.await();
			for (GeocodingResult result : results) {
				LatLng latLng = result.geometry.location;
				latitudeLongitude = new LatitudeLongitude(latLng.lat, latLng.lng);
			}
		} catch (ApiException | InterruptedException | IOException e) {
			LOGGER.error("Could not fetch latitude longitude for the address. Exception:- " + e.getMessage());
			throw new ApplicationException();
		}
		return latitudeLongitude;
	}

	@Override
	public void getDistances(String apiKey, String origin, String[] destinations, List<Shop> shops) {
		GeoApiContext context = getGeoApiContext(apiKey);

		try {
			DistanceMatrix matrix = DistanceMatrixApi.getDistanceMatrix(context, new String[] { origin }, destinations)
					.await();
			if (matrix != null) {
				DistanceMatrixRow[] rows = matrix.rows;
				DistanceMatrixElement[] elements = rows[0].elements;
				if (elements != null) {
					for (int j = 0; j < elements.length; j++) {
						Shop shop = shops.get(j);
						if (DistanceMatrixElementStatus.OK == elements[j].status) {
							shop.setDistance(elements[j].distance.toString());
						} else {
							LOGGER.info("Could not derive distance of the shop from the origin. Origin:- " + origin,
									" Shop:- " + shop);
						}
					}
				}
			}
		} catch (ApiException | InterruptedException | IOException e) {
			LOGGER.error("Could not derive distances of the shop from the origin. Exception:- " + e.getMessage());
			throw new ApplicationException();
		}
	}

	@Override
	public String getAddressFromLatitudeLongitude(String apiKey, double latitude, double longitude) {
		String originAddress = null;
		LatLng latLng = new LatLng(latitude, longitude);
		GeoApiContext context = getGeoApiContext(apiKey);

		try {
			GeocodingResult[] results = GeocodingApi.reverseGeocode(context, latLng).await();
			for (GeocodingResult result : results) {
				originAddress = result.formattedAddress;
			}
		} catch (ApiException | InterruptedException | IOException e) {
			LOGGER.error("Fetching address from latitude and longitude failed. Exception:- " + e.getMessage());
			throw new ApplicationException();
		}

		return originAddress;
	}

	private GeoApiContext getGeoApiContext(String apiKey) {
		GeoApiContext context = new GeoApiContext().setApiKey(apiKey);
		return context;
	}

}
