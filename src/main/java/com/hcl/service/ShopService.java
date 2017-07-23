package com.hcl.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.hcl.model.Shop;

/**
 * @author Shreyas Mehta
 *
 */
public interface ShopService {
	public Shop addShop(Shop shop);

	public Page<Shop> listShops(int page, int size);

	public List<Shop> findNearestShops(int page, int size, double latitude, double longitude);
	
	public Shop findShopByName(String name);
}
