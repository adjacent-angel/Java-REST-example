package com.astontech.rest;

public class ProductNotFoundException extends RuntimeException {

    ProductNotFoundException(String id) {
        super("Could not find product " + id);
    }
}
