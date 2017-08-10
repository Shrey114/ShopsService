package com.hcl.service;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;
import com.hcl.model.LatitudeLongitude;
import com.hcl.model.Shop;
import com.hcl.model.ShopAddress;

/**
 * @author Shreyas Mehta
 *
 */
@WebMvcTest
@PrepareForTest({ GeocodingApi.class, GeocodingApiRequest.class })
@RunWith(SpringRunner.class)
public class GeoServiceImplTest {

	private static final String ADDRESS = "";

	private static final String SHOP_NAME = "My Fashion Apparel";

	private LatitudeLongitude latLng;

	private GeocodingResult[] results;

	private Shop shop;

	@InjectMocks
	private GeoServiceImpl geoServiceImpl;

	@BeforeMethod
	public void setUP() throws Exception {
		/*
		 * PowerMockito.mockStatic(GeocodingApi.class);
		 * PowerMockito.mockStatic(GeocodingApiRequest.class);
		 * MockitoAnnotations.initMocks(this);
		 * 
		 * mockLatitudeLongitude(); mockGeocodingResult(); mockShop();
		 */
	}

	@AfterMethod
	public void tearDown() throws Exception {
		this.latLng = null;
		this.shop = null;
		this.results = null;
	}

	private void mockShop() {
		ShopAddress shopAddress = new ShopAddress("LA-1111", "70526", "Crowley, LA");
		this.shop = new Shop(SHOP_NAME, shopAddress);
	}

	private void mockLatitudeLongitude() {
		this.latLng = new LatitudeLongitude(30.00000000, -92.00000000);
	}

	private void mockGeocodingResult() {
		GeocodingResult geocodingResult = new GeocodingResult();
		geocodingResult.geometry = new Geometry();
		geocodingResult.geometry.location = new LatLng(30.00000000, -92.00000000);

		this.results = new GeocodingResult[] { geocodingResult };
	}

	@Test
	public void testGetLatitudeLongitudeFromAddress() throws Exception {
		/*
		 * GeoApiContext context = new GeoApiContext().setApiKey("");
		 * when(GeocodingApi.geocode(context,
		 * ADDRESS).await()).thenReturn(results); LatitudeLongitude
		 * latitudeLongitude =
		 * geoServiceImpl.getLatitudeLongitudeFromAddress("", shop);
		 * 
		 * assertNotNull(latitudeLongitude);
		 */
	}
}
