package com.hcl.respository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hcl.model.Shop;

@Repository
public interface ShopRepository extends PagingAndSortingRepository<Shop, String> {

}
