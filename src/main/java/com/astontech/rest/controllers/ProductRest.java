package com.astontech.rest.controllers;

import com.astontech.rest.domain.Product;
import com.astontech.rest.exceptions.ProductNotFoundException;
import com.astontech.rest.repositories.ProductRepo;
import com.astontech.rest.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductRest {

    private ProductService productService;

    public ProductRest(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")        //http://localhost:8080/product/
    public ResponseEntity<Iterable<Product>> findProduct() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping()   //http://localhost:8080/product?
    public ResponseEntity<Product> findByIdOrSku(@RequestParam(required = false) Integer id,
                                                @RequestParam(required = false) String sku) {
      return ResponseEntity.ok(productService.findBySkuOrId(sku, id));
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Product> findByIdInPath(@PathVariable Integer id) throws ProductNotFoundException {
        return ResponseEntity.ok(productService.findBySkuOrId(null, id));
    }



    @PostMapping("/")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {

        return new ResponseEntity<>(
                productService.saveProduct(product),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        return new ResponseEntity<>(
                productService.updateProduct(product),
                HttpStatus.ACCEPTED
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdateDynamic(
            @RequestBody Map<String, Object> updates,
            @PathVariable Integer id) {

        return new ResponseEntity<>(
                productService.patchProduct(updates, id),
                HttpStatus.ACCEPTED
        );
    }



    @DeleteMapping("/")
    public void deleteProduct(@RequestBody Product product) {
        productService.deleteProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }


}
