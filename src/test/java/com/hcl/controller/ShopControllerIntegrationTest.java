package com.hcl.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.model.Shop;
import com.hcl.model.ShopAddress;
import com.hcl.service.ShopService;

/**
 * @author Shreyas Mehta
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShopControllerIntegrationTest {
	
	private static final String SHOP_NAME = "My Fashion Apparel";
	
	private static final String SHOP_REST_CONTROLLER = "/shops";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ShopService shopService;
	
	private Shop shop;
	private Shop shop1;
	private Shop shop2;
	private Shop shop3;
	private Shop shop4;
	
	private List<Shop> shops;
	
	@Before
	public void setUp() throws Exception {
		mockShop();
		mockShops();
	}
	
	private void mockShops() {
		this.shop1 = new Shop("Shop 1", new ShopAddress("LA-1111, Crowley, LA", "70526"));
		this.shop2 = new Shop("Shop 2", new ShopAddress("State Hwy 1111, Jackson, KY", "41339"));
		this.shop3 = new Shop("Shop 3", new ShopAddress("LA-1111 Spur, Crowley, LA", "70526"));
		this.shop4 = new Shop("Shop 4", new ShopAddress("State Rte 1111, Henrico, VA", "23238"));
		
		shopService.addShop(this.shop1);
		shopService.addShop(this.shop2);
		shopService.addShop(this.shop3);
		shopService.addShop(this.shop4);
		
		shops = new ArrayList<Shop>();
		shops.add(shop1);
		shops.add(shop2);
		shops.add(shop3);
		shops.add(shop4);
	}

	private void mockShop() {
		ShopAddress shopAddress = new ShopAddress("Mother's Kitchen, Vishal Nagar, Pimpri-Chinchwad, Maharashtra", "411038");
		this.shop = new Shop(SHOP_NAME, shopAddress);
	}

	@After
	public void tearDown() throws Exception {
		this.shop = null;
		this.shop1 = null;
		this.shop2 = null;
		this.shop3 = null;
		this.shop4 = null;
	}
	
	@Test
	public void addShop() throws JsonProcessingException, Exception {
		MvcResult mvcResult = this.mockMvc
				.perform(post(SHOP_REST_CONTROLLER).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsBytes(this.shop)).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", containsString(SHOP_REST_CONTROLLER))).andReturn();
		assertEquals("new shop added", mvcResult.getResponse().getContentAsString());
		
		shop.setShopAddress(new ShopAddress("CopaCabana,Omkar Society, Pimple Nilakh, Pimpri-Chinchwad, Maharashtra", "411029"));
		this.mockMvc
			.perform(post(SHOP_REST_CONTROLLER).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(this.shop)).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk()).andExpect(jsonPath("$.currentAddress.shopAddress.postCode", equalTo("411029")));
	}
	
	@Test
	public void findNearestShops() throws JsonProcessingException, Exception {
		this.mockMvc
			.perform(get(SHOP_REST_CONTROLLER).param("latitude", "30.00000000").param("longitude", "-92.00000000")
				.param("page", "0").param("size", "10").contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.[0].shopName", equalTo(this.shop1.getShopName())))
			.andExpect(jsonPath("$.[1].shopName", equalTo(this.shop2.getShopName())))
			.andExpect(jsonPath("$.[2].shopName", equalTo(this.shop3.getShopName())))
			.andExpect(jsonPath("$.[3].shopName", equalTo(this.shop4.getShopName())));
	}
}
