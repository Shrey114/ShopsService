package com.hcl.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hcl.model.LatitudeLongitude;
import com.hcl.model.Shop;
import com.hcl.model.ShopAddress;
import com.hcl.respository.ShopRepository;

/**
 * @author Shreyas Mehta
 *
 */
@WebMvcTest
@RunWith(SpringRunner.class)
public class ShopServiceImplTest {

	private static final String SHOP_NAME = "My Fashion Apparel";

	private static final String API_KEY = "AIzaSyA9FjdLyNRnmHleG0J76KOlyP5cmqWcAcE";

	private Shop shop;

	private LatitudeLongitude latLng;

	@InjectMocks
	private ShopServiceImpl shopServiceImpl;

	@Mock
	private GeoService geoServiceMock;

	@Mock
	private ShopRepository shopRepositoryMock;

	@BeforeMethod
	public void setUP() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockShop();
		mockLatitudeLongitude();
	}

	@AfterMethod
	public void tearDown() throws Exception {
		this.shop = null;
	}

	private void mockShop() {
		ShopAddress shopAddress = new ShopAddress("LA-1111", "70526", "Crowley, LA");
		this.shop = new Shop(SHOP_NAME, shopAddress);
	}

	private void mockLatitudeLongitude() {
		this.latLng = new LatitudeLongitude(30.00000000, -92.00000000);
	}

	@Test
	public void testSave() throws Exception {
		when(geoServiceMock.getLatitudeLongitudeFromAddress(API_KEY, shop)).thenReturn(latLng);
		when(shopRepositoryMock.save(shop)).thenReturn(shop);
		Shop savedShop = shopServiceImpl.addShop(shop);
		assertEquals(SHOP_NAME, savedShop.getShopName());
		verify(shopRepositoryMock).save(shop);
	}

	@Test
	public void testFindAll() throws Exception {
		List<Shop> shops = new ArrayList<Shop>();
		shops.add(shop);

		Page<Shop> pageShops = new PageImpl<Shop>(shops);

		when(shopRepositoryMock.findAll(new PageRequest(0, 10))).thenReturn(pageShops);
		shopServiceImpl.listShops(0, 10);

		assertNotNull(shops.get(0).getShopName());
		assertEquals(SHOP_NAME, shops.get(0).getShopName());
		assertNotNull(shops.get(0).getShopAddress());

		verify(shopRepositoryMock).findAll(new PageRequest(0, 10));
	}

}