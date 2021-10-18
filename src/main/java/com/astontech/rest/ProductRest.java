package com.astontech.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductRest {

    private ProductRepo productRepo;

    public ProductRest(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping("/")        //http://localhost:8080/product/
    public Iterable<Product> getAllProducts() {
        return productRepo.findAll();
    }

    //CRUD

    @PostMapping("/")
    public Product addProduct(@RequestBody Product product) {
        return productRepo.save(product);
    }

    @PutMapping("/")
    public Product updateProduct(@RequestBody Product product) {
        return productRepo.save(product);
    }

    @DeleteMapping("/")
    public void deleteProduct(@RequestBody Product product) {
        productRepo.delete(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Integer id) {
        productRepo.deleteById(id);
    }

    //Query params
    @GetMapping()   //http://localhost:8080/product?
    public Product findByIdOrSku(@RequestParam(required = false) Integer id,
                                 @RequestParam(required = false) String sku) {
        if (sku != null && !sku.isEmpty()) {
            return productRepo.findBySku(sku)
                    .orElseThrow(() -> new ProductNotFoundException(sku));
        } else if (id != null) {
            return productRepo.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException(Integer.toString(id)));
        } else {
            throw new ProductNotFoundException(sku == null ? Integer.toString(id) : sku);
        }
    }

    //Path param
    @GetMapping("/{id}")
    public Product findByIdInPath(@PathVariable Integer id) throws ProductNotFoundException {
        return productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(Integer.toString(id)));
    }
}
