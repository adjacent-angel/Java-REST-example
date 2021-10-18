package com.astontech.rest;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepo extends CrudRepository<Product, Integer> {

    Optional<Product> findBySku(String sku);
}
