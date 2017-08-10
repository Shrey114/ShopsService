package com.hcl.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hcl.model.Shop;
import com.hcl.model.ShopResponse;
import com.hcl.service.ShopService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Shreyas Mehta
 *
 */
@RestController
@Api(value = "Shop Mgmt")
public class ShopController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShopController.class);

	@Autowired
	private ShopService shopService;

	@ApiOperation(value = "Create Shop", nickname = "createShop", response = Shop.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Shop.class),
			@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Failure") })
	@RequestMapping(value = "/shops", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> createShop(@RequestBody Shop shop) {
		Shop previousShop = null;

		Shop existingShop = shopService.findShopByName(shop.getShopName());
		if (existingShop != null) {
			previousShop = new Shop(existingShop);
		}

		Shop savedShop = shopService.addShop(shop);

		if (previousShop != null) {
			ShopResponse response = new ShopResponse();
			response.setCurrentAddress(savedShop);
			response.setPreviousAddress(previousShop);
			return ResponseEntity.ok(response);
		}

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}")
				.buildAndExpand(savedShop.getShopName()).toUri();
		LOGGER.info("Shop Added:- " + savedShop);
		return ResponseEntity.created(location).body("new shop added");
	}

	@ApiOperation(value = "Find nearest shops", nickname = "findNearestShops", response = Shop.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Shop.class),
			@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Failure") })
	@RequestMapping(value = "/shops", method = RequestMethod.GET, produces = "application/json", params = { "page",
			"size", "latitude", "longitude" })
	public List<Shop> findNearestShops(@RequestParam("page") int page, @RequestParam("size") int size,
			@RequestParam("latitude") double latitude, @RequestParam("longitude") double longitude) {
		return shopService.findNearestShops(page, size, latitude, longitude);
	}

	@ApiOperation(value = "List all shops", nickname = "listShops", response = Shop.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = Shop.class),
			@ApiResponse(code = 400, message = "Bad request"), @ApiResponse(code = 401, message = "Unauthorized"),
			@ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
			@ApiResponse(code = 500, message = "Internal Failure") })
	@RequestMapping(value = "/shops", method = RequestMethod.GET, produces = "application/json", params = { "page",
			"size" })
	public Page<Shop> listShops(@RequestParam("page") int page, @RequestParam("size") int size) {
		return shopService.listShops(page, size);
	}

}
