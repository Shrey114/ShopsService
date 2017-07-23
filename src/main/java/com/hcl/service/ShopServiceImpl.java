package com.hcl.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hcl.data.ErrorResponse;
import com.hcl.exception.BadRequestException;
import com.hcl.model.LatitudeLongitude;
import com.hcl.model.Shop;
import com.hcl.respository.ShopRepository;

/**
 * @author Shreyas Mehta
 *
 */
@Service
public class ShopServiceImpl implements ShopService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShopServiceImpl.class);

	private static final String API_KEY = "AIzaSyA9FjdLyNRnmHleG0J76KOlyP5cmqWcAcE";

	@Autowired
	private ShopRepository shopRepository;

	@Autowired
	private GeoService geoService;

	@Override
	public Shop addShop(Shop shop) throws DataAccessException {
		LatitudeLongitude latitudeLongitude = geoService.getLatitudeLongitudeFromAddress(API_KEY, shop);

		if (latitudeLongitude != null) {

			LOGGER.info("Fetched latitude and longitude for the shop. " + latitudeLongitude);
			shop.setLatLng(latitudeLongitude);
			return shopRepository.save(shop);
		}

		LOGGER.error("Latitude and longitude for the shop cannot be fetched for the shop. " + shop);
		return null;
	}

	@Override
	public Page<Shop> listShops(int page, int size) {
		return shopRepository.findAll(new PageRequest(page, size));
	}

	@Override
	public List<Shop> findNearestShops(int page, int size, double latitude, double longitude) {
		List<Shop> shops = new ArrayList<Shop>();
		List<String> destinationAddress = new ArrayList<String>();
		LatitudeLongitude latLng = new LatitudeLongitude(latitude, longitude);

		populateShopsandDestnAddr(destinationAddress, shops);

		populateDistanceOfShops(latLng.getLatitudeLongitudeString(), destinationAddress, shops);
		return shops;
	}

	@Override
	public Shop findShopByName(String name) {
		Shop shop = null;
		try {
			shop = shopRepository.findOne(name);
		} catch (InvalidDataAccessApiUsageException ex) {
			throw new BadRequestException(new ErrorResponse(HttpStatus.BAD_REQUEST, "Shop name is empty or incorrect"));
		}
		return shop;
	}

	private void populateDistanceOfShops(String originAddress, List<String> destinationAddress, List<Shop> shops) {
		geoService.getDistances(API_KEY, originAddress, destinationAddress.toArray(new String[] {}), shops);
		LOGGER.info("Added distances of the shop from the origin. Origin Address:- " + originAddress);
	}

	private void populateShopsandDestnAddr(List<String> destinationAddress, List<Shop> shops) {
		Iterator<Shop> shopsItr = shopRepository.findAll().iterator();
		while (shopsItr.hasNext()) {
			Shop shop = shopsItr.next();
			shops.add(shop);
			destinationAddress.add(shop.getLatLng().getLatitudeLongitudeString());
		}
	}

}
